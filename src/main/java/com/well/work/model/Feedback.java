package com.well.work.model;

import java.time.LocalDateTime;

/**
 * Model: Feedback
 * Tabela: TB_WLW_FEEDBACK
 * Descrição: Representa um feedback gerado automaticamente baseado nas avaliações
 */
public class Feedback {
    private Long idFeedback;
    private Long idUsuario;
    private LocalDateTime dataFeedback;
    private String resumoSemanal;
    private String statusGeral; // excelente, bom, moderado, ruim, critico

    // Construtores
    public Feedback() {
    }

    public Feedback(Long idFeedback, Long idUsuario, LocalDateTime dataFeedback, 
                    String resumoSemanal, String statusGeral) {
        this.idFeedback = idFeedback;
        this.idUsuario = idUsuario;
        this.dataFeedback = dataFeedback;
        this.resumoSemanal = resumoSemanal;
        this.statusGeral = statusGeral;
    }

    // Getters e Setters
    public Long getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }

    public void setDataFeedback(LocalDateTime dataFeedback) {
        this.dataFeedback = dataFeedback;
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

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", idUsuario=" + idUsuario +
                ", dataFeedback=" + dataFeedback +
                ", resumoSemanal='" + resumoSemanal + '\'' +
                ", statusGeral='" + statusGeral + '\'' +
                '}';
    }
}
