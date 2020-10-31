package com.projetobeta.vidanoparque.bd;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projetobeta.vidanoparque.Explicacao;
import com.projetobeta.vidanoparque.Funcionalidades;
import com.projetobeta.vidanoparque.Play_Quiz;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.generalfunctions.SharedPrefs;

import java.io.FileWriter;
import java.util.List;

public class Conexao {
    private AlertDialog dialog;
    private Activity activity;


    public Conexao(){

    }

    public Conexao(@NonNull Activity activity,AlertDialog dialog){
        this.dialog = dialog;
        this.activity = activity;
    }

    private void salvar(final Usuario usuario){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Usuarios");
        final String id = db.push().getKey();
        db.child(id).setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                new Repository(activity).criarPerfil();
                usuario.setId(id);
                new Repository(activity).salvarPerfil(usuario);
                activity.startActivity(new Intent(activity, Funcionalidades.class));
                activity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Algo deu errado!");
                builder.setMessage(e.getMessage());
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        });

    }

    public void verificaEmail(final Usuario usuario, final EditText email){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Usuarios");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existe = false;
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("email").getValue().toString().equalsIgnoreCase(email.getText().toString())){
                        dialog.dismiss();
                        email.setError("Este email já está cadastrado no nosso banco de dados");
                        email.requestFocus();
                        existe = true;
                        break;
                    }
                }
                if(!existe) salvar(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void verificaLoginGoogle(@NonNull final GoogleSignInAccount account){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                boolean existe = false;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        if(ds.child("id_google").getValue().toString().equalsIgnoreCase(account.getId())){
                            Usuario usuario = new Usuario();
                            usuario.setNome(ds.child("nome").getValue().toString());
                            usuario.setEmail(ds.child("email").getValue().toString());
                            usuario.setFoto_perfil(ds.child("foto_perfil").getValue().toString());
                            usuario.setId(ds.getKey());
                            new Repository(activity).criarPerfil();
                            new Repository(activity).salvarPerfil(usuario);
                            jogouQuiz(ds.getKey());
                            activity.startActivity(new Intent(activity,Funcionalidades.class));
                            activity.finish();
                            existe = true;
                            break;
                        }
                    }
                    if(!existe){
                        Usuario usuario = new Usuario();
                        usuario.setEmail(account.getEmail());
                        usuario.setId_google(account.getId());
                        usuario.setNome(account.getDisplayName());
                        if(account.getPhotoUrl() != null) usuario.setFoto_perfil(account.getPhotoUrl().toString());
                        else usuario.setFoto_perfil("");
                        salvar(usuario);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    public void login(final String email, final String senha){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean existe = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("email").getValue().toString().equalsIgnoreCase(email)
                            && ds.child("senha").getValue().toString().equals(senha)
                            ){
                        Usuario usuario = new Usuario();
                        usuario.setNome(ds.child("nome").getValue().toString());
                        usuario.setEmail(ds.child("email").getValue().toString());
                        usuario.setFoto_perfil(ds.child("foto_perfil").getValue().toString());
                        usuario.setId(ds.getKey());
                        jogouQuiz(ds.getKey());
                        new Repository(activity).criarPerfil();
                        new Repository(activity).salvarPerfil(usuario);
                        activity.startActivity(new Intent(activity,Funcionalidades.class));
                        activity.finish();
                        existe = true;
                        break;
                    }
                }

                if(!existe){
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Algo deu errado!");
                    builder.setMessage("Verifique seu email e senha e tente novamente.");
                    builder.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void salvarFoto_Perfil(final String id_usuario, String imagem, final ImageView imageView){
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Usuarios").child(id_usuario);
        storageReference.putFile(Uri.parse(imagem)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        new Repository(activity).updateFotoPerfil(uri.toString());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
                        databaseReference.child(id_usuario).child("foto_perfil").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Glide.with(activity).load(uri).into(imageView);
                              dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    public void questoesQuiz(){
        new Repository(activity).criarQuestoesQuiz();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Questoes_Quiz quiz = new Questoes_Quiz();
                    quiz.setId(ds.getKey());
                    quiz.setPergunta(ds.child("pergunta").getValue().toString());
                    quiz.setOp1(ds.child("op1").getValue().toString());
                    quiz.setOp2(ds.child("op2").getValue().toString());
                    quiz.setOp3(ds.child("op3").getValue().toString());
                    quiz.setOp4(ds.child("op4").getValue().toString());
                    quiz.setCorreta(ds.child("correta").getValue().toString());
                    quiz.setExplicacao(ds.child("explicacao").getValue().toString());
                    new Repository(activity).salvarQuestao(quiz);
                }
                dialog.dismiss();
                activity.startActivity(new Intent(activity, Play_Quiz.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void salvarQuizUsuario(Usuario_Quiz usuario_quiz){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuario_Quiz");
        databaseReference.child(databaseReference.push().getKey()).setValue(usuario_quiz).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                new SharedPrefs().setSharedPrefs(activity,"VivendoParque","Jogou_Quiz","Jogou");
                Intent intent1 = new Intent(activity,Funcionalidades.class);
                intent1.putExtra("jogou","jogou");
                activity.startActivity(intent1);
                dialog.dismiss();
            }
        });
    }

    private void jogouQuiz(final String idUsuario){
        new Repository(activity).criarPontos();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuario_Quiz");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean jogou = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("id_Usuario").getValue().toString().equalsIgnoreCase(idUsuario)){
                        new SharedPrefs().setSharedPrefs(activity,"VivendoParque","Jogou_Quiz","Jogou");
                        new Repository(activity).salvarPontos(Integer.parseInt(dataSnapshot.child("pontos").getValue().toString()),
                                Integer.parseInt(dataSnapshot.child("acertos").getValue().toString()));
                        jogou = true;
                        break;
                    }
                }
                if(!jogou) new SharedPrefs().setSharedPrefs(activity,"VivendoParque","Jogou_Quiz",null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
