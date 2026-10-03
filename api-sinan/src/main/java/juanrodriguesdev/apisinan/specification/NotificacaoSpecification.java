package juanrodriguesdev.apisinan.specification;

import juanrodriguesdev.apisinan.model.Notificacao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class NotificacaoSpecification {
    private NotificacaoSpecification(){}

    public static Specification<Notificacao> comAgravo(String agravo){
        if(!StringUtils.hasText(agravo))return null;
        return  ((root, query, cb) ->
                cb.equal(cb.lower(root.get("agravo")), agravo.toLowerCase().trim()));

    }

    public static Specification<Notificacao> comNomePaciente(String nome){
        if (!StringUtils.hasText(nome)) return null;
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("dadosPessoais").get("nomePaciente")),
                        "%" + nome.toLowerCase().trim() + "%");
    }

    public static Specification<Notificacao> comUfResidencia(String uf){
        if (!StringUtils.hasText(uf)) return null;
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("dadosResidencia").get("ufResidencia")), uf.toLowerCase().trim());
    }
}
