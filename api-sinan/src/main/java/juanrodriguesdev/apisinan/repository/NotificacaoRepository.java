package juanrodriguesdev.apisinan.repository;

import jdk.jfr.Registered;
import juanrodriguesdev.apisinan.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository  extends JpaRepository<Notificacao, Long> , JpaSpecificationExecutor {
    @Query(value = """
        SELECT DISTINCT n1.id
        FROM notificacao n1
        JOIN dados_pessoais dp1 ON n1.dados_pessoais_id = dp1.id
        JOIN notificacao n2 ON n2.id <> n1.id
        JOIN dados_pessoais dp2 ON n2.dados_pessoais_id = dp2.id
        WHERE LOWER(TRIM(n1.agravo)) = LOWER(TRIM(n2.agravo))
          AND LOWER(TRIM(dp1.nome_paciente)) = LOWER(TRIM(dp2.nome_paciente))
          AND dp1.data_nascimento = dp2.data_nascimento
          AND LOWER(TRIM(dp1.nome_mae)) = LOWER(TRIM(dp2.nome_mae))
          AND ABS(DATEDIFF('DAY', n1.data_notificacao, n2.data_notificacao)) <= 3
          AND dp1.nome_mae IS NOT NULL AND TRIM(dp1.nome_mae) <> ''
          AND dp2.nome_mae IS NOT NULL AND TRIM(dp2.nome_mae) <> ''
        """, nativeQuery = true)
    List<Long> buscarIdsDuplicados();
}
