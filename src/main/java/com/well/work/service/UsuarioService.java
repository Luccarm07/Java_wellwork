package com.well.work.service;

import com.well.work.dao.UsuarioDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.Usuario;

/**
 * Service: UsuarioService
 * Descrição: Lógica de negócio para usuários
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Login ou cria usuário se não existir
     */
    public Usuario loginOuCriarUsuario(String email, String nome) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }

        // Busca usuário existente
        Usuario usuario = usuarioDAO.findByEmail(email);

        // Se não existir, cria novo usuário
        if (usuario == null) {
            if (nome == null || nome.trim().isEmpty()) {
                throw new BusinessException("Nome é obrigatório para criar novo usuário");
            }

            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email);
            novoUsuario.setNome(nome);

            Long id = usuarioDAO.create(novoUsuario);
            if (id == null) {
                throw new BusinessException("Erro ao criar usuário");
            }

            usuario = usuarioDAO.findById(id);
        }

        return usuario;
    }

    /**
     * Busca usuário por ID
     */
    public Usuario buscarPorId(Long id) {
        if (id == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        return usuario;
    }

    /**
     * Busca usuário por email
     */
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }

        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        return usuario;
    }

    /**
     * Lista todos os usuários
     */
    public java.util.List<Usuario> listarTodos() {
        return usuarioDAO.findAll();
    }

    /**
     * Atualiza um usuário
     */
    public Usuario atualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome é obrigatório");
        }

        // Verifica se usuário existe
        Usuario usuarioExistente = usuarioDAO.findById(usuario.getIdUsuario());
        if (usuarioExistente == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        boolean atualizado = usuarioDAO.update(usuario);
        if (!atualizado) {
            throw new BusinessException("Erro ao atualizar usuário");
        }

        return usuarioDAO.findById(usuario.getIdUsuario());
    }

    /**
     * Deleta um usuário
     */
    public void deletarUsuario(Long id) {
        if (id == null) {
            throw new BusinessException("ID do usuário é obrigatório");
        }

        // Verifica se usuário existe
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        boolean deletado = usuarioDAO.delete(id);
        if (!deletado) {
            throw new BusinessException("Erro ao deletar usuário");
        }
    }
}
