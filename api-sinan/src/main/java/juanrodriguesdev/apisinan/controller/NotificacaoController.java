package juanrodriguesdev.apisinan.controller;

import jakarta.validation.Valid;
import juanrodriguesdev.apisinan.dto.NotificacaoRequestDTO;
import juanrodriguesdev.apisinan.dto.NotificacaoResponseDTO;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.model.Sexo;
import juanrodriguesdev.apisinan.service.NotificacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {
    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificacaoResponseDTO> listar(@RequestParam(required = false) String agravo,
                                               @RequestParam(required = false) String nomePaciente,
                                               @RequestParam(required = false) String ufResidencia,
                                               @RequestParam(required = false) String municipioNotificacao,
                                               @RequestParam(required = false) Sexo sexo,
                                               @RequestParam(required = false) LocalDate dataNotificacaoInicio,
                                               @RequestParam(required = false) LocalDate dataNotificacaoFim,
                                               @RequestParam(required = false, defaultValue = "false") boolean duplicadas) {
        List<Notificacao> resultado = duplicadas
                ? service.buscarDuplicadas()
                : service.listar(agravo, nomePaciente, ufResidencia, municipioNotificacao,
                sexo, dataNotificacaoInicio, dataNotificacaoFim);

        return resultado.stream()
                .map(NotificacaoResponseDTO::fromEntity)
                .toList();
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
