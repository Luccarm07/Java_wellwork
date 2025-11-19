package com.well.work.model.dto;

import java.util.List;

/**
 * DTO: BemEstarResponseDTO
 * Descrição: Response com análise de bem-estar para o dashboard
 */
public class BemEstarResponseDTO {
    public String sonoSemanal;           // "bom", "moderado", "ruim"
    public String estresse;              // "baixo", "moderado", "alto"
    public String mensagemDaSemana;      // Mensagem personalizada
    public String statusGeral;           // "excelente", "bom", "moderado", "ruim", "critico"
    public List<HistoricoItem> historico;

    public static class HistoricoItem {
        public String data;
        public Integer sono;
        public Integer estresse;
        public Integer humor;

        public HistoricoItem(String data, Integer sono, Integer estresse, Integer humor) {
            this.data = data;
            this.sono = sono;
            this.estresse = estresse;
            this.humor = humor;
        }
    }
}
