package com.projetobeta.vidanoparque.bd;

import java.util.List;

public class Usuario_Quiz {
    private String id_Usuario;
    private List<String> questoes_Jogadas;
    private int acertos;
    private int pontos;

    public String getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(String id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public List<String> getQuestoes_Jogadas() {
        return questoes_Jogadas;
    }

    public void setQuestoes_Jogadas(List<String> questoes_Jogadas) {
        this.questoes_Jogadas = questoes_Jogadas;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
