package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.HabitoSaudavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: HabitoSaudavelDAO
 * Tabela: TB_WLW_HABITO_SAUDAVEL
 * Padrão: JDBC puro (sem JPA)
 */
public class HabitoSaudavelDAO {

    /**
     * Cria um novo hábito saudável
     */
    public Long create(HabitoSaudavel habito) {
        String sql = "INSERT INTO TB_WLW_HABITO_SAUDAVEL (id_habito, titulo, descricao, categoria, ativo) " +
                     "VALUES (SEQ_TB_WLW_HABITO_SAUDAVEL.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_habito"})) {
            
            stmt.setString(1, habito.getTitulo());
            stmt.setString(2, habito.getDescricao());
            stmt.setString(3, habito.getCategoria());
            stmt.setInt(4, habito.getAtivo() != null ? habito.getAtivo() : 1);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar hábito saudável: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca hábito por ID
     */
    public HabitoSaudavel findById(Long id) {
        String sql = "SELECT id_habito, titulo, descricao, categoria, ativo " +
                     "FROM TB_WLW_HABITO_SAUDAVEL WHERE id_habito = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToHabito(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hábito por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Lista todos os hábitos ativos
     */
    public List<HabitoSaudavel> findAllAtivos() {
        List<HabitoSaudavel> habitos = new ArrayList<>();
        String sql = "SELECT id_habito, titulo, descricao, categoria, ativo " +
                     "FROM TB_WLW_HABITO_SAUDAVEL WHERE ativo = 1 ORDER BY categoria, titulo";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                habitos.add(mapResultSetToHabito(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar hábitos ativos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return habitos;
    }

    /**
     * Lista todos os hábitos (ativos e inativos)
     */
    public List<HabitoSaudavel> findAll() {
        List<HabitoSaudavel> habitos = new ArrayList<>();
        String sql = "SELECT id_habito, titulo, descricao, categoria, ativo " +
                     "FROM TB_WLW_HABITO_SAUDAVEL ORDER BY categoria, titulo";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                habitos.add(mapResultSetToHabito(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar hábitos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return habitos;
    }

    /**
     * Busca hábitos por categoria
     */
    public List<HabitoSaudavel> findByCategoria(String categoria) {
        List<HabitoSaudavel> habitos = new ArrayList<>();
        String sql = "SELECT id_habito, titulo, descricao, categoria, ativo " +
                     "FROM TB_WLW_HABITO_SAUDAVEL WHERE categoria = ? AND ativo = 1 ORDER BY titulo";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                habitos.add(mapResultSetToHabito(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hábitos por categoria: " + e.getMessage());
            e.printStackTrace();
        }
        
        return habitos;
    }

    /**
     * Atualiza um hábito
     */
    public boolean update(HabitoSaudavel habito) {
        String sql = "UPDATE TB_WLW_HABITO_SAUDAVEL SET titulo = ?, descricao = ?, categoria = ?, ativo = ? " +
                     "WHERE id_habito = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, habito.getTitulo());
            stmt.setString(2, habito.getDescricao());
            stmt.setString(3, habito.getCategoria());
            stmt.setInt(4, habito.getAtivo());
            stmt.setLong(5, habito.getIdHabito());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar hábito: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Deleta um hábito
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_HABITO_SAUDAVEL WHERE id_habito = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao deletar hábito: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Mapeia ResultSet para objeto HabitoSaudavel
     */
    private HabitoSaudavel mapResultSetToHabito(ResultSet rs) throws SQLException {
        HabitoSaudavel habito = new HabitoSaudavel();
        habito.setIdHabito(rs.getLong("id_habito"));
        habito.setTitulo(rs.getString("titulo"));
        habito.setDescricao(rs.getString("descricao"));
        habito.setCategoria(rs.getString("categoria"));
        habito.setAtivo(rs.getInt("ativo"));
        return habito;
    }
}
