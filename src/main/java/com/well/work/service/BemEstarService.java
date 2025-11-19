package com.well.work.service;

import com.well.work.dao.AvaliacaoDAO;
import com.well.work.dao.FeedbackDAO;
import com.well.work.dao.UsuarioDAO;
import com.well.work.exception.BusinessException;
import com.well.work.model.Avaliacao;
import com.well.work.model.Feedback;
import com.well.work.model.Usuario;
import com.well.work.model.dto.BemEstarResponseDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service: BemEstarService
 * Descrição: Lógica de negócio para análise de bem-estar
 */
public class BemEstarService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    /**
     * Salva pesquisa de bem-estar e gera análise
     */
    public BemEstarResponseDTO salvarPesquisa(String email, Integer sono, Integer estresse, Integer humor) {
        // Validações
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }

        if (sono == null || sono < 1 || sono > 5) {
            throw new BusinessException("Sono deve ser entre 1 e 5");
        }

        if (estresse == null || estresse < 1 || estresse > 5) {
            throw new BusinessException("Estresse deve ser entre 1 e 5");
        }

        if (humor == null || humor < 1 || humor > 5) {
            throw new BusinessException("Humor deve ser entre 1 e 5");
        }

        // Busca ou cria usuário
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado. Faça login primeiro.");
        }

        // Cria avaliação
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdUsuario(usuario.getIdUsuario());
        avaliacao.setSono(sono);
        avaliacao.setEstresse(estresse);
        avaliacao.setHumor(humor);

        Long avaliacaoId = avaliacaoDAO.create(avaliacao);
        if (avaliacaoId == null) {
            throw new BusinessException("Erro ao salvar avaliação");
        }

        // Gera análise e cria feedback
        String resumo = gerarResumoSemanal(sono, estresse, humor);
        String statusGeral = calcularStatusGeral(sono, estresse, humor);

        Feedback feedback = new Feedback();
        feedback.setIdUsuario(usuario.getIdUsuario());
        feedback.setResumoSemanal(resumo);
        feedback.setStatusGeral(statusGeral);

        feedbackDAO.create(feedback);

        // Retorna análise para o dashboard
        return buscarDashboard(email);
    }

    /**
     * Busca dados do dashboard por email
     */
    public BemEstarResponseDTO buscarDashboard(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }

        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        // Busca avaliações do usuário
        List<Avaliacao> avaliacoes = avaliacaoDAO.findByUsuarioId(usuario.getIdUsuario());

        if (avaliacoes.isEmpty()) {
            // Retorna resposta vazia se não houver avaliações
            BemEstarResponseDTO response = new BemEstarResponseDTO();
            response.sonoSemanal = "sem_dados";
            response.estresse = "sem_dados";
            response.mensagemDaSemana = "Você ainda não respondeu nenhuma pesquisa. Que tal começar agora?";
            response.statusGeral = "sem_dados";
            response.historico = new ArrayList<>();
            return response;
        }

        // Calcula médias
        double mediaSono = avaliacoes.stream().mapToInt(Avaliacao::getSono).average().orElse(0);
        double mediaEstresse = avaliacoes.stream().mapToInt(Avaliacao::getEstresse).average().orElse(0);
        double mediaHumor = avaliacoes.stream().mapToInt(Avaliacao::getHumor).average().orElse(0);

        // Monta response
        BemEstarResponseDTO response = new BemEstarResponseDTO();
        response.sonoSemanal = classificarSono(mediaSono);
        response.estresse = classificarEstresse(mediaEstresse);
        response.mensagemDaSemana = gerarMensagemPersonalizada(mediaSono, mediaEstresse, mediaHumor);
        response.statusGeral = calcularStatusGeralMedia(mediaSono, mediaEstresse, mediaHumor);

        // Monta histórico
        response.historico = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Avaliacao av : avaliacoes) {
            String data = av.getDataAvaliacao().format(formatter);
            response.historico.add(new BemEstarResponseDTO.HistoricoItem(
                    data,
                    av.getSono(),
                    av.getEstresse(),
                    av.getHumor()
            ));
        }

        return response;
    }

    /**
     * Gera resumo semanal baseado nos valores
     */
    private String gerarResumoSemanal(int sono, int estresse, int humor) {
        if (sono <= 2) {
            return "Essa semana você dormiu pouco. Tente melhorar sua qualidade de sono.";
        } else if (estresse >= 4) {
            return "Seu nível de estresse está alto. Considere fazer pausas durante o trabalho.";
        } else if (humor <= 2) {
            return "Você não está se sentindo bem. Que tal conversar com alguém?";
        } else if (sono >= 4 && estresse <= 2 && humor >= 4) {
            return "Parabéns! Você está com ótimos indicadores de bem-estar!";
        } else {
            return "Continue cuidando da sua saúde mental e física!";
        }
    }

    /**
     * Calcula status geral baseado nos valores
     */
    private String calcularStatusGeral(int sono, int estresse, int humor) {
        double media = (sono + (6 - estresse) + humor) / 3.0;

        if (media >= 4.5) return "excelente";
        if (media >= 3.5) return "bom";
        if (media >= 2.5) return "moderado";
        if (media >= 1.5) return "ruim";
        return "critico";
    }

    /**
     * Calcula status geral baseado nas médias
     */
    private String calcularStatusGeralMedia(double mediaSono, double mediaEstresse, double mediaHumor) {
        double media = (mediaSono + (6 - mediaEstresse) + mediaHumor) / 3.0;

        if (media >= 4.5) return "excelente";
        if (media >= 3.5) return "bom";
        if (media >= 2.5) return "moderado";
        if (media >= 1.5) return "ruim";
        return "critico";
    }

    /**
     * Classifica qualidade do sono
     */
    private String classificarSono(double media) {
        if (media >= 4) return "bom";
        if (media >= 2.5) return "moderado";
        return "ruim";
    }

    /**
     * Classifica nível de estresse
     */
    private String classificarEstresse(double media) {
        if (media >= 4) return "alto";
        if (media >= 2.5) return "moderado";
        return "baixo";
    }

    /**
     * Gera mensagem personalizada
     */
    private String gerarMensagemPersonalizada(double mediaSono, double mediaEstresse, double mediaHumor) {
        if (mediaSono <= 2) {
            return "Você tem dormido pouco ultimamente. Tente estabelecer uma rotina de sono.";
        } else if (mediaEstresse >= 4) {
            return "Seu estresse está elevado. Considere técnicas de relaxamento.";
        } else if (mediaHumor <= 2) {
            return "Você não está se sentindo bem. Busque apoio se necessário.";
        } else if (mediaSono >= 4 && mediaEstresse <= 2 && mediaHumor >= 4) {
            return "Excelente! Você está mantendo um ótimo equilíbrio!";
        } else {
            return "Continue cuidando do seu bem-estar no trabalho!";
        }
    }
}
