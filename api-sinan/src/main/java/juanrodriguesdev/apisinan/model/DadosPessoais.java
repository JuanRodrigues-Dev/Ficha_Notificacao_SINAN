package juanrodriguesdev.apisinan.model;


import jakarta.persistence.*;
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
    private String nomePaciente;
    private LocalDate dataNascimento;
    private int idade;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Enumerated(EnumType.STRING)
    private Gestante gestante;

    private String nomeMae;

}
