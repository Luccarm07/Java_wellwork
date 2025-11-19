package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.dto.BemEstarRequestDTO;
import com.well.work.service.BemEstarService;

/**
 * Controller: BemEstarController
 * Path: /bemestar
 * Descrição: Endpoints REST para análise de bem-estar (integração com frontend)
 */
@Path("/bemestar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BemEstarController {

    private final BemEstarService bemEstarService = new BemEstarService();

    /**
     * POST /bemestar
     * Salva pesquisa de bem-estar e retorna análise
     * 
     * Usado pelo frontend na página /pesquisa
     */
    @POST
    public Response salvarPesquisa(BemEstarRequestDTO request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Dados da pesquisa são obrigatórios"))
                        .build();
            }

            if (request.email == null || request.email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Email é obrigatório"))
                        .build();
            }

            if (request.sono == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Campo 'sono' é obrigatório"))
                        .build();
            }

            if (request.estresse == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Campo 'estresse' é obrigatório"))
                        .build();
            }

            if (request.humor == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Campo 'humor' é obrigatório"))
                        .build();
            }

            var resultado = bemEstarService.salvarPesquisa(
                    request.email,
                    request.sono,
                    request.estresse,
                    request.humor
            );

            return Response.status(Response.Status.CREATED).entity(resultado).build();

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
     * GET /bemestar/{email}
     * Busca análise de bem-estar para o dashboard
     * 
     * Usado pelo frontend na página /dashboard
     */
    @GET
    @Path("/{email}")
    public Response buscarDashboard(@PathParam("email") String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Email é obrigatório"))
                        .build();
            }

            var dashboard = bemEstarService.buscarDashboard(email);
            return Response.ok(dashboard).build();

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
}
