package juanrodriguesdev.apisinan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import juanrodriguesdev.apisinan.model.Escolaridade;
import juanrodriguesdev.apisinan.model.Gestante;
import juanrodriguesdev.apisinan.model.RacaCor;
import juanrodriguesdev.apisinan.model.Sexo;

import java.time.LocalDate;

public record DadosPessoaisRequestDTO(

        LocalDate dataPrimeiroSintomas,
        @NotBlank(message = "O nome do paciente é obrigatório")
        String nomePaciente,
        LocalDate dataNascimento,
        Integer idade,
        @NotNull(message = "O sexo do paciente é obrigatório")
        Sexo sexo,
        Gestante gestante,
        RacaCor racaCor,
        Escolaridade escolaridade,
        String numeroCartaoSus,
        String nomeMae
) {}
