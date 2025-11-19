package com.well.work.service;

import com.well.work.dao.AvaliacaoDAO;
import com.well.work.dao.UsuarioDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.Avaliacao;

import java.util.List;

/**
 * Service: AvaliacaoService
 * Descrição: Lógica de negócio para avaliações
 */
public class AvaliacaoService {

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Cria uma nova avaliação
     */
    public Avaliacao criarAvaliacao(Long usuarioId, Integer sono, Integer estresse, Integer humor) {
        // Validações
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        if (sono == null || sono < 1 || sono > 5) {
            throw new BusinessException("Sono deve estar entre 1 e 5");
        }

        if (estresse == null || estresse < 1 || estresse > 5) {
            throw new BusinessException("Estresse deve estar entre 1 e 5");
        }

        if (humor == null || humor < 1 || humor > 5) {
            throw new BusinessException("Humor deve estar entre 1 e 5");
        }

        // Cria avaliação
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdUsuario(usuarioId);
        avaliacao.setSono(sono);
        avaliacao.setEstresse(estresse);
        avaliacao.setHumor(humor);

        Long id = avaliacaoDAO.create(avaliacao);
        if (id == null) {
            throw new BusinessException("Erro ao criar avaliação");
        }

        return avaliacaoDAO.findById(id);
    }

    /**
     * Busca avaliação por ID
     */
    public Avaliacao buscarPorId(Long id) {
        if (id == null) {
            throw new BusinessException("ID da avaliação é obrigatório");
        }

        Avaliacao avaliacao = avaliacaoDAO.findById(id);
        if (avaliacao == null) {
            throw new BusinessException("Avaliação não encontrada");
        }

        return avaliacao;
    }

    /**
     * Busca todas as avaliações de um usuário
     */
    public List<Avaliacao> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        return avaliacaoDAO.findByUsuarioId(usuarioId);
    }

    /**
     * Atualiza uma avaliação
     */
    public Avaliacao atualizarAvaliacao(Long id, Integer sono, Integer estresse, Integer humor) {
        if (id == null) {
            throw new BusinessException("ID da avaliação é obrigatório");
        }

        // Verifica se avaliação existe
        Avaliacao avaliacaoExistente = avaliacaoDAO.findById(id);
        if (avaliacaoExistente == null) {
            throw new BusinessException("Avaliação não encontrada");
        }

        if (sono == null || sono < 1 || sono > 5) {
            throw new BusinessException("Sono deve estar entre 1 e 5");
        }

        if (estresse == null || estresse < 1 || estresse > 5) {
            throw new BusinessException("Estresse deve estar entre 1 e 5");
        }

        if (humor == null || humor < 1 || humor > 5) {
            throw new BusinessException("Humor deve estar entre 1 e 5");
        }

        // Atualiza avaliação
        avaliacaoExistente.setSono(sono);
        avaliacaoExistente.setEstresse(estresse);
        avaliacaoExistente.setHumor(humor);

        boolean atualizado = avaliacaoDAO.update(avaliacaoExistente);
        if (!atualizado) {
            throw new BusinessException("Erro ao atualizar avaliação");
        }

        return avaliacaoDAO.findById(id);
    }

    /**
     * Deleta uma avaliação
     */
    public void deletarAvaliacao(Long id) {
        if (id == null) {
            throw new BusinessException("ID da avaliação é obrigatório");
        }

        // Verifica se avaliação existe
        Avaliacao avaliacao = avaliacaoDAO.findById(id);
        if (avaliacao == null) {
            throw new BusinessException("Avaliação não encontrada");
        }

        boolean deletado = avaliacaoDAO.delete(id);
        if (!deletado) {
            throw new BusinessException("Erro ao deletar avaliação");
        }
    }
}
