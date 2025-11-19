package com.well.work.dao;

import com.well.work.config.DatabaseConfig;
import com.well.work.model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: AvaliacaoDAO
 * Tabela: TB_WLW_AVALIACAO
 * Padrão: JDBC puro (sem JPA)
 */
public class AvaliacaoDAO {

    /**
     * Cria uma nova avaliação
     */
    public Long create(Avaliacao avaliacao) {
        String sql = "INSERT INTO TB_WLW_AVALIACAO (id_avaliacao, id_usuario, data_avaliacao, sono, estresse, humor) " +
                "VALUES (SEQ_TB_WLW_AVALIACAO.NEXTVAL, ?, CURRENT_TIMESTAMP, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_avaliacao"})) {

            stmt.setLong(1, avaliacao.getIdUsuario());
            stmt.setInt(2, avaliacao.getSono());
            stmt.setInt(3, avaliacao.getEstresse());
            stmt.setInt(4, avaliacao.getHumor());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar avaliação: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca avaliação por ID
     */
    public Avaliacao findById(Long id) {
        String sql = "SELECT id_avaliacao, id_usuario, data_avaliacao, sono, estresse, humor " +
                "FROM TB_WLW_AVALIACAO WHERE id_avaliacao = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAvaliacao(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar avaliação por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca todas as avaliações de um usuário
     */
    public List<Avaliacao> findByUsuarioId(Long usuarioId) {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT id_avaliacao, id_usuario, data_avaliacao, sono, estresse, humor " +
                "FROM TB_WLW_AVALIACAO WHERE id_usuario = ? ORDER BY data_avaliacao DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                avaliacoes.add(mapResultSetToAvaliacao(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar avaliações por usuário: " + e.getMessage());
            e.printStackTrace();
        }

        return avaliacoes;
    }

    /**
     * Lista todas as avaliações
     */
    public List<Avaliacao> findAll() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT id_avaliacao, id_usuario, data_avaliacao, sono, estresse, humor " +
                "FROM TB_WLW_AVALIACAO ORDER BY data_avaliacao DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                avaliacoes.add(mapResultSetToAvaliacao(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar avaliações: " + e.getMessage());
            e.printStackTrace();
        }

        return avaliacoes;
    }

    /**
     * Atualiza uma avaliação
     */
    public boolean update(Avaliacao avaliacao) {
        String sql = "UPDATE TB_WLW_AVALIACAO SET sono = ?, estresse = ?, humor = ? WHERE id_avaliacao = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao.getSono());
            stmt.setInt(2, avaliacao.getEstresse());
            stmt.setInt(3, avaliacao.getHumor());
            stmt.setLong(4, avaliacao.getIdAvaliacao());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar avaliação: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deleta uma avaliação
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM TB_WLW_AVALIACAO WHERE id_avaliacao = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar avaliação: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Mapeia ResultSet para objeto Avaliacao
     */
    private Avaliacao mapResultSetToAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdAvaliacao(rs.getLong("id_avaliacao"));
        avaliacao.setIdUsuario(rs.getLong("id_usuario"));
        avaliacao.setSono(rs.getInt("sono"));
        avaliacao.setEstresse(rs.getInt("estresse"));
        avaliacao.setHumor(rs.getInt("humor"));

        Timestamp dataAvaliacao = rs.getTimestamp("data_avaliacao");
        if (dataAvaliacao != null) {
            avaliacao.setDataAvaliacao(dataAvaliacao.toLocalDateTime());
        }

        return avaliacao;
    }
}
