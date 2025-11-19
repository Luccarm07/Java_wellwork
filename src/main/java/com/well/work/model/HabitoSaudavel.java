package com.well.work.model;

/**
 * Model: HabitoSaudavel
 * Tabela: TB_WLW_HABITO_SAUDAVEL
 * Descrição: Representa um hábito saudável recomendado
 */
public class HabitoSaudavel {
    private Long idHabito;
    private String titulo;
    private String descricao;
    private String categoria; // sono, alimentacao, exercicio, mental, social
    private Integer ativo;    // 0 ou 1

    // Construtores
    public HabitoSaudavel() {
    }

    public HabitoSaudavel(Long idHabito, String titulo, String descricao, 
                          String categoria, Integer ativo) {
        this.idHabito = idHabito;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.ativo = ativo;
    }

    // Getters e Setters
    public Long getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(Long idHabito) {
        this.idHabito = idHabito;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "HabitoSaudavel{" +
                "idHabito=" + idHabito +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
