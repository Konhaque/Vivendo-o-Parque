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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Fauna;
import com.projetobeta.vidanoparque.bd.Questoes_Quiz;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Login extends AppCompatActivity {
    private TextView cadastrar;
    private EditText email;
    private EditText senha;
    private Button login;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView logo;
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
        //setLogo();
    }

    private void iniciarObjetos(){
        cadastrar = (TextView) findViewById(R.id.registro);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        login = (Button) findViewById(R.id.logar);
        signInButton = (SignInButton) findViewById(R.id.singinbutton);
        logo = (ImageView) findViewById(R.id.logo);
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

    private void setLogo(){
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuiz();

            }
        });
    }

    private void setFauna(){
        Fauna fauna = new Fauna();
        fauna.setNome_popular("Sabiá-barranco");
        fauna.setNome_cientifico("Turdus leucomelas");
        fauna.setImagem("https://firebasestorage.googleapis.com/v0/b/vivendo-o-parque-1596659442701.appspot.com/o/Fauna%2FTurdus%20%20leucomelas.png?alt=media&token=3fbcb7c6-ae96-4e63-9451-23032cb7870f");
        fauna.setDescricao("O  Turdus  leucomelas,  popularmente  conhecido  como sabiá-barranco, é uma ave da ordem dos passeriforme e da família da Turdidae, é o sabiá  mais  comum  de  todo  o  interior  brasileiro.  O  sabiá-barranco  possui  uma pelagem com diversos tipos de tonalidade que vão desde acinzentado e oliva para sua cabeça, costas acinzentadas, asas marrons e peito acinzentado. É facilmente encontrado em parques, matas, coqueirais, matas de galeria e cafezais.");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Fauna");
        final String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(fauna).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Fauna_Parque/Pituacu/");
                databaseReference1.child("2").setValue(id).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this,"Sucesso",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void setQuiz(){
        Questoes_Quiz quiz = new Questoes_Quiz();
        quiz.setPergunta("Quantos Hectares possuía anteriormente o parque de Pituaçu?");
        quiz.setOp1("750.000 Hectares");
        quiz.setOp2("530.000 Hectares");
        quiz.setOp3("660.000 Hectares");
        quiz.setOp4("800.000 Hectares");
        quiz.setCorreta("660.000 Hectares");
        quiz.setExplicacao("Logo quando decretado o Parque de Pituaçu possuía cerca de 660.000 Hectares de extensão, hoje ocupa 425 hectares que é equivalente que é apenas 64% da sua área original.");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz");
        databaseReference.child(databaseReference.push().getKey()).setValue(quiz).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Login.this,"Sucesso",Toast.LENGTH_LONG).show();
            }
        });

    }

}