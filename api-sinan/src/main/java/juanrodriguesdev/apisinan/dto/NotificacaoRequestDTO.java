package juanrodriguesdev.apisinan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NotificacaoRequestDTO(
        @NotBlank(message = "O agravo/doença é obrigatório")
        String agravo,

        @NotNull(message = "A data da notificação é obrigatória")
        LocalDate dataNotificacao,

        @NotBlank(message = "A UF de notificação é obrigatória")
        String ufNotificacao,

        @NotBlank(message = "O município de notificação é obrigatório")
        String municipioNotificacao,

        @NotBlank(message = "A unidade de saúde é obrigatória")
        String unidadeSaude,

        @NotNull(message = "Os dados pessoais são obrigatórios")
        @Valid
        DadosPessoaisRequestDTO dadosPessoais,

        @NotNull(message = "Os dados de residência são obrigatórios")
        @Valid
        DadosResidenciaRequestDTO dadosResidencia,

        @Valid
        ConclusaoRequestDTO conclusao,

        @Valid
        InvestigadorRequestDTO investigador
) {
}
