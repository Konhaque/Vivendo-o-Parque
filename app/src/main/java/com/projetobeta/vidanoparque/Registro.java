package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Registro extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText conf_Senha;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_registro);
        iniciarObjetos();
    }

    private void iniciarObjetos(){
        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        conf_Senha = (EditText) findViewById(R.id.confirmar_senha);
        cadastrar = (Button) findViewById(R.id.cadastrar);
    }

   



}