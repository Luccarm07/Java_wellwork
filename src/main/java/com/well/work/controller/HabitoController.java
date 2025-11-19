package com.well.work.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.well.work.model.dto.HabitoRequestDTO;
import com.well.work.service.HabitoService;

/**
 * Controller: HabitoController
 * Path: /habitos
 * Descrição: Endpoints REST para gerenciamento de hábitos saudáveis
 */
@Path("/habitos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HabitoController {

    private final HabitoService habitoService = new HabitoService();

    /**
     * GET /habitos
     * Lista todos os hábitos ativos
     */
    @GET
    public Response listarAtivos() {
        try {
            var habitos = habitoService.listarHabitosAtivos();
            return Response.ok(habitos).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /habitos/{id}
     * Busca hábito por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            var habito = habitoService.buscarPorId(id);
            return Response.ok(habito).build();

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
     * GET /habitos/categoria/{categoria}
     * Busca hábitos por categoria
     */
    @GET
    @Path("/categoria/{categoria}")
    public Response buscarPorCategoria(@PathParam("categoria") String categoria) {
        try {
            var habitos = habitoService.buscarPorCategoria(categoria);
            return Response.ok(habitos).build();

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
     * POST /habitos
     * Cria um novo hábito
     */
    @POST
    public Response criar(HabitoRequestDTO request) {
        try {
            if (request == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Dados do hábito são obrigatórios"))
                        .build();
            }

            var habito = habitoService.criarHabito(
                    request.titulo,
                    request.descricao,
                    request.categoria,
                    request.ativo
            );

            return Response.status(Response.Status.CREATED).entity(habito).build();

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
     * PUT /habitos/{id}
     * Atualiza um hábito
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, HabitoRequestDTO request) {
        try {
            var habito = habitoService.atualizarHabito(
                    id,
                    request.titulo,
                    request.descricao,
                    request.categoria,
                    request.ativo
            );

            return Response.ok(habito).build();

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
     * DELETE /habitos/{id}
     * Deleta um hábito
     */
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            habitoService.deletarHabito(id);
            return Response.ok().entity(new SuccessResponse("Hábito deletado com sucesso.")).build();

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
