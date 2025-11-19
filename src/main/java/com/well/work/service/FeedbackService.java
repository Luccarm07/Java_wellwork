package com.well.work.service;

import com.well.work.dao.FeedbackDAO;
import com.well.work.dao.UsuarioDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.Feedback;

import java.util.List;

/**
 * Service: FeedbackService
 * Descrição: Lógica de negócio para feedbacks
 */
public class FeedbackService {

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Cria um novo feedback
     */
    public Feedback criarFeedback(Long usuarioId, String resumoSemanal, String statusGeral) {
        // Validações
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        if (resumoSemanal == null || resumoSemanal.trim().isEmpty()) {
            throw new BusinessException("Resumo semanal é obrigatório");
        }

        if (statusGeral == null || statusGeral.trim().isEmpty()) {
            throw new BusinessException("Status geral é obrigatório");
        }

        // Valida status geral
        if (!statusGeral.matches("excelente|bom|moderado|ruim|critico")) {
            throw new BusinessException("Status geral deve ser: excelente, bom, moderado, ruim ou critico");
        }

        // Cria feedback
        Feedback feedback = new Feedback();
        feedback.setIdUsuario(usuarioId);
        feedback.setResumoSemanal(resumoSemanal);
        feedback.setStatusGeral(statusGeral);

        Long id = feedbackDAO.create(feedback);
        if (id == null) {
            throw new BusinessException("Erro ao criar feedback");
        }

        return feedbackDAO.findById(id);
    }

    /**
     * Busca feedback por ID
     */
    public Feedback buscarPorId(Long id) {
        if (id == null) {
            throw new BusinessException("ID do feedback é obrigatório");
        }

        Feedback feedback = feedbackDAO.findById(id);
        if (feedback == null) {
            throw new BusinessException("Feedback não encontrado");
        }

        return feedback;
    }

    /**
     * Busca todos os feedbacks de um usuário
     */
    public List<Feedback> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        return feedbackDAO.findByUsuarioId(usuarioId);
    }

    /**
     * Atualiza um feedback
     */
    public Feedback atualizarFeedback(Long id, String resumoSemanal, String statusGeral) {
        if (id == null) {
            throw new BusinessException("ID do feedback é obrigatório");
        }

        // Verifica se feedback existe
        Feedback feedbackExistente = feedbackDAO.findById(id);
        if (feedbackExistente == null) {
            throw new BusinessException("Feedback não encontrado");
        }

        if (resumoSemanal == null || resumoSemanal.trim().isEmpty()) {
            throw new BusinessException("Resumo semanal é obrigatório");
        }

        if (statusGeral == null || statusGeral.trim().isEmpty()) {
            throw new BusinessException("Status geral é obrigatório");
        }

        // Valida status geral
        if (!statusGeral.matches("excelente|bom|moderado|ruim|critico")) {
            throw new BusinessException("Status geral deve ser: excelente, bom, moderado, ruim ou critico");
        }

        // Atualiza feedback
        feedbackExistente.setResumoSemanal(resumoSemanal);
        feedbackExistente.setStatusGeral(statusGeral);

        boolean atualizado = feedbackDAO.update(feedbackExistente);
        if (!atualizado) {
            throw new BusinessException("Erro ao atualizar feedback");
        }

        return feedbackDAO.findById(id);
    }

    /**
     * Deleta um feedback
     */
    public void deletarFeedback(Long id) {
        if (id == null) {
            throw new BusinessException("ID do feedback é obrigatório");
        }

        // Verifica se feedback existe
        Feedback feedback = feedbackDAO.findById(id);
        if (feedback == null) {
            throw new BusinessException("Feedback não encontrado");
        }

        boolean deletado = feedbackDAO.delete(id);
        if (!deletado) {
            throw new BusinessException("Erro ao deletar feedback");
        }
    }
}
