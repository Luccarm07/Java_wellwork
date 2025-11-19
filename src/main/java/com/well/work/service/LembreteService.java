package com.well.work.service;

import com.well.work.dao.LembreteDAO;
import com.well.work.dao.UsuarioDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.Lembrete;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service: LembreteService
 * Descrição: Lógica de negócio para lembretes
 */
public class LembreteService {

    private final LembreteDAO lembreteDAO = new LembreteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Cria um novo lembrete
     */
    public Lembrete criarLembrete(Long usuarioId, String titulo, String descricao, LocalDateTime dataLembrete) {
        // Validações
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new BusinessException("Título do lembrete é obrigatório");
        }

        if (dataLembrete == null) {
            throw new BusinessException("Data do lembrete é obrigatória");
        }

        // Cria lembrete
        Lembrete lembrete = new Lembrete();
        lembrete.setIdUsuario(usuarioId);
        lembrete.setTitulo(titulo);
        lembrete.setDescricao(descricao);
        lembrete.setDataLembrete(dataLembrete);
        lembrete.setConcluido(0);

        Long id = lembreteDAO.create(lembrete);
        if (id == null) {
            throw new BusinessException("Erro ao criar lembrete");
        }

        return lembreteDAO.findById(id);
    }

    /**
     * Busca lembrete por ID
     */
    public Lembrete buscarPorId(Long id) {
        if (id == null) {
            throw new BusinessException("ID do lembrete é obrigatório");
        }

        Lembrete lembrete = lembreteDAO.findById(id);
        if (lembrete == null) {
            throw new BusinessException("Lembrete não encontrado");
        }

        return lembrete;
    }

    /**
     * Busca todos os lembretes de um usuário
     */
    public List<Lembrete> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuarioDAO.findById(usuarioId) == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        return lembreteDAO.findByUsuarioId(usuarioId);
    }

    /**
     * Atualiza um lembrete
     */
    public Lembrete atualizarLembrete(Long id, String titulo, String descricao, LocalDateTime dataLembrete, Integer concluido) {
        Lembrete lembrete = buscarPorId(id);

        if (titulo != null && !titulo.trim().isEmpty()) {
            lembrete.setTitulo(titulo);
        }

        if (descricao != null) {
            lembrete.setDescricao(descricao);
        }

        if (dataLembrete != null) {
            lembrete.setDataLembrete(dataLembrete);
        }

        if (concluido != null) {
            if (concluido != 0 && concluido != 1) {
                throw new BusinessException("Concluído deve ser 0 ou 1");
            }
            lembrete.setConcluido(concluido);
        }

        boolean atualizado = lembreteDAO.update(lembrete);
        if (!atualizado) {
            throw new BusinessException("Erro ao atualizar lembrete");
        }

        return lembreteDAO.findById(id);
    }

    /**
     * Deleta um lembrete
     */
    public void deletarLembrete(Long id) {
        if (id == null) {
            throw new BusinessException("ID do lembrete é obrigatório");
        }

        if (lembreteDAO.findById(id) == null) {
            throw new BusinessException("Lembrete não encontrado");
        }

        boolean deletado = lembreteDAO.delete(id);
        if (!deletado) {
            throw new BusinessException("Erro ao deletar lembrete");
        }
    }
}
