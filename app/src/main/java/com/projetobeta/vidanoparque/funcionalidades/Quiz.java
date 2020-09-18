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

import com.projetobeta.vidanoparque.Play_Quiz;
import com.projetobeta.vidanoparque.R;

public class Quiz extends Fragment {
    private Button jogar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.quiz,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        jogar = (Button) getActivity().findViewById(R.id.jogar_quiz);
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Play_Quiz.class));
            }
        });
        super.onStart();
    }
}
