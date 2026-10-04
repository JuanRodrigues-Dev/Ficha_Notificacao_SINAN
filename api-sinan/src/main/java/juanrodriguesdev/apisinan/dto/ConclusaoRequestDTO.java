package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Autoctonia;
import juanrodriguesdev.apisinan.model.DoencaRelacionadaTrabalho;

import java.time.LocalDate;

public record ConclusaoRequestDTO(
        LocalDate dataInvestigacao,
        String classificacaoFinal,
        String criterioConfirmacao,
        Autoctonia autoctone,
        String ufLocalInfeccao,
        String paisLocalInfeccao,
        String municipioLocalInfeccao,
        String distritoLocalInfeccao,
        String bairroLocalInfeccao,
        DoencaRelacionadaTrabalho doencaRelacionadaTrabalho,
        String evolucaoCaso,
        LocalDate dataObito,
        LocalDate dataEncerramento
) {
}
