package com.projetobeta.vidanoparque.parques;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.Login;
import com.projetobeta.vidanoparque.R;

public class Parque_Pituacu extends Fragment {
    private Button avancar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.parque_pituacu,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        objects();
        setAvancar();
    }

    private void objects(){
        avancar = (Button) getActivity().findViewById(R.id.btn_parquepituacu);
    }

    private void setAvancar(){
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });
    }
}
