package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.Usuario;
import com.well.work.model.dto.LoginRequestDTO;
import com.well.work.service.UsuarioService;

/**
 * Controller: UsuarioController
 * Path: /usuarios
 * Descrição: Endpoints REST para gerenciamento de usuários
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    /**
     * POST /usuarios/login
     * Login ou cria usuário se não existir
     */
    @POST
    @Path("/login")
    public Response login(LoginRequestDTO request) {
        try {
            if (request == null || request.email == null || request.email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("O email é obrigatório para o login."))
                        .build();
            }

            var usuario = usuarioService.loginOuCriarUsuario(request.email, request.nome);
            return Response.ok(usuario).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno ao processar o login: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /usuarios
     * Lista todos os usuários
     */
    @GET
    public Response listarTodos() {
        try {
            var usuarios = usuarioService.listarTodos();
            return Response.ok(usuarios).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /usuarios/{id}
     * Busca usuário por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var usuario = usuarioService.buscarPorId(id);
            return Response.ok(usuario).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * PUT /usuarios/{id}
     * Atualiza um usuário
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            var usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
            return Response.ok(usuarioAtualizado).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * DELETE /usuarios/{id}
     * Deleta um usuário
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return Response.ok().entity(new SuccessResponse("Usuário deletado com sucesso.")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Classe interna para respostas de erro
     */
    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    /**
     * Classe interna para respostas de sucesso
     */
    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}
