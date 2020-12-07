package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Questoes_Quiz;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario_Quiz;
import com.projetobeta.vidanoparque.funcionalidades.Perfil;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;
import com.projetobeta.vidanoparque.generalfunctions.SharedPrefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Play_Quiz extends AppCompatActivity {
    private TextView pontos;
    private TextView questao;
    private Button op1;
    private Button op2;
    private Button op3;
    private Button op4;
    private Toolbar toolbar;
    private List<Questoes_Quiz> questoes;
    private ArrayList questoesJogadas;
    private Random random;
    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    private int respostas;
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_play__quiz);
        iniciarObjetos();
        setOpcoes();
        setQuestao();
    }

    private void iniciarObjetos(){
        toolbar = (Toolbar) findViewById(R.id.tb);
        pontos = (TextView) findViewById(R.id.pontos);
        questao = (TextView) findViewById(R.id.questao);
        op1 = (Button) findViewById(R.id.op1);
        op2 = (Button) findViewById(R.id.op2);
        op3 = (Button) findViewById(R.id.op3);
        op4 = (Button) findViewById(R.id.op4);
        imageView = (ImageView) findViewById(R.id.imagem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questoesJogadas = new ArrayList();
        random = new Random();
        respostas = 1;
        questoes = new Repository(this).getQuestoes();
        new Repository(this).criarPontos();
        new Repository(this).salvarPontos(0,0);
        setPontos();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                sair();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setQuestao(){
        i = random.nextInt(questoes.size());
        if(!questoesJogadas.contains(questoes.get(i).getId())){
            if(questoes.get(i).getArquivo() != null){
                if(questoes.get(i).getTipo().contains("image")){
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(this).load(questoes.get(i).getArquivo()).into(imageView);
                }else{
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(this, Uri.parse(questoes.get(i).getArquivo()));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }catch (IOException e){
                        Log.i("kfskjldsnksdfns",e.getMessage());
                    }
                }
            }
            questao.setText(questoes.get(i).getPergunta());
            op1.setText(questoes.get(i).getOp1());
            op2.setText(questoes.get(i).getOp2());
            op3.setText(questoes.get(i).getOp3());
            op4.setText(questoes.get(i).getOp4());
            questoesJogadas.add(questoes.get(i).getId());
        }else if(respostas < 20) setQuestao();
            //Intent intent = new Intent(this,Funcionalidades.class);
            //intent.putExtra("jogou","jogou");
            //startActivity(intent);
            /*questoesJogadas.clear();
            setQuestao();*/

    }

    private void setPontos(){
        pontos.setText("Pontos: "+new Repository(this).getPontos());
    }

    private void setOpcoes(){
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
                respostas++;
               verificaResposta(op1.getText().toString());
            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
                respostas++;
                verificaResposta(op2.getText().toString());
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
                respostas++;
                verificaResposta(op3.getText().toString());
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
                respostas++;
                verificaResposta(op4.getText().toString());
            }
        });
    }


    private void verificaResposta(String resposta){
        Intent intent = new Intent(Play_Quiz.this,Explicacao.class);
        intent.putExtra("exp",questoes.get(i).getExplicacao());
        intent.putExtra("resposta",resposta);
        intent.putExtra("certo",questoes.get(i).getCorreta());
        intent.putExtra("qr",respostas-1);
        intent.putExtra("qj",questoesJogadas);
        startActivity(intent);
        setQuestao();
    }

    @Override
    protected void onResume() {
        setPontos();
        imageView.setVisibility(View.GONE);
        super.onResume();
    }

    private void sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseja sair?");
        builder.setMessage("Ao sair, o seu progresso será salvo e você continuará de onde parou.");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNeutralButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}