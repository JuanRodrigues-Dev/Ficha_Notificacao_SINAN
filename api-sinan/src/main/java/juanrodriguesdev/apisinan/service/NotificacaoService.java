package juanrodriguesdev.apisinan.service;

import jakarta.transaction.Transactional;
import juanrodriguesdev.apisinan.dto.*;
import juanrodriguesdev.apisinan.exception.BusinessRuleException;
import juanrodriguesdev.apisinan.exception.ResourceNotFoundException;
import juanrodriguesdev.apisinan.model.*;
import juanrodriguesdev.apisinan.repository.NotificacaoRepository;
import juanrodriguesdev.apisinan.specification.NotificacaoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacaoService {
    private NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;

    }

    // ---------- CRUD ----------

    @Transactional
    public Notificacao criar(NotificacaoRequestDTO dto) {
        Notificacao notificacao = toEntity(dto);
        validarRegrasdeNegocio(notificacao);
        return repository.save(notificacao);
    }

    public Notificacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada: id=" + id));
    }


    @Transactional
    public Notificacao atualizar(Long id ,  NotificacaoRequestDTO dto) {
        buscarPorId(id);
        Notificacao notificacao = toEntity(dto);
        notificacao.setId(id);
        validarRegrasdeNegocio(notificacao);
        return repository.save(notificacao);
    }

    @Transactional
    public void deletar(Long id) {
        Notificacao notificacao = buscarPorId(id);
        repository.delete(notificacao);

    }

    // ---------- Listagem / filtros ----------

    public List<Notificacao> listar() {
        return repository.findAll();
    }

    public Page<Notificacao> listar(String agravo, String nomePaciente, String ufResidencia,
                                    String municipioNotificacao, Sexo sexo, LocalDate dataNotificacaoInicio,
                                    LocalDate dataNotificacaoFim, Pageable  pageable) {
        Specification<Notificacao> spec = Specification.<Notificacao>unrestricted()
                .and(NotificacaoSpecification.comAgravo(agravo))
                .and(NotificacaoSpecification.comNomePaciente(nomePaciente))
                .and(NotificacaoSpecification.comUfResidencia(ufResidencia))
                .and(NotificacaoSpecification.comMunicipioNotificacao(municipioNotificacao))
                .and(NotificacaoSpecification.comSexo(sexo))
                .and(NotificacaoSpecification.comDataNotificacaoEntre(dataNotificacaoInicio, dataNotificacaoFim));

        return repository.findAll(spec, pageable);
    }

    // ---------- RN01: duplicidade ----------

    public List<Notificacao> buscarDuplicadas() {
        List<Long> ids = repository.buscarIdsDuplicados();
        return repository.findAllById(ids);
    }

    // ---------- RN02 / RN03: obrigatoriedade condicional ----------

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
    private void validarResidencia(DadosResidencia dr) {
        boolean resideNoBrasil = estaVazio(dr.getPaisResidencia())
                || dr.getPaisResidencia().equalsIgnoreCase("Brasil");

        if (resideNoBrasil) {
            if (estaVazio(dr.getUfResidencia())) {
                throw new BusinessRuleException(
                        "UF de residência é obrigatória para pacientes residentes no Brasil (RN03)");
            }
            if (estaVazio(dr.getMunicipioResidencia())) {
                throw new BusinessRuleException(
                        "Município de residência é obrigatório quando a UF é informada (RN03)");
            }
        } else if (estaVazio(dr.getPaisResidencia())) {
            throw new BusinessRuleException(
                    "País de residência é obrigatório para pacientes que não residem no Brasil (RN03)");
        }
    }

    private boolean estaVazio(String texto) {
        return texto == null || texto.isBlank();
    }

    // ---------- mapeamento DTO -> entidade ----------

    private Notificacao toEntity(NotificacaoRequestDTO dto) {
        Notificacao notificacao = new Notificacao();
        notificacao.setTipoNotificacao(dto.tipoNotificacao());
        notificacao.setAgravo(dto.agravo());
        notificacao.setDataNotificacao(dto.dataNotificacao());
        notificacao.setUfNotificacao(dto.ufNotificacao());
        notificacao.setMunicipioNotificacao(dto.municipioNotificacao());
        notificacao.setUnidadeSaude(dto.unidadeSaude());
        notificacao.setDadosPessoais(toEntity(dto.dadosPessoais()));
        notificacao.setDadosResidencia(toEntity(dto.dadosResidencia()));
        notificacao.setConclusao(dto.conclusao() != null ? toEntity(dto.conclusao()) : null);
        notificacao.setInvestigador(dto.investigador() != null ? toEntity(dto.investigador()) : null);
        return notificacao;
    }

    private DadosPessoais toEntity(DadosPessoaisRequestDTO dto) {
        DadosPessoais dp = new DadosPessoais();
        dp.setDataPrimeiroSintomas(dto.dataPrimeiroSintomas());
        dp.setNomePaciente(dto.nomePaciente());
        dp.setDataNascimento(dto.dataNascimento());
        dp.setIdade(dto.idade());
        dp.setSexo(dto.sexo());
        dp.setGestante(dto.gestante());
        dp.setRacaCor(dto.racaCor());
        dp.setEscolaridade(dto.escolaridade());
        dp.setNumeroCartaoSus(dto.numeroCartaoSus());
        dp.setNomeMae(dto.nomeMae());
        return dp;
    }

    private DadosResidencia toEntity(DadosResidenciaRequestDTO dto) {
        DadosResidencia dr = new DadosResidencia();
        dr.setUfResidencia(dto.ufResidencia());
        dr.setMunicipioResidencia(dto.municipioResidencia());
        dr.setPaisResidencia(dto.paisResidencia());
        dr.setDistrito(dto.distrito());
        dr.setBairro(dto.bairro());
        dr.setLogradouro(dto.logradouro());
        dr.setNumero(dto.numero());
        dr.setComplemento(dto.complemento());
        dr.setCep(dto.cep());
        dr.setTelefone(dto.telefone());
        dr.setGeoCampo1(dto.geoCampo1());
        dr.setGeoCampo2(dto.geoCampo2());
        dr.setPontoReferencia(dto.pontoReferencia());
        dr.setZona(dto.zona());
        return dr;
    }

    private Conclusao toEntity(ConclusaoRequestDTO dto) {
        Conclusao c = new Conclusao();
        c.setDataInvestigacao(dto.dataInvestigacao());
        c.setClassificacaoFinal(dto.classificacaoFinal());
        c.setCriterioConfirmacao(dto.criterioConfirmacao());
        c.setEvolucaoCaso(dto.evolucaoCaso());
        c.setDataObito(dto.dataObito());
        c.setDataEncerramento(dto.dataEncerramento());
        return c;
    }

    private Investigador toEntity(InvestigadorRequestDTO dto) {
        Investigador inv = new Investigador();
        inv.setCodUnidadeSaude(dto.codUnidadeSaude());
        inv.setMunicipioUnidadeSaude(dto.municipioUnidadeSaude());
        inv.setNomeInvestigador(dto.nomeInvestigador());
        inv.setFuncao(dto.funcao());
        return inv;
    }
}
