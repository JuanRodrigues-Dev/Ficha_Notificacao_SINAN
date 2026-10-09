package juanrodriguesdev.apisinan.controller;

import jakarta.validation.Valid;
import juanrodriguesdev.apisinan.dto.NotificacaoRequestDTO;
import juanrodriguesdev.apisinan.dto.NotificacaoResponseDTO;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.model.Sexo;
import juanrodriguesdev.apisinan.service.NotificacaoService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {
    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<NotificacaoResponseDTO>> listar(
            @RequestParam(required = false) String agravo,
            @RequestParam(required = false) String nomePaciente,
            @RequestParam(required = false) String ufResidencia,
            @RequestParam(required = false) String municipioNotificacao,
            @RequestParam(required = false) Sexo sexo,
            @RequestParam(required = false) LocalDate dataNotificacaoInicio,
            @RequestParam(required = false) LocalDate dataNotificacaoFim,
            @RequestParam(required = false, defaultValue = "false") boolean duplicadas,
            @RequestParam(required = false, defaultValue = "1") int pagina,
            @RequestParam(required = false, defaultValue = "10") int tamanho,
            @RequestParam(required = false) String ordenarPor,
            @RequestParam(required = false, defaultValue = "ASC") String ordem
    ) {
        if (duplicadas) {
            List<NotificacaoResponseDTO> dtos = service.buscarDuplicadas().stream()
                    .map(NotificacaoResponseDTO::fromEntity)
                    .toList();
            return ResponseEntity.ok(new PageImpl<>(dtos));
        }

        Pageable pageable = construirPageable(pagina, tamanho, ordenarPor, ordem);

        Page<Notificacao> resultado = service.listar(agravo, nomePaciente, ufResidencia,
                municipioNotificacao, sexo, dataNotificacaoInicio, dataNotificacaoFim, pageable);

        return ResponseEntity.ok(resultado.map(NotificacaoResponseDTO::fromEntity));
    }

    private static final Set<String> CAMPOS_ORDENAVEIS = Set.of(
            "id", "agravo", "dataNotificacao", "ufNotificacao", "municipioNotificacao");

    private Pageable construirPageable(int pagina, int tamanho, String ordenarPor, String ordem) {
        if (pagina < 1) {
            throw new IllegalArgumentException("O parâmetro 'pagina' deve ser >= 1");
        }
        if (tamanho < 1) {
            throw new IllegalArgumentException("O parâmetro 'tamanho' deve ser >= 1");
        }

        Sort.Direction direcao;
        try {
            direcao = Sort.Direction.fromString(ordem);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("O parâmetro 'ordem' deve ser ASC ou DESC");
        }

        if (ordenarPor == null) {
            return PageRequest.of(pagina - 1, tamanho, Sort.by(Sort.Direction.ASC, "id"));
        }
        if (!CAMPOS_ORDENAVEIS.contains(ordenarPor)) {
            throw new IllegalArgumentException(
                    "O parâmetro 'ordenarPor' deve ser um dos seguintes: " + CAMPOS_ORDENAVEIS);
        }
        return PageRequest.of(pagina - 1, tamanho, Sort.by(direcao, ordenarPor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        Notificacao notificacao = service.buscarPorId(id);
        return ResponseEntity.ok(NotificacaoResponseDTO.fromEntity(notificacao));
    }

    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criar(@Valid @RequestBody NotificacaoRequestDTO dto) {
        Notificacao criada = service.criar(dto);
        return ResponseEntity.created(URI.create("/notificacao/" + criada.getId()))
                .body((NotificacaoResponseDTO.fromEntity(criada)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> atualizar(@PathVariable Long id,
                                                            @Valid @RequestBody NotificacaoRequestDTO dto) {
        Notificacao atualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(NotificacaoResponseDTO.fromEntity(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
