package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.DadosResidencia;
import juanrodriguesdev.apisinan.model.Zona;

public record DadosResidenciaResponseDTO(
        Long id,
        String ufResidencia,
        String municipioResidencia,
        String paisResidencia,
        String distrito,
        String bairro,
        String logradouro,
        String numero,
        String complemento,
        String cep,
        String telefone,
        String geoCampo1,
        String geoCampo2,
        String pontoReferencia,
        Zona zona
) {
    public static DadosResidenciaResponseDTO fromEntity(DadosResidencia dr) {
        return new DadosResidenciaResponseDTO(
                dr.getId(),
                dr.getUfResidencia(),
                dr.getMunicipioResidencia(),
                dr.getPaisResidencia(),
                dr.getDistrito(),
                dr.getBairro(),
                dr.getLogradouro(),
                dr.getNumero(),
                dr.getComplemento(),
                dr.getCep(),
                dr.getTelefone(),
                dr.getGeoCampo1(),
                dr.getGeoCampo2(),
                dr.getPontoReferencia(),
                dr.getZona()
        );
    }
}

