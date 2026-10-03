package juanrodriguesdev.apisinan.controller;

import jakarta.validation.Valid;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.service.NotificacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {
    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notificacao> listar(@RequestParam(required = false) String agravo,
                                    @RequestParam(required = false) String nomePaciente,
                                    @RequestParam(required = false) String ufResidencia,
                                    @RequestParam(required = false, defaultValue = "false") boolean duplicadas) {
        if (duplicadas) {
            return service.buscarDuplicadas();
        }
        return service.listar(agravo, nomePaciente, ufResidencia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        Notificacao notificacao = service.buscarPorId(id);
        return ResponseEntity.ok(notificacao);
    }

    @PostMapping
    public ResponseEntity<Notificacao> criar(@Valid @RequestBody Notificacao notificacao) {
        Notificacao criada = service.criar(notificacao);
        return ResponseEntity.created(URI.create("/notificacao/" + criada.getId())).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> atualizar(@PathVariable Long id, @Valid @RequestBody Notificacao notificacao) {
        Notificacao atualizada = service.atualizar(id, notificacao);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
