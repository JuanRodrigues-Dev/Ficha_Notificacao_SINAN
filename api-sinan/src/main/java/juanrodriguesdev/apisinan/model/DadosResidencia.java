package juanrodriguesdev.apisinan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dados_residencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DadosResidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ufResidencia;
    private String municipioResidencia;
    private String paisResidencia;
    private String distrito;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String telefone;
    private String geoCampo1;
    private String geoCampo2;
    private String pontoReferencia;

    @Enumerated(EnumType.STRING)
    private Zona zona;
}
