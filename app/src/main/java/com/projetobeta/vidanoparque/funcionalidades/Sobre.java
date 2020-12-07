package com.projetobeta.vidanoparque.funcionalidades;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.generalfunctions.AbreTela;

public class Sobre extends Fragment {
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.sobre_parque,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setToolbar();
        super.onStart();
    }

    private void iniciarObjetos(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.tb_sobre);
    }

    private void setToolbar(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                new AbreTela(getActivity().getSupportFragmentManager(),new Inicio(),R.id.set_Tela);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
