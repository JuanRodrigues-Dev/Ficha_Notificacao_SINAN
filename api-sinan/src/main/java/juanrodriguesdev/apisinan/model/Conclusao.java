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
    private String classifcacaoFinal;
    private String criterioConfirmacao;
    private String evolucaoCaso;
    private LocalDate dataObito;
    private LocalDate dataEncerramento;
    
}
