package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.dto.AvaliacaoRequestDTO;
import com.well.work.service.AvaliacaoService;

/**
 * Controller: AvaliacaoController
 * Path: /avaliacoes
 * Descrição: Endpoints REST para gerenciamento de avaliações
 */
@Path("/avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService = new AvaliacaoService();

    /**
     * POST /avaliacoes
     * Cria uma nova avaliação
     */
    @POST
    public Response criar(AvaliacaoRequestDTO request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Dados da avaliação são obrigatórios"))
                        .build();
            }

            var avaliacao = avaliacaoService.criarAvaliacao(
                    request.usuarioId,
                    request.sono,
                    request.estresse,
                    request.humor
            );

            return Response.status(Response.Status.CREATED).entity(avaliacao).build();

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
     * GET /avaliacoes/{id}
     * Busca avaliação por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var avaliacao = avaliacaoService.buscarPorId(id);
            return Response.ok(avaliacao).build();

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
     * GET /avaliacoes/usuario/{usuarioId}
     * Busca todas as avaliações de um usuário
     */
    @GET
    @Path("/usuario/{usuarioId}")
    public Response buscarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            var avaliacoes = avaliacaoService.buscarPorUsuario(usuarioId);
            return Response.ok(avaliacoes).build();

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
     * PUT /avaliacoes/{id}
     * Atualiza uma avaliação
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, AvaliacaoRequestDTO request) {
        try {
            var avaliacao = avaliacaoService.atualizarAvaliacao(
                    id,
                    request.sono,
                    request.estresse,
                    request.humor
            );

            return Response.ok(avaliacao).build();

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
     * DELETE /avaliacoes/{id}
     * Deleta uma avaliação
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            avaliacaoService.deletarAvaliacao(id);
            return Response.ok().entity(new SuccessResponse("Avaliação deletada com sucesso.")).build();

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
