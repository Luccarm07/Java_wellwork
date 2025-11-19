package com.well.work.model;

import java.time.LocalDateTime;

/**
 * Model: HistoricoFeedback
 * Tabela: TB_WLW_HISTORICO_FEEDBACK
 * Descrição: Representa o histórico detalhado de um feedback
 */
public class HistoricoFeedback {
    private Long idHistorico;
    private Long idFeedback;
    private LocalDateTime dataRegistro;
    private String analiseDetalhada;

    // Construtores
    public HistoricoFeedback() {
    }

    public HistoricoFeedback(Long idHistorico, Long idFeedback, LocalDateTime dataRegistro, 
                             String analiseDetalhada) {
        this.idHistorico = idHistorico;
        this.idFeedback = idFeedback;
        this.dataRegistro = dataRegistro;
        this.analiseDetalhada = analiseDetalhada;
    }

    // Getters e Setters
    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Long getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getAnaliseDetalhada() {
        return analiseDetalhada;
    }

    public void setAnaliseDetalhada(String analiseDetalhada) {
        this.analiseDetalhada = analiseDetalhada;
    }

    @Override
    public String toString() {
        return "HistoricoFeedback{" +
                "idHistorico=" + idHistorico +
                ", idFeedback=" + idFeedback +
                ", dataRegistro=" + dataRegistro +
                ", analiseDetalhada='" + analiseDetalhada + '\'' +
                '}';
    }
}
