package com.projetobeta.vidanoparque.funcionalidades;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.generalfunctions.AbreTela;

public class AgendaCultural extends Fragment {
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.agenda,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setToolbar();
        pegarAgenda();
        super.onStart();
    }

    private void iniciarObjetos(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.tb_agenda);
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.containerAgenda);
    }

    private void setToolbar(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                new AbreTela(getActivity().getSupportFragmentManager(),new Inicio(),R.id.set_Tela);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void pegarAgenda(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_main,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        linearLayout.removeAllViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Eventos");
        databaseReference.orderByChild("data_Inicio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.montserrat_black);
                    TextView textView = new TextView(getContext());
                    ImageView imageView = new ImageView(getContext());
                    textView.setText("Nenhum evento disponível no momento!");
                    textView.setTypeface(typeface);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.BLACK);
                    Glide.with(getActivity()).load(R.drawable.macaco_gif).into(imageView);
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.addView(textView);
                    linearLayout.addView(imageView);
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
