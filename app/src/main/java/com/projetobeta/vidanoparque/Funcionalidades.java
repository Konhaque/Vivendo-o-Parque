package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projetobeta.vidanoparque.funcionalidades.Fim_de_Jogo;
import com.projetobeta.vidanoparque.funcionalidades.Inicio;
import com.projetobeta.vidanoparque.funcionalidades.Perfil;
import com.projetobeta.vidanoparque.funcionalidades.Quiz;
import com.projetobeta.vidanoparque.funcionalidades.Rede_Social;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;
import com.projetobeta.vidanoparque.generalfunctions.SharedPrefs;

public class Funcionalidades extends AppCompatActivity {
    private Intent intent;
    private BottomNavigationView menu;
    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.inicio:
                    abretela(new Inicio());
                    return true;
                case R.id.quiz:
                    if(new SharedPrefs().getSharedPrefs(Funcionalidades.this,"VivendoParque","Jogou_Quiz") != null) abretela(new Fim_de_Jogo());
                    else abretela(new Quiz());
                    return true;
                case R.id.rede_social:
                    abretela(new Rede_Social());
                    return true;
                case R.id.perfil:
                    abretela(new Perfil());
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
    }

    private void abretela(Fragment fragment){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.set_Tela,fragment).commit();
    }

    private void iniciaObjetos(){
        menu = (BottomNavigationView) findViewById(R.id.nav_view);
        menu.setOnNavigationItemSelectedListener(itemSelectedListener);
        menu.setSelectedItemId(R.id.inicio);
        intent = getIntent();
        if(intent.hasExtra("jogou")) menu.setSelectedItemId(R.id.quiz);
    }


}