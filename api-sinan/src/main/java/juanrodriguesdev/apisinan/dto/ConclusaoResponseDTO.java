package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Autoctonia;
import juanrodriguesdev.apisinan.model.Conclusao;
import juanrodriguesdev.apisinan.model.DoencaRelacionadaTrabalho;

import java.time.LocalDate;

public record ConclusaoResponseDTO(
        Long id,
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
    public static ConclusaoResponseDTO fromEntity(Conclusao c) {
        return new ConclusaoResponseDTO(
                c.getId(),
                c.getDataInvestigacao(),
                c.getClassificacaoFinal(),
                c.getCriterioConfirmacao(),
                c.getAutoctone(),
                c.getUfLocalInfeccao(),
                c.getPaisLocalInfeccao(),
                c.getMunicipioLocalInfeccao(),
                c.getDistritoLocalInfeccao(),
                c.getBairroLocalInfeccao(),
                c.getDoencaRelacionadaTrabalho(),
                c.getEvolucaoCaso(),
                c.getDataObito(),
                c.getDataEncerramento()
        );
    }
}
