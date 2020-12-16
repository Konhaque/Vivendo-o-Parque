package com.projetobeta.vidanoparque.funcionalidades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.FaunaeFlora;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.generalfunctions.AbreTela;

public class Inicio extends Fragment {

    private ImageView sobre;
    private ImageView visita;
    private ImageView faunaeflora;
    private ImageView agendaCultural;
    private TextView lblInicio;
    //private ImageView fale_conosco;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.inicio,container,false);
        return viewGroup;
    }


    @Override
    public void onStart() {
        iniciarObjetos();
        setLblInicio();
        setSobre();
        setVisita();
        setFaunaeflora();
        //setFale_conosco();
        setAgendaCultural();
        super.onStart();
    }

    private void iniciarObjetos(){
        sobre = (ImageView) getActivity().findViewById(R.id.sobre);
        visita = (ImageView) getActivity().findViewById(R.id.visita);
        faunaeflora = (ImageView) getActivity().findViewById(R.id.faunaeflora);
        //fale_conosco = (ImageView) getActivity().findViewById(R.id.fale_conosco);
        agendaCultural = (ImageView) getActivity().findViewById(R.id.agenda);
        lblInicio = (TextView) getActivity().findViewById(R.id.lbl_inicio);
    }

    private void setSobre(){
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new AbreTela(getActivity().getSupportFragmentManager(),new Sobre(),R.id.set_Tela);
            }
        });
    }

    private void setVisita(){
        visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://app.lapentor.com/sphere/visita-virtual-vivendo-o-parque-1600857000"));
                startActivity(intent);
            }
        });
    }

    /*private void setFale_conosco(){
        fale_conosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });
    }*/

    private void setFaunaeflora(){
        faunaeflora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FaunaeFlora.class);
                startActivity(intent);
            }
        });
    }

    private void setAgendaCultural(){
        agendaCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AbreTela(getActivity().getSupportFragmentManager(),new AgendaCultural(),R.id.set_Tela);
            }
        });
    }

    private void setLblInicio(){
        lblInicio.setText("Bem Vindo "+new Repository(getContext()).getNomeUsuario()+"!");
    }

    private void enviarEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String[] email = {
          "vivendoparque@gmail.com"
        };
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(intent);
    }

}
