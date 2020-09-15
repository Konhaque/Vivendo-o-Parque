package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projetobeta.vidanoparque.funcionalidades.Inicio;
import com.projetobeta.vidanoparque.funcionalidades.Quiz;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Funcionalidades extends AppCompatActivity {

    private BottomNavigationView menu;
    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.inicio:
                    abretela(new Inicio());
                    return true;
                case R.id.quiz:
                    abretela(new Quiz());
                    return true;
                case R.id.rede_social:
                    return true;
                case R.id.perfil:
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
    }
}