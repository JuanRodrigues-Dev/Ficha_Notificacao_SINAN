package juanrodriguesdev.apisinan.service;

import jakarta.transaction.Transactional;
import juanrodriguesdev.apisinan.exeption.ResourceNotFoundException;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {
    private NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;

    }

    @Transactional
    public Notificacao criar(Notificacao notificacao) {
        return repository.save(notificacao);
    }

    public Notificacao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada: id=" + id));
    }

    public List<Notificacao> listarTodas() {
        return repository.findAll();
    }

    @Transactional
    public Notificacao atualizar(Long id ,  Notificacao dadosAtualizados) {
        buscarPorId(id);
        dadosAtualizados.setId(id);
        return repository.save(dadosAtualizados);
    }

    @Transactional
    public void deletar(Long id) {
        Notificacao notificacao = buscarPorId(id);
        repository.delete(notificacao);

    }
}
