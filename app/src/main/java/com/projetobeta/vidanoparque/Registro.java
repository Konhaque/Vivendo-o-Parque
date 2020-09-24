package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Usuario;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Registro extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText conf_Senha;
    private Button cadastrar;
    private Usuario usuario;
    private AlertDialog dialog;


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
        usuario = new Usuario();
    }

    private void verifica(){
        if(nome.getText().length() == 0){
            nome.setError("Este campo precisa ser preenchido!");
            nome.requestFocus();
            dialog.dismiss();
        }else if(email.getText().length() == 0){
            email.setError("Este campo precisa ser preenchido");
            email.requestFocus();
            dialog.dismiss();
        }else if(senha.getText().length() == 0){
            senha.setError("Este campo precisa ser preenchido");
            senha.requestFocus();
            dialog.dismiss();
        }else if(!conf_Senha.getText().toString().equals(senha.getText().toString())){
            conf_Senha.setError("Os campos devem ser iguais");
            senha.setError("Os campos devem ser iguais");
            conf_Senha.requestFocus();
            senha.requestFocus();
            dialog.dismiss();
        }else {
            carregaUsuario();
            new Conexao(this,dialog).verificaEmail(usuario,email);
        }

    }

    private void setCadastrar(){
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
                builder.setCancelable(true);
                dialog = builder.create();
                dialog.show();
                verifica();
            }
        });
    }

    private void carregaUsuario(){
        usuario.setNome(nome.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setId_google("");
    }






   



}