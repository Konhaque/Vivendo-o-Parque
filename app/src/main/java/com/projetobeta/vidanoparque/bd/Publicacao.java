package com.projetobeta.vidanoparque.bd;

public class Publicacao {
    private String id_usuario;
    private String nome;
    private String ftPerfil;
    private String texto;
    private String midia;
    private String tipo;

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFtPerfil() {
        return ftPerfil;
    }

    public void setFtPerfil(String ftPerfil) {
        this.ftPerfil = ftPerfil;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
