package com.projetobeta.vidanoparque.funcionalidades;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.projetobeta.vidanoparque.Criar_Publicacao;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;
import com.projetobeta.vidanoparque.generalfunctions.Alerts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Rede_Social extends Fragment {
    private ImageView foto_Perfil;
    private Usuario usuario;
    private TextView lblRede;
    private ImageView rede;
    private LinearLayout linearLayout;
    private AlertDialog dialog;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rede_social,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setFoto_Perfil();
        setLblRede();
        setRede();
        getPublicacaoes();
        super.onStart();
    }

    private void iniciarObjetos(){
        foto_Perfil = (ImageView) getActivity().findViewById(R.id.foto_perfilRede);
        usuario = new Repository(getContext()).getPerfil();
        lblRede = (TextView) getActivity().findViewById(R.id.lbl_rede);
        rede = (ImageView) getActivity().findViewById(R.id.adicionar_publicacao);
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.publicacoes);
        linearLayout.removeAllViews();
    }

    private void setFoto_Perfil(){
        if (usuario.getFoto_perfil() != null && usuario.getFoto_perfil().length()>0)
            Glide.with(getActivity()).load(usuario.getFoto_perfil()).into(foto_Perfil);
    }


    private void criarPublicacao(){
        startActivity(new Intent(getActivity(), Criar_Publicacao.class));
    }

    private void setLblRede(){
        lblRede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPublicacao();
            }
        });
    }

    private void setRede(){
        rede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPublicacao();
            }
        });
    }

    private void getPublicacaoes(){
        final List<DataSnapshot> dataSnapshots = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Publicacao");
        databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataSnapshots.add(dataSnapshot);
                }
                Collections.reverse(dataSnapshots);
                for(int i = 0; i<dataSnapshots.size();i++){
                    inserir(dataSnapshots.get(i));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inserir(final DataSnapshot snapshot) {
        final TextView textView = new TextView(getContext());
        final TextView nome = new TextView(getContext());
        CircleImageView ft_perfil = new CircleImageView(getContext());
        LinearLayout top = new LinearLayout(getContext());
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.montserrat_black);
        Typeface typeface1 = ResourcesCompat.getFont(getContext(), R.font.nerkoone);
        final ImageView imagem = new ImageView(getContext());
        TextView espaco = new TextView(getContext());
        final TextView likes = new TextView(getContext());
        LinearLayout novo = new LinearLayout(getContext());
        ImageView imagem1 = new ImageView(getContext());
        ImageView compartilhar = new ImageView(getContext());
        final VideoView videoView = new VideoView(getContext());
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.addView(ft_perfil, 90, 90);
        top.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        if(snapshot.child("ftPerfil").getValue() != null && snapshot.child("ftPerfil").getValue().toString().length()>0)
            Glide.with(getActivity()).load(snapshot.child("ftPerfil").getValue().toString()).into(ft_perfil);
        else{
            Glide.with(getActivity()).load(R.drawable.icone_perfil).into(ft_perfil);
            ft_perfil.setColorFilter(Color.BLACK);
        }
        ft_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informacoesUser(snapshot);
            }
        });
        nome.setText(" " + snapshot.child("nome").getValue().toString());
        nome.setTextSize(15);
        nome.setPadding(10, 30, 0, 0);
        nome.setTypeface(typeface);
        nome.setTextColor(Color.BLACK);
        top.addView(nome);
        linearLayout.addView(top);
        textView.setText(snapshot.child("texto").getValue() + "");
        textView.setTextSize(25);
        textView.setPadding(10, 5, 0, 0);
        textView.setTypeface(typeface1);
        textView.setTextColor(Color.BLACK);
        linearLayout.addView(textView);
        if (snapshot.child("midia").getValue() != null) {
            if (snapshot.child("tipo").getValue().toString().contains("image")) {
                linearLayout.setGravity(Gravity.CENTER);
                Glide.with(getActivity()).load(snapshot.child("midia").getValue().toString()).into(imagem1);
                linearLayout.addView(imagem1);
            } else {
                linearLayout.setGravity(Gravity.CENTER);
                videoView.setVideoURI(Uri.parse(snapshot.child("midia").getValue().toString()));
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setVolume(0,0);
                    }
                });
                MediaController mediaController = new MediaController(getContext());
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.start();
                    }
                });
                linearLayout.addView(videoView, LinearLayout.LayoutParams.MATCH_PARENT, 300);
                videoView.start();
            }
        }
        Glide.with(getActivity()).load(R.drawable.folha).into(imagem);
        imagem.setPadding(10, 0, 0, 0);
        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagem.getColorFilter() == null) {
                    imagem.setColorFilter(Color.parseColor("#82c91e"));
                    like(snapshot, 1, likes);
                } else {
                    imagem.setColorFilter(null);
                    like(snapshot, 2, likes);
                }
            }
        });
        compartilhar.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_share_24));
        compartilhar.setPadding(10, 0, 0, 0);
        novo.setOrientation(LinearLayout.HORIZONTAL);
        compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartilhar(snapshot.getKey(),snapshot.child("tipo").getValue().toString());
            }
        });
        if (snapshot.child("likes").child("qntLikes").getValue() != null
                && Integer.parseInt(snapshot.child("likes").child("qntLikes").getValue().toString())>0) procuraUsuariosLike(snapshot,likes,imagem);
        else likes.setVisibility(View.GONE);
        novo.addView(imagem, 60, 60);
        novo.addView(likes);
        novo.addView(compartilhar, 60, 60);
        linearLayout.addView(novo);
        espaco.setText("");
        linearLayout.addView(espaco);
    }


    private void like(final DataSnapshot sp, final int caso, final TextView textView){
        DatabaseReference getLike = FirebaseDatabase.getInstance().getReference("Publicacao/"+sp.getKey());
        getLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int like = 0;
                final DatabaseReference databaseReference;
                if(snapshot.child("likes").child("qntLikes").getValue() != null)
                    like = Integer.parseInt(snapshot.child("likes").child("qntLikes").getValue().toString());
                switch (caso){
                    case 1:
                        like++;
                        databaseReference = FirebaseDatabase.getInstance().getReference("Publicacao/"+sp.getKey());
                        final int llike = like;
                        databaseReference.child("likes").child("qntLikes").setValue(like).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        databaseReference.child("likes").child("usuarios").child(llike+"")
                                                .setValue(new Repository(getContext()).getIdUsuario());
                                        textView.setText(llike+"");
                                        if(llike > 0) textView.setVisibility(View.VISIBLE);
                                    }
                                });
                        break;
                    case 2:
                        like--;
                        databaseReference = FirebaseDatabase.getInstance().getReference("Publicacao/"+snapshot.getKey());
                        final int llk = like;
                        databaseReference.child("likes").child("qntLikes").setValue(like)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                textView.setText(llk+"");
                                if(llk <= 0) textView.setVisibility(View.GONE);
                                removeLike(sp,databaseReference);
                            }
                        });
                        break;
                    default: break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void procuraUsuariosLike(DataSnapshot snapshot, TextView textView, ImageView imageView){
        for(int i = 1; i<= Integer.parseInt(snapshot.child("likes").child("qntLikes").getValue().toString());i++){
            Log.i("kfgkgmfkf",snapshot.child("likes").child("usuarios").child(i+"").getValue().toString());
            Log.i("kfgkgmfkf",new Repository(getContext()).getIdUsuario());
            if(snapshot.child("likes").child("usuarios").child(i+"").getValue() != null){
                if(snapshot.child("likes").child("usuarios").child(i+"").getValue().toString().
                        equalsIgnoreCase(new Repository(getContext()).getIdUsuario())){
                    imageView.setColorFilter(Color.parseColor("#82c91e"));
                    break;
                }
            }
        }
        textView.setText(snapshot.child("likes").child("qntLikes").getValue().toString());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(10);
    }

    private void removeLike(DataSnapshot snapshot, DatabaseReference databaseReference){
        for(int i = 1; i<= Integer.parseInt(snapshot.child("likes").child("qntLikes").getValue().toString());i++){
            if(snapshot.child("likes").child("usuarios").child(i+"").getValue().toString().
                    equals(new Repository(getContext()).getIdUsuario())){
               databaseReference.child("likes").child("usuarios").child(i+"").removeValue();
               break;
            }
        }
    }


    private void compartilhar(String url, final String tipo){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Publicacao/");
        File file = null;
        if(tipo.contains("jpeg"))  file = new File(Environment.getExternalStorageDirectory(), "forEmail.JPEG");
        if (tipo.contains("png")) file = new File(Environment.getExternalStorageDirectory(), "forEmail.PNG");
        if(tipo.contains("mp4")) file = new File(Environment.getExternalStorageDirectory(), "forEmail.MP4");
        final File finalFile = file;
        if(url != null) {
            storageReference.child(url).getFile(finalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(finalFile));
                    if (tipo.contains("image")) intent.setType("image/*");
                    if (tipo.contains("video")) intent.setType("video/*");
                    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(intent, null));
                }
            });
        }
    }

    private void informacoesUser(DataSnapshot sp){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                getReference("Usuarios/"+sp.child("id_usuario").getValue().toString());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.informacoes_user,null);
                ImageView imageView = view.findViewById(R.id.perfil);
                TextView nome = view.findViewById(R.id.nome);
                TextView email = view.findViewById(R.id.email);
                if(snapshot.child("foto_perfil").getValue() != null && snapshot.child("foto_perfil").getValue().toString().length()>0)
                    Glide.with(getActivity()).load(snapshot.child("foto_perfil").getValue().toString()).into(imageView);
                else{
                    Glide.with(getActivity()).load(R.drawable.icone_perfil).into(imageView);
                    imageView.setColorFilter(Color.BLACK);
                }
                nome.setText(snapshot.child("nome").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
