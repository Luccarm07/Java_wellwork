package com.well.work.model;

import java.time.LocalDateTime;

/**
 * Model: Lembrete
 * Tabela: TB_WLW_LEMBRETE
 * Descrição: Representa um lembrete do usuário
 */
public class Lembrete {
    private Long idLembrete;
    private Long idUsuario;
    private String titulo;
    private String descricao;
    private LocalDateTime dataLembrete;
    private Integer concluido;  // 0 ou 1
    private LocalDateTime dataCriacao;

    // Construtores
    public Lembrete() {
    }

    public Lembrete(Long idLembrete, Long idUsuario, String titulo, String descricao, 
                    LocalDateTime dataLembrete, Integer concluido, LocalDateTime dataCriacao) {
        this.idLembrete = idLembrete;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLembrete = dataLembrete;
        this.concluido = concluido;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public Long getIdLembrete() {
        return idLembrete;
    }

    public void setIdLembrete(Long idLembrete) {
        this.idLembrete = idLembrete;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataLembrete() {
        return dataLembrete;
    }

    public void setDataLembrete(LocalDateTime dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    public Integer getConcluido() {
        return concluido;
    }

    public void setConcluido(Integer concluido) {
        this.concluido = concluido;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Lembrete{" +
                "idLembrete=" + idLembrete +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataLembrete=" + dataLembrete +
                ", concluido=" + concluido +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
