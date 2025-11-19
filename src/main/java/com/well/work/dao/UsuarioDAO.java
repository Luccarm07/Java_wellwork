package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: UsuarioDAO
 * Tabela: TB_WLW_USUARIO
 * Padrão: JDBC puro (sem JPA)
 */
public class UsuarioDAO {

    /**
     * Busca usuário por email
     */
    public Usuario findByEmail(String email) {
        String sql = "SELECT id_usuario, email, nome, data_cadastro FROM TB_WLW_USUARIO WHERE email = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getLong("id_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                if (dataCadastro != null) {
                    usuario.setDataCadastro(dataCadastro.toLocalDateTime());
                }
                
                return usuario;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca usuário por ID
     */
    public Usuario findById(Long id) {
        String sql = "SELECT id_usuario, email, nome, data_cadastro FROM TB_WLW_USUARIO WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getLong("id_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                if (dataCadastro != null) {
                    usuario.setDataCadastro(dataCadastro.toLocalDateTime());
                }
                
                return usuario;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Cria um novo usuário
     */
    public Long create(Usuario usuario) {
        String sql = "INSERT INTO TB_WLW_USUARIO (id_usuario, email, nome, data_cadastro) " +
                     "VALUES (SEQ_TB_WLW_USUARIO.NEXTVAL, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_usuario"})) {
            
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getNome());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Lista todos os usuários
     */
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, email, nome, data_cadastro FROM TB_WLW_USUARIO ORDER BY data_cadastro DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getLong("id_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                
                Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                if (dataCadastro != null) {
                    usuario.setDataCadastro(dataCadastro.toLocalDateTime());
                }
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        
        return usuarios;
    }

    /**
     * Atualiza um usuário
     */
    public boolean update(Usuario usuario) {
        String sql = "UPDATE TB_WLW_USUARIO SET nome = ?, email = ? WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setLong(3, usuario.getIdUsuario());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Deleta um usuário
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_USUARIO WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
