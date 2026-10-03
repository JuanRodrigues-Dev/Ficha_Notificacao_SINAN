package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Conclusao;

import java.time.LocalDate;

public record ConclusaoResponseDTO(
        Long id,
        LocalDate dataInvestigacao,
        String classificacaoFinal,
        String criterioConfirmacao,
        String evolucaoCaso,
        LocalDate dataObito,
        LocalDate dataEncerramento
) {
    public static ConclusaoResponseDTO fromEntity(Conclusao c) {
        return new ConclusaoResponseDTO(
                c.getId(),
                c.getDataInvestigacao(),
                c.getClassificacaoFinal(),
                c.getCriterioConfirmacao(),
                c.getEvolucaoCaso(),
                c.getDataObito(),
                c.getDataEncerramento()
        );
    }
}
