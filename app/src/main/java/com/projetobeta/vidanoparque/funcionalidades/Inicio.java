package com.projetobeta.vidanoparque.funcionalidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.WebView;

public class Inicio extends Fragment {

    private Button historia;
    private Button fauna;
    private Button flora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.inicio,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setHistoria();
        super.onStart();
    }

    private void iniciarObjetos(){
        historia = (Button) getActivity().findViewById(R.id.historia);
    }

    private void setHistoria(){
        historia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WebView.class));
            }
        });
    }
}
