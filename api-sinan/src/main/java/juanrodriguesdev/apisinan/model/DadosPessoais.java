package juanrodriguesdev.apisinan.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dados_pessoais")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class DadosPessoais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataPrimeiroSintomas;
    @NotBlank(message = "O nome do paciente é obrigatório")
    private String nomePaciente;


    private LocalDate dataNascimento;
    private Integer idade;
    @NotNull(message = "O sexo do paciente é obrigatório")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Enumerated(EnumType.STRING)
    private Gestante gestante;
    @Enumerated(EnumType.STRING)
    private RacaCor racaCor;
    @Enumerated(EnumType.STRING)
    private Escolaridade escolaridade;
    private String numeroCartaoSus;
    private String nomeMae;

}
