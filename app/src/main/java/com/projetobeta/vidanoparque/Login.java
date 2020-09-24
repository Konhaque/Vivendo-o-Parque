package com.projetobeta.vidanoparque;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Login extends AppCompatActivity {
    private TextView cadastrar;
    private EditText email;
    private EditText senha;
    private Button login;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private AlertDialog dialog;
    private final int RC_SING_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_login);
        iniciarObjetos();
        setCadastrar();
        setLoginGoogle();
        setSignInButton();
        setLogin();
    }

    private void iniciarObjetos(){
        cadastrar = (TextView) findViewById(R.id.registro);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        login = (Button) findViewById(R.id.logar);
        signInButton = (SignInButton) findViewById(R.id.singinbutton);
    }

    private void setCadastrar(){
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Registro.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setLoginGoogle(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
    }

    private void singIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SING_IN);
    }

    private void setSignInButton(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SING_IN){
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSingInResult(accountTask);
        }
    }

    private void handleSingInResult(Task<GoogleSignInAccount> task){
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
            builder.setCancelable(true);
            dialog = builder.create();
            dialog.show();
            GoogleSignInAccount account = task.getResult(ApiException.class);
            new Conexao(this,dialog).verificaLoginGoogle(account);
        }catch (ApiException e){
            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();
            e.getMessage();
        }
    }

    private void setLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
                builder.setCancelable(true);
                dialog = builder.create();
                dialog.show();
                new Conexao(Login.this,dialog).login(email.getText().toString(),senha.getText().toString());
            }
        });
    }
}