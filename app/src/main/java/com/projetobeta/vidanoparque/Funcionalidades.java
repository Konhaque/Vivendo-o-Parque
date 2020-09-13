package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class Funcionalidades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_funcionalidades);
    }
}