package com.projetobeta.vidanoparque.generalfunctions;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    public SharedPrefs(){

    }

    public void setSharedPrefs(Context contexto,
                                String nomeProjeto,
                                String chave,
                                String valor) {
        SharedPreferences sharedPreferences;
        sharedPreferences = contexto.getSharedPreferences(nomeProjeto, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor);
        editor.apply();
    }

    public String getSharedPrefs(Context contexto,
                                  String nomeProjeto,
                                  String chave) {
        SharedPreferences sharedPreferences;
        sharedPreferences = contexto.getSharedPreferences(nomeProjeto, Context.MODE_PRIVATE);
        return sharedPreferences.getString(chave, null);
    }
}
