package juanrodriguesdev.apisinan.dto;

import java.time.LocalDate;

public record ConclusaoRequestDTO(
        LocalDate dataInvestigacao,
        String classificacaoFinal,
        String criterioConfirmacao,
        String evolucaoCaso,
        LocalDate dataObito,
        LocalDate dataEncerramento
) {
}
