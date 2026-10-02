package juanrodriguesdev.apisinan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notificacao")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agravo;
    private LocalDate dataNotificacao;
    private String ufNotificacao;
    private String municipioNotificacao;
    private String unidadeSaude;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dados_pessoais_id")
    private DadosPessoais dadosPessoais;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dados_residencia_id")
    private DadosResidencia dadosResidencia;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "conclusao_id")
    private Conclusao conclusao;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "investigador_id")
    private Investigador investigador;
}
