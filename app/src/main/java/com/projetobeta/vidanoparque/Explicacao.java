package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario_Quiz;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

import org.w3c.dom.Text;

public class Explicacao extends AppCompatActivity {
    private TextView explicacao;
    private TextView lbl;
    private Button confirmar;
    private Intent intent;
    private AlertDialog dialog;
    private Usuario_Quiz usuario_quiz;
    private ImageView macaco;
    private int pontos;
    private int acertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_explicacao);
        iniciarObjetos();
        setResultado();
        setExplicacao();
        setConfirmar();
    }

    private void iniciarObjetos(){
        explicacao = (TextView) findViewById(R.id.explicacao);
        lbl = (TextView) findViewById(R.id.lbl_resultado);
        confirmar = (Button) findViewById(R.id.confirmar);
        macaco = (ImageView) findViewById(R.id.macaco);
        intent = getIntent();
        usuario_quiz = new Usuario_Quiz();
        pontos = new Repository(this).getPontos();
        acertos = new Repository(this).getAcertos();
    }

    private void setResultado(){
        if(intent.getStringExtra("resposta").equals(intent.getStringExtra("certo"))){
            abrirDialog(R.layout.acertou);
            lbl.setText("Você Acertou!");
            Glide.with(this).load(R.drawable.macaco_joinha).into(macaco);
            pontos+= 10;
            acertos+=1;
            new Repository(this).updatePontos(pontos,acertos);
        }
        else {
            abrirDialog(R.layout.errou);
            lbl.setText("Você Errou!");
            Glide.with(this).load(R.drawable.macaco_negativo).into(macaco);
        }
    }
    private void setExplicacao(){
        explicacao.setText(intent.getStringExtra("exp"));
    }
    private void setConfirmar(){
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent.getIntExtra("qr",0) == 2) {
                    usuario_quiz.setAcertos(acertos);
                    usuario_quiz.setPontos(pontos);
                    usuario_quiz.setId_Usuario(new Repository(Explicacao.this).getIdUsuario());
                    usuario_quiz.setQuestoes_Jogadas(intent.getStringArrayListExtra("qj"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Explicacao.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
                    builder.setCancelable(true);
                    dialog = builder.create();
                    dialog.show();
                    new Conexao(Explicacao.this, dialog).salvarQuizUsuario(usuario_quiz);
                }else finish();

            }
        });
    }

    private void abrirDialog(int layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(layout,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },2000);
    }



}