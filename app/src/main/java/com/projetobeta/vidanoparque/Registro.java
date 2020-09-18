package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        setCadastrar();
    }

    private void iniciarObjetos(){
        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        conf_Senha = (EditText) findViewById(R.id.confirmar_senha);
        cadastrar = (Button) findViewById(R.id.cadastrar);
    }

    private void verifica(){
        if(nome.getText().length() == 0){
            nome.setError("Este campo precisa ser preenchido!");
            nome.requestFocus();
        }else if(email.getText().length() == 0){
            email.setError("Este campo precisa ser preenchido");
            email.requestFocus();
        }else if(senha.getText().length() == 0){
            senha.setError("Este campo precisa ser preenchido");
            senha.requestFocus();
        }else if(conf_Senha.getText().toString().equals(senha.getText().toString())){
            conf_Senha.setError("Os campos devem ser iguais");
            senha.setError("Os campos devem ser iguais");
            conf_Senha.requestFocus();
            senha.requestFocus();
        }else {
            salvar();
        }

    }

    private void salvar(){
        startActivity(new Intent(this,Funcionalidades.class));
        finish();
    }

    private void setCadastrar(){
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifica();
            }
        });
    }


   



}