package juanrodriguesdev.apisinan.dto;

public record InvestigadorRequestDTO(
        String codUnidadeSaude,
        String municipioUnidadeSaude,
        String nomeInvestigador,
        String funcao
) {
}
