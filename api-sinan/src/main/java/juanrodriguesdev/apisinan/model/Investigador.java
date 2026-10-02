package juanrodriguesdev.apisinan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "investigador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investigador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codUnidadeSaude;
    private String municipioUnidadeSaude;
    private String nomeInvestigador;
    private String funcao;

}
