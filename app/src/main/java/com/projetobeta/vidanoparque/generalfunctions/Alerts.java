package com.projetobeta.vidanoparque.generalfunctions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.projetobeta.vidanoparque.R;

public class Alerts {
    private AlertDialog dialog;

    public Alerts(@NonNull AlertDialog dialog){
        this.dialog = dialog;
    }

    public void Carregando(@NonNull Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_main,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

}
