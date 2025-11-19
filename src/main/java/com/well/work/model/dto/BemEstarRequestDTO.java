package com.well.work.model.dto;

/**
 * DTO: BemEstarRequestDTO
 * Descrição: Request para salvar pesquisa de bem-estar
 */
public class BemEstarRequestDTO {
    public String email;
    public Integer sono;      // 1-5
    public Integer estresse;  // 1-5
    public Integer humor;     // 1-5
}
