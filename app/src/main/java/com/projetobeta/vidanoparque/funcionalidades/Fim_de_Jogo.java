package com.projetobeta.vidanoparque.funcionalidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.core.Repo;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;

import java.util.List;

public class Fim_de_Jogo extends Fragment {
    private TextView usuario;
    private TextView ranking;
    private Usuario usuarios;
    private int pontos;
    private int acertos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup  = (ViewGroup) inflater.inflate(R.layout.fim_de_jogo,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setUsuario();
        super.onStart();
    }

    private void iniciarObjetos(){
        usuario = (TextView) getActivity().findViewById(R.id.user);
        ranking =  (TextView) getActivity().findViewById(R.id.ranking);
        usuarios = new Repository(getContext()).getPerfil();
        pontos = new Repository(getContext()).getPontos();
        acertos = new Repository(getContext()).getAcertos();
    }

    private void setUsuario(){
        usuario.setText("Parabens "+usuarios.getNome()+"! Você fez "+pontos+" pontos no nosso Quiz");
        ranking.setText("Você acertou "+acertos+"/20");
    }

}
