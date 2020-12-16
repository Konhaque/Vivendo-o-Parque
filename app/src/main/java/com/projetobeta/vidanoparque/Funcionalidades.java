package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projetobeta.vidanoparque.funcionalidades.Fim_de_Jogo;
import com.projetobeta.vidanoparque.funcionalidades.Inicio;
import com.projetobeta.vidanoparque.funcionalidades.Perfil;
import com.projetobeta.vidanoparque.funcionalidades.Quiz;
import com.projetobeta.vidanoparque.funcionalidades.Rede_Social;
import com.projetobeta.vidanoparque.generalfunctions.AbreTela;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;
import com.projetobeta.vidanoparque.generalfunctions.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class Funcionalidades extends AppCompatActivity {
    public static final int CODIGO_PERMISSOES_REQUERIDAS = 1;
    private Intent intent;
    private BottomNavigationView menu;
    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.inicio:
                    new AbreTela(getSupportFragmentManager(),new Inicio(),R.id.set_Tela);
                    return true;
                case R.id.quiz:
                    if(new SharedPrefs().getSharedPrefs(Funcionalidades.this,"VivendoParque","Jogou_Quiz") != null)
                        new AbreTela(getSupportFragmentManager(),new Fim_de_Jogo(),R.id.set_Tela);
                    else new AbreTela(getSupportFragmentManager(),new Quiz(),R.id.set_Tela);
                    return true;
                case R.id.rede_social:
                    new AbreTela(getSupportFragmentManager(),new Rede_Social(),R.id.set_Tela);
                    return true;
                case R.id.perfil:
                    new AbreTela(getSupportFragmentManager(),new Perfil(),R.id.set_Tela);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_funcionalidades);
        iniciaObjetos();
        verificarPermissoes();
    }

    /*private void abretela(Fragment fragment){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.set_Tela,fragment).commit();
    }*/

    private void iniciaObjetos(){
        menu = (BottomNavigationView) findViewById(R.id.nav_view);
        menu.setOnNavigationItemSelectedListener(itemSelectedListener);
        menu.setSelectedItemId(R.id.inicio);
        intent = getIntent();
        if(intent.hasExtra("jogou")) menu.setSelectedItemId(R.id.quiz);
        if(intent.hasExtra("pub")) menu.setSelectedItemId(R.id.rede_social);
    }

    public void verificarPermissoes(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_PERMISSOES_REQUERIDAS);
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODIGO_PERMISSOES_REQUERIDAS);
        }

    }


}