package juanrodriguesdev.apisinan.dto;

import juanrodriguesdev.apisinan.model.DadosPessoais;
import juanrodriguesdev.apisinan.model.Gestante;
import juanrodriguesdev.apisinan.model.Sexo;

import java.time.LocalDate;

public record DadosPessoaisResponseDTO(
        Long id,
        LocalDate dataPrimeiroSintomas,
        String nomePaciente,
        LocalDate dataNascimento,
        Integer idade,
        Sexo sexo,
        Gestante gestante,
        String nomeMae
) {
    public static  DadosPessoaisResponseDTO fromEntity(DadosPessoais dp){
        return new DadosPessoaisResponseDTO(
                dp.getId(),
                dp.getDataPrimeiroSintomas(),
                dp.getNomePaciente(),
                dp.getDataNascimento(),
                dp.getIdade(),
                dp.getSexo(),
                dp.getGestante(),
                dp.getNomeMae()
        );
    }
}
