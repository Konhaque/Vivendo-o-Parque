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
        stringBuilder.append("FOTO_PERFIL TEXT,");
        stringBuilder.append("ENDERECO TEXT,");
        stringBuilder.append("PROFISSAO TEXT,");
        stringBuilder.append("ESCOLARIDADE TEXT)");
        daoUtil.getConexaoDataBase().execSQL(stringBuilder.toString());
    }

    public void criarFauna(){
        String sql = "DROP TABLE IF EXISTS TB_FAUNA";
        daoUtil.getConexaoDataBase().execSQL(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE TB_FAUNA(");
        stringBuilder.append("ID_FAUNA TEXT NOT NULL,");
        stringBuilder.append("NOME_CIENTIFICO TEXT NOT NULL,");
        stringBuilder.append("NOME_POPULAR TEXT NOT NULL,");
        stringBuilder.append("DESCRICAO TEXT NOT NULL,");
        stringBuilder.append("IMAGEM TEXT NOT NULL)");
        daoUtil.getConexaoDataBase().execSQL(stringBuilder.toString());
    }

    public void criarFlora(){
        String sql = "DROP TABLE IF EXISTS TB_FLORA";
        daoUtil.getConexaoDataBase().execSQL(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE TB_FLORA(");
        stringBuilder.append("ID_FLORA TEXT NOT NULL,");
        stringBuilder.append("NOME_CIENTIFICO TEXT NOT NULL,");
        stringBuilder.append("NOME_POPULAR TEXT NOT NULL,");
        stringBuilder.append("DESCRICAO TEXT NOT NULL,");
        stringBuilder.append("IMAGEM TEXT NOT NULL)");
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
        stringBuilder.append("EXPLICACAO TEXT NOT NULL,");
        stringBuilder.append("ARQUIVO TEXT,");
        stringBuilder.append("TIPO TEXT)");
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

    public void salvarFauna(Fauna fauna){
        daoUtil.getConexaoDataBase().insert("TB_FAUNA",null,insertFauna(fauna));
    }

    public void salvarFlora(Fauna flora){
        daoUtil.getConexaoDataBase().insert("TB_FLORA",null,insertFlora(flora));
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
        cv.put("ARQUIVO", quiz.getArquivo());
        cv.put("TIPO",quiz.getTipo());
        return cv;
    }

    private ContentValues insertFauna(Fauna fauna){
        ContentValues cv = new ContentValues();
        cv.put("ID_FAUNA",fauna.getId());
        cv.put("NOME_CIENTIFICO",fauna.getNome_cientifico());
        cv.put("NOME_POPULAR",fauna.getNome_popular());
        cv.put("DESCRICAO",fauna.getDescricao());
        cv.put("IMAGEM",fauna.getImagem());
        return cv;
    }

    private ContentValues insertFlora(Fauna fauna){
        ContentValues cv = new ContentValues();
        cv.put("ID_FLORA",fauna.getId());
        cv.put("NOME_CIENTIFICO",fauna.getNome_cientifico());
        cv.put("NOME_POPULAR",fauna.getNome_popular());
        cv.put("DESCRICAO",fauna.getDescricao());
        cv.put("IMAGEM",fauna.getImagem());
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
        contentValues.put("ENDERECO",usuario.getEndereco());
        contentValues.put("PROFISSAO",usuario.getProfissao());
        contentValues.put("ESCOLARIDADE",usuario.getFormacao());
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

    public List<Fauna> getFauna(){
        List<Fauna> faunas = new ArrayList<>();
        String sql = "SELECT * FROM TB_FAUNA";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            faunas.add(carregarFauna(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return faunas;
    }

    public List<Fauna> getFlora(){
        List<Fauna> faunas = new ArrayList<>();
        String sql = "SELECT * FROM TB_FLORA";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            faunas.add(carregarFlora(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return faunas;
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

    public String getNomeUsuario(){
        String sql = "SELECT * FROM TB_PERFIL";
        Cursor cursor = daoUtil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();
        String nome = cursor.getString(cursor.getColumnIndex("NOME"));
        cursor.close();
        return nome;
    }

    private void carregarPerfil(Usuario perfil, Cursor cursor){
        perfil.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        perfil.setId(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
        perfil.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
        perfil.setFoto_perfil(cursor.getString(cursor.getColumnIndex("FOTO_PERFIL")));
        perfil.setFormacao(cursor.getString(cursor.getColumnIndex("ESCOLARIDADE")));
        perfil.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
        perfil.setProfissao(cursor.getString(cursor.getColumnIndex("PROFISSAO")));
    }

    private Fauna carregarFauna(Cursor cursor){
        Fauna fauna = new Fauna();
        fauna.setId(cursor.getString(cursor.getColumnIndex("ID_FAUNA")));
        fauna.setNome_cientifico(cursor.getString(cursor.getColumnIndex("NOME_CIENTIFICO")));
        fauna.setNome_popular(cursor.getString(cursor.getColumnIndex("NOME_POPULAR")));
        fauna.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
        fauna.setImagem(cursor.getString(cursor.getColumnIndex("IMAGEM")));
        return fauna;
    }

    private Fauna carregarFlora(Cursor cursor){
        Fauna fauna = new Fauna();
        fauna.setId(cursor.getString(cursor.getColumnIndex("ID_FLORA")));
        fauna.setNome_cientifico(cursor.getString(cursor.getColumnIndex("NOME_CIENTIFICO")));
        fauna.setNome_popular(cursor.getString(cursor.getColumnIndex("NOME_POPULAR")));
        fauna.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
        fauna.setImagem(cursor.getString(cursor.getColumnIndex("IMAGEM")));
        return fauna;
    }



    public void updateFotoPerfil(String imagem){
        String sql = "UPDATE TB_PERFIL SET FOTO_PERFIL="+"'"+imagem+"'";
        daoUtil.getConexaoDataBase().execSQL(sql);
    }

    public void updateInformacoes(Usuario usuario){
        String sql = "UPDATE TB_PERFIL SET ENDERECO="+"'"+usuario.getEndereco()+"'";
        daoUtil.getConexaoDataBase().execSQL(sql);
        sql = "UPDATE TB_PERFIL SET PROFISSAO="+"'"+usuario.getProfissao()+"'";
        daoUtil.getConexaoDataBase().execSQL(sql);
        sql = "UPDATE TB_PERFIL SET ESCOLARIDADE="+"'"+usuario.getFormacao()+"'";
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
        quiz.setArquivo(cursor.getString(cursor.getColumnIndex("ARQUIVO")));
        quiz.setTipo(cursor.getString(cursor.getColumnIndex("TIPO")));
        return quiz;
    }





}
