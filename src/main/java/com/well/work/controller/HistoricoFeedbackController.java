package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.dao.HistoricoFeedbackDAO;

/**
 * Controller: HistoricoFeedbackController
 * Path: /historico-feedback
 * Descrição: Endpoints REST para histórico de feedbacks
 */
@Path("/historico-feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoricoFeedbackController {

    private final HistoricoFeedbackDAO historicoDAO = new HistoricoFeedbackDAO();

    /**
     * GET /historico-feedback/feedback/{feedbackId}
     * Busca histórico por ID do feedback
     */
    @GET
    @Path("/feedback/{feedbackId}")
    public Response buscarPorFeedbackId(@PathParam("feedbackId") Long feedbackId) {
        try {
            var historico = historicoDAO.findByFeedbackId(feedbackId);
            
            if (historico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Histórico não encontrado para este feedback"))
                        .build();
            }
            
            return Response.ok(historico).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /historico-feedback/{id}
     * Busca histórico por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var historico = historicoDAO.findById(id);
            
            if (historico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Histórico não encontrado"))
                        .build();
            }
            
            return Response.ok(historico).build();

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
}
