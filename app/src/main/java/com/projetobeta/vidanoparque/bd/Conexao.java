package com.projetobeta.vidanoparque.bd;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetobeta.vidanoparque.Funcionalidades;

public class Conexao {
    private AlertDialog dialog;
    private Activity activity;


    public Conexao(){

    }

    public Conexao(@NonNull Activity activity,AlertDialog dialog){
        this.dialog = dialog;
        this.activity = activity;
    }

    private void salvar(Usuario usuario){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Usuarios");
        String id = db.push().getKey();
        db.child(id).setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
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
                            && ds.child("senha").getValue().toString().equals(senha)){
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


}
