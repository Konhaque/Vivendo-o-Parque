package com.projetobeta.vidanoparque.funcionalidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;

public class Perfil extends Fragment {
    private TextView nome;
    private TextView email;
    private ImageView ft_perfil;
    private Usuario usuario;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.perfil,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        iniciar_Objetos();
        setInfo();
        setFt_perfil();
        super.onStart();
    }

    private void iniciar_Objetos(){
        nome = (TextView) getActivity().findViewById(R.id.nome);
        email = (TextView) getActivity().findViewById(R.id.email);
        ft_perfil = (ImageView) getActivity().findViewById(R.id.foto_perfil);
        usuario = new Repository(getContext()).getPerfil();
    }

    private void setInfo(){
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
        if (usuario.getFoto_perfil() != null && usuario.getFoto_perfil().length()>0)
            Glide.with(getActivity()).load(usuario.getFoto_perfil()).into(ft_perfil);
    }

    private void setFt_perfil(){
        ft_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(resultCode == getActivity().RESULT_OK){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = getLayoutInflater();
                    builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
                    builder.setCancelable(true);
                    dialog = builder.create();
                    dialog.show();
                    new Conexao(getActivity(),dialog).salvarFoto_Perfil(usuario.getId(),data.getDataString(),ft_perfil);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
