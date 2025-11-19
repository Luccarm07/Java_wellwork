package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.Lembrete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: LembreteDAO
 * Tabela: TB_WLW_LEMBRETE
 * Padrão: JDBC puro (sem JPA)
 */
public class LembreteDAO {

    /**
     * Cria um novo lembrete
     */
    public Long create(Lembrete lembrete) {
        String sql = "INSERT INTO TB_WLW_LEMBRETE (id_lembrete, id_usuario, titulo, descricao, data_lembrete, concluido, data_criacao) " +
                     "VALUES (SEQ_TB_WLW_LEMBRETE.NEXTVAL, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_lembrete"})) {
            
            stmt.setLong(1, lembrete.getIdUsuario());
            stmt.setString(2, lembrete.getTitulo());
            stmt.setString(3, lembrete.getDescricao());
            stmt.setTimestamp(4, Timestamp.valueOf(lembrete.getDataLembrete()));
            stmt.setInt(5, lembrete.getConcluido() != null ? lembrete.getConcluido() : 0);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar lembrete: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca lembrete por ID
     */
    public Lembrete findById(Long id) {
        String sql = "SELECT id_lembrete, id_usuario, titulo, descricao, data_lembrete, concluido, data_criacao " +
                     "FROM TB_WLW_LEMBRETE WHERE id_lembrete = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToLembrete(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar lembrete por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca todos os lembretes de um usuário
     */
    public List<Lembrete> findByUsuarioId(Long usuarioId) {
        List<Lembrete> lembretes = new ArrayList<>();
        String sql = "SELECT id_lembrete, id_usuario, titulo, descricao, data_lembrete, concluido, data_criacao " +
                     "FROM TB_WLW_LEMBRETE WHERE id_usuario = ? ORDER BY data_lembrete ASC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                lembretes.add(mapResultSetToLembrete(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar lembretes por usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lembretes;
    }

    /**
     * Atualiza um lembrete
     */
    public boolean update(Lembrete lembrete) {
        String sql = "UPDATE TB_WLW_LEMBRETE SET titulo = ?, descricao = ?, data_lembrete = ?, concluido = ? " +
                     "WHERE id_lembrete = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lembrete.getTitulo());
            stmt.setString(2, lembrete.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(lembrete.getDataLembrete()));
            stmt.setInt(4, lembrete.getConcluido());
            stmt.setLong(5, lembrete.getIdLembrete());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar lembrete: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Deleta um lembrete
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_LEMBRETE WHERE id_lembrete = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao deletar lembrete: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Mapeia ResultSet para objeto Lembrete
     */
    private Lembrete mapResultSetToLembrete(ResultSet rs) throws SQLException {
        Lembrete lembrete = new Lembrete();
        lembrete.setIdLembrete(rs.getLong("id_lembrete"));
        lembrete.setIdUsuario(rs.getLong("id_usuario"));
        lembrete.setTitulo(rs.getString("titulo"));
        lembrete.setDescricao(rs.getString("descricao"));
        lembrete.setConcluido(rs.getInt("concluido"));
        
        Timestamp dataLembrete = rs.getTimestamp("data_lembrete");
        if (dataLembrete != null) {
            lembrete.setDataLembrete(dataLembrete.toLocalDateTime());
        }
        
        Timestamp dataCriacao = rs.getTimestamp("data_criacao");
        if (dataCriacao != null) {
            lembrete.setDataCriacao(dataCriacao.toLocalDateTime());
        }
        
        return lembrete;
    }
}
