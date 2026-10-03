package juanrodriguesdev.apisinan.dto;

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
        String telefone
) {
}
