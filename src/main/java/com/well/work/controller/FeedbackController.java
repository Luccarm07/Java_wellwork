package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.dto.FeedbackRequestDTO;
import com.well.work.service.FeedbackService;

/**
 * Controller: FeedbackController
 * Path: /feedbacks
 * Descrição: Endpoints REST para gerenciamento de feedbacks
 */
@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackController {

    private final FeedbackService feedbackService = new FeedbackService();

    /**
     * POST /feedbacks
     * Cria um novo feedback
     */
    @POST
    public Response criar(FeedbackRequestDTO request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Dados do feedback são obrigatórios"))
                        .build();
            }

            var feedback = feedbackService.criarFeedback(
                    request.usuarioId,
                    request.resumoSemanal,
                    request.statusGeral
            );

            return Response.status(Response.Status.CREATED).entity(feedback).build();

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
     * GET /feedbacks/{id}
     * Busca feedback por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var feedback = feedbackService.buscarPorId(id);
            return Response.ok(feedback).build();

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
     * GET /feedbacks/usuario/{usuarioId}
     * Busca todos os feedbacks de um usuário
     */
    @GET
    @Path("/usuario/{usuarioId}")
    public Response buscarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            var feedbacks = feedbackService.buscarPorUsuario(usuarioId);
            return Response.ok(feedbacks).build();

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
     * PUT /feedbacks/{id}
     * Atualiza um feedback
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, FeedbackRequestDTO request) {
        try {
            var feedback = feedbackService.atualizarFeedback(
                    id,
                    request.resumoSemanal,
                    request.statusGeral
            );

            return Response.ok(feedback).build();

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
     * DELETE /feedbacks/{id}
     * Deleta um feedback
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            feedbackService.deletarFeedback(id);
            return Response.ok().entity(new SuccessResponse("Feedback deletado com sucesso.")).build();

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
