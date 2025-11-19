package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.HistoricoFeedback;

import java.sql.*;

/**
 * DAO: HistoricoFeedbackDAO
 * Tabela: TB_WLW_HISTORICO_FEEDBACK
 * Padrão: JDBC puro (sem JPA)
 */
public class HistoricoFeedbackDAO {

    /**
     * Cria um novo histórico de feedback
     */
    public Long create(HistoricoFeedback historico) {
        String sql = "INSERT INTO TB_WLW_HISTORICO_FEEDBACK (id_historico, id_feedback, data_registro, analise_detalhada) " +
                     "VALUES (SEQ_TB_WLW_HISTORICO_FEEDBACK.NEXTVAL, ?, CURRENT_TIMESTAMP, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_historico"})) {
            
            stmt.setLong(1, historico.getIdFeedback());
            stmt.setString(2, historico.getAnaliseDetalhada());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar histórico de feedback: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca histórico por ID do feedback (relacionamento 1:1)
     */
    public HistoricoFeedback findByFeedbackId(Long feedbackId) {
        String sql = "SELECT id_historico, id_feedback, data_registro, analise_detalhada " +
                     "FROM TB_WLW_HISTORICO_FEEDBACK WHERE id_feedback = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToHistorico(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar histórico por feedback ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Busca histórico por ID
     */
    public HistoricoFeedback findById(Long id) {
        String sql = "SELECT id_historico, id_feedback, data_registro, analise_detalhada " +
                     "FROM TB_WLW_HISTORICO_FEEDBACK WHERE id_historico = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToHistorico(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar histórico por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Atualiza um histórico de feedback
     */
    public boolean update(HistoricoFeedback historico) {
        String sql = "UPDATE TB_WLW_HISTORICO_FEEDBACK SET analise_detalhada = ? WHERE id_historico = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, historico.getAnaliseDetalhada());
            stmt.setLong(2, historico.getIdHistorico());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar histórico: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Deleta um histórico
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_HISTORICO_FEEDBACK WHERE id_historico = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao deletar histórico: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Mapeia ResultSet para objeto HistoricoFeedback
     */
    private HistoricoFeedback mapResultSetToHistorico(ResultSet rs) throws SQLException {
        HistoricoFeedback historico = new HistoricoFeedback();
        historico.setIdHistorico(rs.getLong("id_historico"));
        historico.setIdFeedback(rs.getLong("id_feedback"));
        historico.setAnaliseDetalhada(rs.getString("analise_detalhada"));
        
        Timestamp dataRegistro = rs.getTimestamp("data_registro");
        if (dataRegistro != null) {
            historico.setDataRegistro(dataRegistro.toLocalDateTime());
        }
        
        return historico;
    }
}
