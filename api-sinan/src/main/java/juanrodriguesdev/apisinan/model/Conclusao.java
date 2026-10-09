package juanrodriguesdev.apisinan.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "conclusão")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conclusao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataInvestigacao;
    private String classificacaoFinal;
    private String criterioConfirmacao;
    private String evolucaoCaso;
    private LocalDate dataObito;
    private LocalDate dataEncerramento;
    @Enumerated(EnumType.STRING)
    private Autoctonia autoctone;
    private String ufLocalInfeccao;
    private String paisLocalInfeccao;
    private String municipioLocalInfeccao;
    private String distritoLocalInfeccao;
    private String bairroLocalInfeccao;
    @Enumerated(EnumType.STRING)
    private DoencaRelacionadaTrabalho doencaRelacionadaTrabalho;

}
