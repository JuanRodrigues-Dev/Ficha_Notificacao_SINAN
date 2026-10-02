package juanrodriguesdev.apisinan.service;

import jakarta.transaction.Transactional;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.repository.NotificacaoRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

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

    
}
