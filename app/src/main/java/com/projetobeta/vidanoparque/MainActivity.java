package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_main);
        opcoes();
    }

    private void opcoes(){
        if(getSharedPrefs(this,"vivendoparque", "ini") != null){
            passarTela(new Intent(MainActivity.this,Login.class));
        }else{
            setSharedPrefs(this, "vivendoparque", "ini", "foi");
            passarTela(new Intent(MainActivity.this,Escolher_Parque.class));
        }
    }

    private void setSharedPrefs(Context contexto,
                                String nomeProjeto,
                                String chave,
                                String valor) {
        SharedPreferences sharedPreferences;
        sharedPreferences = contexto.getSharedPreferences(nomeProjeto, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor);
        editor.apply();
    }

    private String getSharedPrefs(Context contexto,
                                  String nomeProjeto,
                                  String chave) {
        SharedPreferences sharedPreferences;
        sharedPreferences = contexto.getSharedPreferences(nomeProjeto, Context.MODE_PRIVATE);
        return sharedPreferences.getString(chave, null);
    }

    private void passarTela(final Intent intent){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);
    }


}