package com.projetobeta.vidanoparque.funcionalidades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.FaunaeFlora;
import com.projetobeta.vidanoparque.R;

public class Inicio extends Fragment {

    private ImageView sobre;
    private ImageView visita;
    private ImageView faunaeflora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.inicio,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setSobre();
        setVisita();
        //setFaunaeflora();
        super.onStart();
    }

    private void iniciarObjetos(){
        sobre = (ImageView) getActivity().findViewById(R.id.sobre);
        visita = (ImageView) getActivity().findViewById(R.id.visita);
        faunaeflora = (ImageView) getActivity().findViewById(R.id.faunaeflora);
    }

    private void setSobre(){
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zealous-ground-0db153910.azurestaticapps.net/pituacu/pituacu.html#visit"));
                startActivity(intent);
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

    private void setFaunaeflora(){
        faunaeflora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FaunaeFlora.class);
                startActivity(intent);
            }
        });
    }


}
