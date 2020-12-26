package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Publicacao;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Criar_Publicacao extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText texto;
    private ImageView imageView;
    private TextView addImagem;
    private Publicacao publicacao;
    private AlertDialog dialog;
    private Usuario usuario;
    private Button publicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_criar__publicacao);
        iniciarObjetos();
        setAddImagem();
        setPublicar();
    }

    private void iniciarObjetos(){
        toolbar = (Toolbar) findViewById(R.id.tb);
        texto = (EditText) findViewById(R.id.mensagem);
        imageView = (ImageView) findViewById(R.id.imagem);
        addImagem = (TextView) findViewById(R.id.addMidia);
        publicar = (Button) findViewById(R.id.publicar);
        publicacao = new Publicacao();
        usuario = new Repository(this).getPerfil();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void setAddImagem(){
        addImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK){
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(this).load(data.getData()).into(imageView);
                    publicacao.setMidia(data.getDataString());
                    ContentResolver cR = this.getContentResolver();
                    publicacao.setTipo(cR.getType(data.getData()));
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPublicar(){
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarPublicacoa();
            }
        });
    }

    private void verificarPublicacoa(){
        if(publicacao.getMidia() == null && texto.getText().toString().length() == 0) erro();
        else publicar();
    }

    private void publicar(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Publicacao");
        final String id = databaseReference.push().getKey();
        publicacao.setId_usuario(new Repository(Criar_Publicacao.this).getIdUsuario());
        publicacao.setTexto(texto.getText().toString());
        publicacao.setFtPerfil(usuario.getFoto_perfil());
        publicacao.setNome(usuario.getNome());
        AlertDialog.Builder builder = new AlertDialog.Builder(Criar_Publicacao.this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_main, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        if (publicacao.getMidia() != null) {
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Publicacao").child(id);
            storageReference.putFile(Uri.parse(publicacao.getMidia())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            publicacao.setMidia(uri.toString());
                            databaseReference.child(id).setValue(publicacao).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Criar_Publicacao.this,Funcionalidades.class);
                                    intent.putExtra("pub","pub");
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            });
        }else{
            databaseReference.child(id).setValue(publicacao).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = new Intent(Criar_Publicacao.this,Funcionalidades.class);
                    intent.putExtra("pub","pub");
                    startActivity(intent);
                }
            });
        }
    }

    private void erro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Algo deu Errado :(");
        builder.setMessage("Você não pode fazer uma publicação vazia. Adicione um texto, uma imagem, um vídeo");
        builder.setNeutralButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnim;
        dialog.show();
    }

}