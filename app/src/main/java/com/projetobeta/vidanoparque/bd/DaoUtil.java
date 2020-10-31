package com.projetobeta.vidanoparque.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DaoUtil extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "VIVENDOOPARQUE.db";
    private static final int VERSAO = 1;

    public DaoUtil(@Nullable Context context){
        super(context,NOME_BANCO,null,VERSAO);
    }

    public SQLiteDatabase getConexaoDataBase(){
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
