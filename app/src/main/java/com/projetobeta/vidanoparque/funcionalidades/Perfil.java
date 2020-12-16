package com.projetobeta.vidanoparque.funcionalidades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.projetobeta.vidanoparque.Login;
import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;
import com.projetobeta.vidanoparque.funcionalidades.perfil.Conquistas;
import com.projetobeta.vidanoparque.funcionalidades.perfil.Informacoes;
import com.projetobeta.vidanoparque.funcionalidades.perfil.Midia;
import com.projetobeta.vidanoparque.generalfunctions.AbreTela;
import com.projetobeta.vidanoparque.generalfunctions.SharedPrefs;

public class Perfil extends Fragment {
    private TextView nome;
    private ImageView ft_perfil;
    private FrameLayout informacoes;
    private FrameLayout midia;
    private FrameLayout conquistas;
    private Usuario usuario;
    private AlertDialog dialog;
    private ImageView sair;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil,container,false);
    }

    @Override
    public void onStart() {
        iniciar_Objetos();
        setInfo();
        setFt_perfil();
        setInformacoes();
        setConquistas();
        setMidia();
        setSair();
        super.onStart();
    }

    private void iniciar_Objetos(){
        nome = (TextView) getActivity().findViewById(R.id.nome);
        ft_perfil = (ImageView) getActivity().findViewById(R.id.foto_perfil);
        usuario = new Repository(getContext()).getPerfil();
        informacoes = (FrameLayout) getActivity().findViewById(R.id.informacoes);
        midia = (FrameLayout) getActivity().findViewById(R.id.midia);
        conquistas = (FrameLayout) getActivity().findViewById(R.id.conquistas);
        informacoes.setBackground(getActivity().getDrawable(R.drawable.btn_style));
        sair = (ImageView) getActivity().findViewById(R.id.sair);
        new AbreTela(getActivity().getSupportFragmentManager(),new Informacoes(),R.id.containerperfil);
    }

    private void setInfo(){
        nome.setText(usuario.getNome());
        if (usuario.getFoto_perfil() != null && usuario.getFoto_perfil().length()>0)
            Glide.with(getActivity()).load(usuario.getFoto_perfil()).into(ft_perfil);
    }

    private void setFt_perfil(){
        ft_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alterar foto");
                builder.setMessage("Deseja alterar a foto do perfil?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,10);
                    }
                });
              builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {

                  }
              });
            dialog = builder.create();
            dialog.show();
            }
        });
    }

    private void setInformacoes(){
        informacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informacoes.setBackground(getActivity().getDrawable(R.drawable.btn_style));
                conquistas.setBackground(null);
                midia.setBackground(null);
                new AbreTela(getActivity().getSupportFragmentManager(),new Informacoes(),R.id.containerperfil);
            }
        });
    }

    private void setMidia(){
        midia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                midia.setBackground(getActivity().getDrawable(R.drawable.btn_style));
                informacoes.setBackground(null);
                conquistas.setBackground(null);
                new AbreTela(getActivity().getSupportFragmentManager(),new Midia(),R.id.containerperfil);
            }
        });
    }

    private void setConquistas(){
        conquistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conquistas.setBackground(getActivity().getDrawable(R.drawable.btn_style));
                midia.setBackground(null);
                informacoes.setBackground(null);
                new AbreTela(getActivity().getSupportFragmentManager(),new Conquistas(),R.id.containerperfil);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(resultCode == getActivity().RESULT_OK){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = getLayoutInflater();
                    builder.setView(layoutInflater.inflate(R.layout.activity_main,null));
                    builder.setCancelable(true);
                    dialog = builder.create();
                    dialog.show();
                    new Conexao(getActivity(),dialog).salvarFoto_Perfil(usuario.getId(),data.getDataString(),ft_perfil);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setSair(){
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Deseja sair?");
                builder.setMessage("Deseja sair do aplicativo?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sair();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void sair(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            mGoogleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    new SharedPrefs().setSharedPrefs(getContext(),"VivendoParque","Logado",null);
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                }
            });
        }else{
            new SharedPrefs().setSharedPrefs(getContext(),"VivendoParque","Logado",null);
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
        }

    }

}
