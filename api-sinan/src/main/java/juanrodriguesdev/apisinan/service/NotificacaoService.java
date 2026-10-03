package juanrodriguesdev.apisinan.service;

import jakarta.transaction.Transactional;
import juanrodriguesdev.apisinan.exception.BusinessRuleException;
import juanrodriguesdev.apisinan.exception.ResourceNotFoundException;
import juanrodriguesdev.apisinan.model.DadosPessoais;
import juanrodriguesdev.apisinan.model.DadosResidencia;
import juanrodriguesdev.apisinan.model.Notificacao;
import juanrodriguesdev.apisinan.model.Sexo;
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

    private void validarRegrasdeNegocio(Notificacao notificacao) {
        validarIdadeOuDataNascimento(notificacao.getDadosPessoais());
        validarGestante(notificacao.getDadosPessoais());
        validarResidencia(notificacao.getDadosResidencia());
    }

    private void validarIdadeOuDataNascimento(DadosPessoais dp){
        boolean temDataNascimento = dp.getDataNascimento() != null;
        boolean temIdade = dp.getIdade() !=null;
        if (!temDataNascimento && !temIdade) {
            throw new BusinessRuleException(
                    "Informe a data de nascimento ou, se desconhecida, a idade do paciente (RN02)");
        }

    }

    private void validarGestante(DadosPessoais dp){
        if (dp.getSexo() == Sexo.F && dp.getGestante() == null) {
            throw new BusinessRuleException(
                    "O campo gestante é obrigatório quando o sexo do paciente é feminino (RN02)");
        }
    }
    private void validarResidencia(DadosResidencia dr){
        boolean resideNoBrasil = dr.getPaisResidencia() == null
                || dr.getPaisResidencia().isBlank()
                || dr.getPaisResidencia().equalsIgnoreCase("Brasil");

        if(resideNoBrasil){
            if (dr.getUfResidencia() == null || dr.getUfResidencia().isBlank()) {
                throw new BusinessRuleException(
                        "UF de residência é obrigatória para pacientes residentes no Brasil (RN03)");
            }
            if (dr.getMunicipioResidencia() == null || dr.getMunicipioResidencia().isBlank()) {
                throw new BusinessRuleException(
                        "Município de residência é obrigatório quando a UF é informada (RN03)");
            }else if (dr.getPaisResidencia().isBlank()) {
                throw new BusinessRuleException(
                        "País de residência é obrigatório para pacientes que não residem no Brasil (RN03)");
            }
        }
    }
}
