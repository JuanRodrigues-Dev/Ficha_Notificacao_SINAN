package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Investigador;

public record InvestigadorResponseDTO(
        Long id,
        String codUnidadeSaude,
        String municipioUnidadeSaude,
        String nomeInvestigador,
        String funcao
) {
    public static InvestigadorResponseDTO fromEntity(Investigador inv) {
        return new InvestigadorResponseDTO(
                inv.getId(),
                inv.getCodUnidadeSaude(),
                inv.getMunicipioUnidadeSaude(),
                inv.getNomeInvestigador(),
                inv.getFuncao()
        );
    }
}
