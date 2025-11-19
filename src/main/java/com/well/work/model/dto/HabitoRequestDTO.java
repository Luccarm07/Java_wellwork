package com.well.work.model.dto;

/**
 * DTO: HabitoRequestDTO
 * Descrição: DTO para requisições de hábitos saudáveis
 */
public class HabitoRequestDTO {
    public String titulo;
    public String descricao;
    public String categoria;
    public Integer ativo;

    public HabitoRequestDTO() {
    }

    public HabitoRequestDTO(String titulo, String descricao, String categoria, Integer ativo) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.ativo = ativo;
    }
}
