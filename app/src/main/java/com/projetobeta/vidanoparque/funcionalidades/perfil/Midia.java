package com.projetobeta.vidanoparque.funcionalidades.perfil;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Repository;

public class Midia extends Fragment {

    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.midia,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        pegarMidia();
        super.onStart();
    }

    private void iniciarObjetos(){
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.containerMidia);
    }

    private void pegarMidia(){
        linearLayout.removeAllViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Publicacao");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existe = false;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("id_usuario").getValue().toString().equals(new Repository(getContext()).getIdUsuario())){
                        if(dataSnapshot.child("midia").getValue() != null){
                            inserir(dataSnapshot);
                            existe = true;
                        }
                    }
                }
                if(!existe) inserir(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void inserir(DataSnapshot snapshot){
        ImageView imageView = new ImageView(getContext());
        final VideoView videoView = new VideoView(getContext());
        if(snapshot == null){
            TextView textView = new TextView(getContext());
            Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.nerkoone);
            textView.setText("Você não possui mídias ainda :(");
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(typeface);
            textView.setTextSize(25);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
            return;
        }
        if(snapshot.child("tipo").getValue().toString().contains("image")){
            Glide.with(getActivity()).load(snapshot.child("midia").getValue().toString()).into(imageView);
            linearLayout.addView(imageView,300,400);
        }else{
            videoView.setVideoURI(Uri.parse(snapshot.child("midia").getValue().toString()));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0,0);
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.start();
                }
            });
            linearLayout.addView(videoView, 200, 400);
            videoView.start();
        }
    }
}
