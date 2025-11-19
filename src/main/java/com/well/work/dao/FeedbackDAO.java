package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: FeedbackDAO
 * Tabela: TB_WLW_FEEDBACK
 * Padrão: JDBC puro (sem JPA)
 */
public class FeedbackDAO {

    /**
     * Cria um novo feedback
     */
    public Long create(Feedback feedback) {
        String sql = "INSERT INTO TB_WLW_FEEDBACK (id_feedback, id_usuario, data_feedback, resumo_semanal, status_geral) " +
                "VALUES (SEQ_TB_WLW_FEEDBACK.NEXTVAL, ?, CURRENT_TIMESTAMP, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_feedback"})) {

            stmt.setLong(1, feedback.getIdUsuario());
            stmt.setString(2, feedback.getResumoSemanal());
            stmt.setString(3, feedback.getStatusGeral());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar feedback: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca feedback por ID
     */
    public Feedback findById(Long id) {
        String sql = "SELECT id_feedback, id_usuario, data_feedback, resumo_semanal, status_geral " +
                "FROM TB_WLW_FEEDBACK WHERE id_feedback = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToFeedback(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar feedback por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca todos os feedbacks de um usuário
     */
    public List<Feedback> findByUsuarioId(Long usuarioId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT id_feedback, id_usuario, data_feedback, resumo_semanal, status_geral " +
                "FROM TB_WLW_FEEDBACK WHERE id_usuario = ? ORDER BY data_feedback DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                feedbacks.add(mapResultSetToFeedback(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar feedbacks por usuário: " + e.getMessage());
            e.printStackTrace();
        }

        return feedbacks;
    }

    /**
     * Lista todos os feedbacks
     */
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT id_feedback, id_usuario, data_feedback, resumo_semanal, status_geral " +
                "FROM TB_WLW_FEEDBACK ORDER BY data_feedback DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                feedbacks.add(mapResultSetToFeedback(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar feedbacks: " + e.getMessage());
            e.printStackTrace();
        }

        return feedbacks;
    }

    /**
     * Atualiza um feedback
     */
    public boolean update(Feedback feedback) {
        String sql = "UPDATE TB_WLW_FEEDBACK SET resumo_semanal = ?, status_geral = ? WHERE id_feedback = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feedback.getResumoSemanal());
            stmt.setString(2, feedback.getStatusGeral());
            stmt.setLong(3, feedback.getIdFeedback());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar feedback: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deleta um feedback
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_FEEDBACK WHERE id_feedback = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar feedback: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Mapeia ResultSet para objeto Feedback
     */
    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setIdFeedback(rs.getLong("id_feedback"));
        feedback.setIdUsuario(rs.getLong("id_usuario"));
        feedback.setResumoSemanal(rs.getString("resumo_semanal"));
        feedback.setStatusGeral(rs.getString("status_geral"));

        Timestamp dataFeedback = rs.getTimestamp("data_feedback");
        if (dataFeedback != null) {
            feedback.setDataFeedback(dataFeedback.toLocalDateTime());
        }

        return feedback;
    }
}
