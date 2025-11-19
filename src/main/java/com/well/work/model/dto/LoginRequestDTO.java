package com.well.work.model.dto;

/**
 * DTO: LoginRequestDTO
 * Descrição: Request para login/registro de usuário
 */
public class LoginRequestDTO {
    public String email;
    public String nome;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
