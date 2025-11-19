package com.well.work.service;

import com.well.work.dao.HabitoSaudavelDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.HabitoSaudavel;

import java.util.List;

/**
 * Service: HabitoService
 * Descrição: Lógica de negócio para hábitos saudáveis
 */
public class HabitoService {

    private final HabitoSaudavelDAO habitoDAO = new HabitoSaudavelDAO();

    /**
     * Lista todos os hábitos ativos
     */
    public List<HabitoSaudavel> listarHabitosAtivos() {
        return habitoDAO.findAllAtivos();
    }

    /**
     * Busca hábito por ID
     */
    public HabitoSaudavel buscarPorId(Long id) {
        if (id == null) {
            throw new BusinessException("ID do hábito é obrigatório");
        }

        HabitoSaudavel habito = habitoDAO.findById(id);
        if (habito == null) {
            throw new BusinessException("Hábito não encontrado");
        }

        return habito;
    }

    /**
     * Busca hábitos por categoria
     */
    public List<HabitoSaudavel> buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new BusinessException("Categoria é obrigatória");
        }

        // Valida categoria
        if (!categoria.matches("sono|alimentacao|exercicio|mental|social")) {
            throw new BusinessException("Categoria inválida. Deve ser: sono, alimentacao, exercicio, mental ou social");
        }

        return habitoDAO.findByCategoria(categoria);
    }

    /**
     * Cria um novo hábito (admin)
     */
    public HabitoSaudavel criarHabito(String titulo, String descricao, String categoria, Integer ativo) {
        // Validações
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new BusinessException("Título é obrigatório");
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new BusinessException("Descrição é obrigatória");
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new BusinessException("Categoria é obrigatória");
        }

        // Valida categoria
        if (!categoria.matches("sono|alimentacao|exercicio|mental|social")) {
            throw new BusinessException("Categoria inválida. Deve ser: sono, alimentacao, exercicio, mental ou social");
        }

        // Cria hábito
        HabitoSaudavel habito = new HabitoSaudavel();
        habito.setTitulo(titulo);
        habito.setDescricao(descricao);
        habito.setCategoria(categoria);
        habito.setAtivo(ativo != null ? ativo : 1);

        Long id = habitoDAO.create(habito);
        if (id == null) {
            throw new BusinessException("Erro ao criar hábito");
        }

        return habitoDAO.findById(id);
    }

    /**
     * Atualiza um hábito (admin)
     */
    public HabitoSaudavel atualizarHabito(Long id, String titulo, String descricao, String categoria, Integer ativo) {
        HabitoSaudavel habito = buscarPorId(id);

        if (titulo != null && !titulo.trim().isEmpty()) {
            habito.setTitulo(titulo);
        }

        if (descricao != null && !descricao.trim().isEmpty()) {
            habito.setDescricao(descricao);
        }

        if (categoria != null && !categoria.trim().isEmpty()) {
            if (!categoria.matches("sono|alimentacao|exercicio|mental|social")) {
                throw new BusinessException("Categoria inválida");
            }
            habito.setCategoria(categoria);
        }

        if (ativo != null) {
            if (ativo != 0 && ativo != 1) {
                throw new BusinessException("Ativo deve ser 0 ou 1");
            }
            habito.setAtivo(ativo);
        }

        boolean atualizado = habitoDAO.update(habito);
        if (!atualizado) {
            throw new BusinessException("Erro ao atualizar hábito");
        }

        return habitoDAO.findById(id);
    }

    /**
     * Deleta um hábito (admin)
     */
    public void deletarHabito(Long id) {
        if (id == null) {
            throw new BusinessException("ID do hábito é obrigatório");
        }

        // Verifica se hábito existe
        HabitoSaudavel habito = habitoDAO.findById(id);
        if (habito == null) {
            throw new BusinessException("Hábito não encontrado");
        }

        boolean deletado = habitoDAO.delete(id);
        if (!deletado) {
            throw new BusinessException("Erro ao deletar hábito");
        }
    }
}
