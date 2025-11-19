package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.dto.LembreteRequestDTO;
import com.well.work.service.LembreteService;

/**
 * Controller: LembreteController
 * Path: /lembretes
 * Descrição: Endpoints REST para gerenciamento de lembretes
 */
@Path("/lembretes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LembreteController {

    private final LembreteService lembreteService = new LembreteService();

    /**
     * POST /lembretes
     * Cria um novo lembrete
     */
    @POST
    public Response criar(LembreteRequestDTO request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Dados do lembrete são obrigatórios"))
                        .build();
            }

            if (request.usuarioId == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("O ID do usuário é obrigatório."))
                        .build();
            }

            if (request.titulo == null || request.titulo.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("O título do lembrete é obrigatório."))
                        .build();
            }

            if (request.dataLembrete == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("A data do lembrete é obrigatória."))
                        .build();
            }

            var lembrete = lembreteService.criarLembrete(
                    request.usuarioId,
                    request.titulo,
                    request.descricao,
                    request.dataLembrete
            );

            return Response.status(Response.Status.CREATED).entity(lembrete).build();

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
     * GET /lembretes/{id}
     * Busca lembrete por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var lembrete = lembreteService.buscarPorId(id);
            return Response.ok(lembrete).build();

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
     * GET /lembretes/usuario/{usuarioId}
     * Busca todos os lembretes de um usuário
     */
    @GET
    @Path("/usuario/{usuarioId}")
    public Response buscarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            var lembretes = lembreteService.buscarPorUsuario(usuarioId);
            return Response.ok(lembretes).build();

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
     * PUT /lembretes/{id}
     * Atualiza um lembrete
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, LembreteRequestDTO request) {
        try {
            var lembrete = lembreteService.atualizarLembrete(
                    id,
                    request.titulo,
                    request.descricao,
                    request.dataLembrete,
                    request.concluido
            );

            return Response.ok(lembrete).build();

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
     * DELETE /lembretes/{id}
     * Deleta um lembrete
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            lembreteService.deletarLembrete(id);
            return Response.ok().entity(new SuccessResponse("Lembrete deletado com sucesso.")).build();

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
