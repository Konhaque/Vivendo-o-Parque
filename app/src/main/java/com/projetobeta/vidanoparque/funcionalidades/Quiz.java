package com.projetobeta.vidanoparque.funcionalidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.Play_Quiz;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Conexao;

public class Quiz extends Fragment {
    private Button jogar;
    private AlertDialog dialog;
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
                getQuestoes();
                //startActivity(new Intent(getActivity(), Play_Quiz.class));
            }
        });
        super.onStart();
    }

    private void getQuestoes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_main,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        new Conexao(getActivity(),dialog).questoesQuiz();
    }
}
