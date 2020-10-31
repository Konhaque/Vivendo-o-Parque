package com.projetobeta.vidanoparque.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private DaoUtil daoUtil;

    public Repository(@Nullable Context context){
        daoUtil = new DaoUtil(context);
    }

    public void criarPerfil(){
        String sql = "DROP TABLE IF EXISTS TB_PERFIL";
        daoUtil.getConexaoDataBase().execSQL(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE TB_PERFIL(");
        stringBuilder.append("ID_USUARIO TEXT NOT NULL,");
        stringBuilder.append("NOME TEXT NOT NULL,");
        stringBuilder.append("EMAIL TEXT NOT NULL,");
        stringBuilder.append("FOTO_PERFIL TEXT)");
        daoUtil.getConexaoDataBase().execSQL(stringBuilder.toString());
    }

    public void criarQuestoesQuiz(){
        String sql = "DROP TABLE IF EXISTS TB_QUIZ";
        daoUtil.getConexaoDataBase().execSQL(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE TB_QUIZ(");
        stringBuilder.append("ID_QUESTAO TEXT NOT NULL,");
        stringBuilder.append("QUESTAO TEXT NOT NULL,");
        stringBuilder.append("OP1 TEXT NOT NULL,");
        stringBuilder.append("OP2 TEXT NOT NULL,");
        stringBuilder.append("OP3 TEXT NOT NULL,");
        stringBuilder.append("OP4 TEXT NOT NULL,");
        stringBuilder.append("CORRETA TEXT NOT NULL,");
        stringBuilder.append("EXPLICACAO TEXT NOT NULL)");
        daoUtil.getConexaoDataBase().execSQL(stringBuilder.toString());
    }

    public void criarPontos(){
        String sql = "DROP TABLE IF EXISTS TB_PONTOS";
        daoUtil.getConexaoDataBase().execSQL(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE TB_PONTOS(");
        stringBuilder.append("PONTOS INT,");
        stringBuilder.append("ACERTTOS INT)");
        daoUtil.getConexaoDataBase().execSQL(stringBuilder.toString());
    }

    public void salvarPerfil(Usuario usuario){
        daoUtil.getConexaoDataBase().insert("TB_PERFIL",null,insertPerfil(usuario));
    }

    public void salvarQuestao(Questoes_Quiz quiz){
        daoUtil.getConexaoDataBase().insert("TB_QUIZ",null,insertQuiz(quiz));
    }

    public void salvarPontos(int pontos,int acertos){
        daoUtil.getConexaoDataBase().insert("TB_PONTOS",null,insertPontos(pontos,acertos));
    }

    private ContentValues insertQuiz(Questoes_Quiz quiz){
        ContentValues cv = new ContentValues();
        cv.put("ID_QUESTAO",quiz.getId());
        cv.put("QUESTAO",quiz.getPergunta());
        cv.put("OP1",quiz.getOp1());
        cv.put("OP2",quiz.getOp2());
        cv.put("OP3",quiz.getOp3());
        cv.put("OP4",quiz.getOp4());
        cv.put("CORRETA",quiz.getCorreta());
        cv.put("EXPLICACAO",quiz.getExplicacao());
        return cv;
    }

    private ContentValues insertPontos(int pontos, int acertos){
        ContentValues cv = new ContentValues();
        cv.put("PONTOS",pontos);
        cv.put("ACERTTOS",acertos);
        return cv;
    }

    public void updatePontos(int pontos, int acertos){
        String sql = "UPDATE TB_PONTOS SET PONTOS ="+pontos;
        daoUtil.getConexaoDataBase().execSQL(sql);
        sql = "UPDATE TB_PONTOS SET ACERTTOS ="+acertos;
        daoUtil.getConexaoDataBase().execSQL(sql);
    }


    private ContentValues insertPerfil(Usuario usuario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_USUARIO",usuario.getId());
        contentValues.put("NOME",usuario.getNome());
        contentValues.put("EMAIL",usuario.getEmail());
        contentValues.put("FOTO_PERFIL",usuario.getFoto_perfil());
        return contentValues;
    }

    public Usuario getPerfil(){
        Usuario perfil = new Usuario();
        String sql = "SELECT * FROM TB_PERFIL";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        carregarPerfil(perfil,cursor);
        return perfil;
    }

    public List<Questoes_Quiz> getQuestoes(){
        List<Questoes_Quiz> questoes_quiz = new ArrayList<>();
        String sql = "SELECT * FROM TB_QUIZ";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            questoes_quiz.add(carregaQuestoes(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return questoes_quiz;
    }

    public int getPontos(){
        int pontos;
        String sql ="SELECT * FROM TB_PONTOS";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        pontos = cursor.getInt(cursor.getColumnIndex("PONTOS"));
        return pontos;
    }

    public int getAcertos(){
        int acertos;
        String sql ="SELECT * FROM TB_PONTOS";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        acertos = cursor.getInt(cursor.getColumnIndex("ACERTTOS"));
        return acertos;
    }

    public String getIdUsuario(){
        String sql = "SELECT * FROM TB_PERFIL";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("ID_USUARIO"));
        cursor.close();
        return id;
    }

    private void carregarPerfil(Usuario perfil, Cursor cursor){
        perfil.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        perfil.setId(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
        perfil.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
        perfil.setFoto_perfil(cursor.getString(cursor.getColumnIndex("FOTO_PERFIL")));
    }

    public void updateFotoPerfil(String imagem){
        String sql = "UPDATE TB_PERFIL SET FOTO_PERFIL="+"'"+imagem+"'";
        daoUtil.getConexaoDataBase().execSQL(sql);
    }

    private Questoes_Quiz carregaQuestoes(Cursor cursor){
        Questoes_Quiz quiz = new Questoes_Quiz();
        quiz.setId(cursor.getString(cursor.getColumnIndex("ID_QUESTAO")));
        quiz.setPergunta(cursor.getString(cursor.getColumnIndex("QUESTAO")));
        quiz.setOp1(cursor.getString(cursor.getColumnIndex("OP1")));
        quiz.setOp2(cursor.getString(cursor.getColumnIndex("OP2")));
        quiz.setOp3(cursor.getString(cursor.getColumnIndex("OP3")));
        quiz.setOp4(cursor.getString(cursor.getColumnIndex("OP4")));
        quiz.setCorreta(cursor.getString(cursor.getColumnIndex("CORRETA")));
        quiz.setExplicacao(cursor.getString(cursor.getColumnIndex("EXPLICACAO")));
        return quiz;
    }





}
