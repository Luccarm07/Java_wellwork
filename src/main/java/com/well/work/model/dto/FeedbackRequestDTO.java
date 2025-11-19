package com.well.work.model.dto;

/**
 * DTO: FeedbackRequestDTO
 * Descrição: Request para criar um feedback
 */
public class FeedbackRequestDTO {
    public Long usuarioId;
    public String resumoSemanal;
    public String statusGeral;

    public FeedbackRequestDTO() {
    }

    public FeedbackRequestDTO(Long usuarioId, String resumoSemanal, String statusGeral) {
        this.usuarioId = usuarioId;
        this.resumoSemanal = resumoSemanal;
        this.statusGeral = statusGeral;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getResumoSemanal() {
        return resumoSemanal;
    }

    public void setResumoSemanal(String resumoSemanal) {
        this.resumoSemanal = resumoSemanal;
    }

    public String getStatusGeral() {
        return statusGeral;
    }

    public void setStatusGeral(String statusGeral) {
        this.statusGeral = statusGeral;
    }
}
