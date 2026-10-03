package juanrodriguesdev.apisinan.specification;

import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.model.Sexo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class NotificacaoSpecification {
    private NotificacaoSpecification(){}

    public static Specification<Notificacao> comAgravo(String agravo){
        if(!StringUtils.hasText(agravo)) {
            return Specification.unrestricted();
        }
        return  ((root, query, cb) ->
                cb.equal(cb.lower(root.get("agravo")), agravo.toLowerCase().trim()));

    }

    public static Specification<Notificacao> comNomePaciente(String nome){
        if (!StringUtils.hasText(nome)) {

            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("dadosPessoais").get("nomePaciente")),
                        "%" + nome.toLowerCase().trim() + "%");
    }

    public static Specification<Notificacao> comUfResidencia(String uf){
        if (!StringUtils.hasText(uf)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("dadosResidencia").get("ufResidencia")), uf.toLowerCase().trim());
    }

    public static Specification<Notificacao> comMunicipioNotificacao(String municipio) {
        if (!StringUtils.hasText(municipio)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("municipioNotificacao")), municipio.toLowerCase().trim());
    }

    public static Specification<Notificacao> comSexo(Sexo sexo) {
        if (sexo == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.equal(root.get("dadosPessoais").get("sexo"), sexo);
    }

    public static Specification<Notificacao> comDataNotificacaoEntre(LocalDate inicio, LocalDate fim) {
        if (inicio == null && fim == null) {
            return Specification.unrestricted();
        }
        if (inicio != null && fim != null) {
            return (root, query, cb) ->
                    cb.between(root.get("dataNotificacao"), inicio, fim);
        }
        if (inicio != null) {
            return (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("dataNotificacao"), inicio);
        }
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("dataNotificacao"), fim);
    }
}