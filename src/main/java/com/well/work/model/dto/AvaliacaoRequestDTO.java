package com.well.work.model.dto;

/**
 * DTO: AvaliacaoRequestDTO
 * Descrição: Request para criar uma avaliação
 */
public class AvaliacaoRequestDTO {
    public Long usuarioId;
    public Integer sono;
    public Integer estresse;
    public Integer humor;

    public AvaliacaoRequestDTO() {
    }

    public AvaliacaoRequestDTO(Long usuarioId, Integer sono, Integer estresse, Integer humor) {
        this.usuarioId = usuarioId;
        this.sono = sono;
        this.estresse = estresse;
        this.humor = humor;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
}
