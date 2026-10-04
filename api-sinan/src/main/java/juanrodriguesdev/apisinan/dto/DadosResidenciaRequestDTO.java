package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.Zona;

public record DadosResidenciaRequestDTO(
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
}
