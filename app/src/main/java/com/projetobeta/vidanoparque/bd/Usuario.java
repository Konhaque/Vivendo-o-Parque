package com.projetobeta.vidanoparque.bd;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private String formacao;
    private String profissao;
    private Selos selos;
    private String id_google;
    private String foto_perfil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId_google(String id_google) {
        this.id_google = id_google;
    }

    public String getId_google() {
        return id_google;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Selos getSelos() {
        return selos;
    }

    public void setSelos(Selos selos) {
        this.selos = selos;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
