package com.projetobeta.vidanoparque.funcionalidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;

public class Rede_Social extends Fragment {
    private ImageView foto_Perfil;
    private Usuario usuario;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.rede_social,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setFoto_Perfil();
        super.onStart();
    }

    private void iniciarObjetos(){
        foto_Perfil = (ImageView) getActivity().findViewById(R.id.foto_perfilRede);
        usuario = new Repository(getContext()).getPerfil();
    }

    private void setFoto_Perfil(){
        if (usuario.getFoto_perfil() != null && usuario.getFoto_perfil().length()>0)
            Glide.with(getActivity()).load(usuario.getFoto_perfil()).into(foto_Perfil);
    }
}
