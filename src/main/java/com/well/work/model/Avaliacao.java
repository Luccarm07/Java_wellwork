package com.well.work.model;

import java.time.LocalDateTime;

/**
 * Model: Avaliacao
 * Tabela: TB_WLW_AVALIACAO
 * Descrição: Representa uma avaliação de bem-estar (sono, estresse, humor)
 */
public class Avaliacao {
    private Long idAvaliacao;
    private Long idUsuario;
    private LocalDateTime dataAvaliacao;
    private Integer sono;        // 1-5
    private Integer estresse;    // 1-5
    private Integer humor;       // 1-5

    // Construtores
    public Avaliacao() {
    }

    public Avaliacao(Long idAvaliacao, Long idUsuario, LocalDateTime dataAvaliacao, 
                     Integer sono, Integer estresse, Integer humor) {
        this.idAvaliacao = idAvaliacao;
        this.idUsuario = idUsuario;
        this.dataAvaliacao = dataAvaliacao;
        this.sono = sono;
        this.estresse = estresse;
        this.humor = humor;
    }

    // Getters e Setters
    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Integer getSono() {
        return sono;
    }

    public void setSono(Integer sono) {
        this.sono = sono;
    }

    public Integer getEstresse() {
        return estresse;
    }

    public void setEstresse(Integer estresse) {
        this.estresse = estresse;
    }

    public Integer getHumor() {
        return humor;
    }

    public void setHumor(Integer humor) {
        this.humor = humor;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "idAvaliacao=" + idAvaliacao +
                ", idUsuario=" + idUsuario +
                ", dataAvaliacao=" + dataAvaliacao +
                ", sono=" + sono +
                ", estresse=" + estresse +
                ", humor=" + humor +
                '}';
    }
}
