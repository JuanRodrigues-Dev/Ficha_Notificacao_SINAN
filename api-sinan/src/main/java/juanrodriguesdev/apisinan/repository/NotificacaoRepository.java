package juanrodriguesdev.apisinan.repository;

import jdk.jfr.Registered;
import juanrodriguesdev.apisinan.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository  extends JpaRepository<Notificacao, Long> {

}
