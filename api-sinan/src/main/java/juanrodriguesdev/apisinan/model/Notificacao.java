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
@Table(name = "notificacao")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O agravo/doença é obrigatório")
    private String agravo;

    @NotNull(message = "A data da notificação é obrigatória")
    private LocalDate dataNotificacao;

    @NotBlank(message = "A UF de notificação é obrigatória")
    private String ufNotificacao;

    @NotBlank(message = "O município de notificação é obrigatório")
    private String municipioNotificacao;

    @NotBlank(message = "A unidade de saúde é obrigatória")
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
