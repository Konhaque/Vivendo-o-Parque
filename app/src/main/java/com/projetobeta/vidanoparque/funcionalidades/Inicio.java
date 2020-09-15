package com.projetobeta.vidanoparque.funcionalidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.R;

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
        super.onStart();
    }

    private void iniciarObjetos(){
        historia = (Button) getActivity().findViewById(R.id.historia);
        fauna = (Button) getActivity().findViewById(R.id.fauna);
        flora = (Button) getActivity().findViewById(R.id.flora);
    }
}
