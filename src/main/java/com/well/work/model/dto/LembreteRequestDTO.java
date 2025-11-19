package com.well.work.model.dto;

import java.time.LocalDateTime;

/**
 * DTO: LembreteRequestDTO
 * Descrição: Request para criar/atualizar um lembrete
 */
public class LembreteRequestDTO {
    public Long usuarioId;
    public String titulo;
    public String descricao;
    public LocalDateTime dataLembrete;
    public Integer concluido;

    public LembreteRequestDTO() {
    }

    public LembreteRequestDTO(Long usuarioId, String titulo, String descricao, 
                              LocalDateTime dataLembrete, Integer concluido) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLembrete = dataLembrete;
        this.concluido = concluido;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
}
