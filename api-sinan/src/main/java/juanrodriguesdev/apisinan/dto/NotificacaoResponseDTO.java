package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Notificacao;

import java.time.LocalDate;

public record NotificacaoResponseDTO(
        Long id,
        String agravo,
        LocalDate dataNotificacao,
        String ufNotificacao,
        String municipioNotificacao,
        String unidadeSaude,
        DadosPessoaisResponseDTO dadosPessoais,
        DadosResidenciaResponseDTO dadosResidencia,
        ConclusaoResponseDTO conclusao,
        InvestigadorResponseDTO investigador
) {
    public static NotificacaoResponseDTO fromEntity(Notificacao n) {
        return new NotificacaoResponseDTO(
                n.getId(),
                n.getAgravo(),
                n.getDataNotificacao(),
                n.getUfNotificacao(),
                n.getMunicipioNotificacao(),
                n.getUnidadeSaude(),
                DadosPessoaisResponseDTO.fromEntity(n.getDadosPessoais()),
                DadosResidenciaResponseDTO.fromEntity(n.getDadosResidencia()),
                n.getConclusao() != null ? ConclusaoResponseDTO.fromEntity(n.getConclusao()) : null,
                n.getInvestigador() != null ? InvestigadorResponseDTO.fromEntity(n.getInvestigador()) : null
        );
    }
}
