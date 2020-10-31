package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class FaunaeFlora extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_fauna_flora);
        iniciaObjetos();
        pegar_Fauna();
    }

    private void iniciaObjetos(){
        toolbar = (Toolbar) findViewById(R.id.tb);
        linearLayout = (LinearLayout) findViewById(R.id.setfaunaeflora);
    }

    private void pegar_Fauna(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Fauna_Parque/Pituacu/");
        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Fauna");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.i("dksadhsadsakdh",dataSnapshot.getValue().toString());
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                               if(dataSnapshot1.getKey().equals(dataSnapshot.getValue().toString())){
                                   TextView textView = new TextView(FaunaeFlora.this);
                                   textView.setText(dataSnapshot1.child("nome_popular").getValue().toString());
                                   linearLayout.addView(textView);
                                   //Log.i("dksadhsadsakdh",dataSnapshot1.child("nome_popular").getValue().toString());
                               }
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}