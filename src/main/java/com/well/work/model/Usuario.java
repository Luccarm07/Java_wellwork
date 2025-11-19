package com.well.work.model;

import java.time.LocalDateTime;

/**
 * Model: Usuario
 * Tabela: TB_WLW_USUARIO
 * Descrição: Representa um usuário do sistema WellWork
 */
public class Usuario {
    private Long idUsuario;
    private String email;
    private String nome;
    private LocalDateTime dataCadastro;

    // Construtores
    public Usuario() {
    }

    public Usuario(Long idUsuario, String email, String nome, LocalDateTime dataCadastro) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
