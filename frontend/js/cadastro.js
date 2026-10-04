import { api } from "./api.js";

const form = document.getElementById("form-notificacao");
const divMensagem = document.getElementById("mensagem");
const tituloPagina = document.getElementById("titulo-pagina");

const idNaUrl = new URLSearchParams(window.location.search).get("id");
const modoEdicao = idNaUrl !== null;

function mostrarErro(mensagem) {
  divMensagem.innerHTML = `<div class="mensagem-erro">${mensagem}</div>`;
}

function mostrarSucesso(mensagem) {
  divMensagem.innerHTML = `<div class="mensagem-sucesso">${mensagem}</div>`;
}

/** Converte string vazia em null — a API trata null como "campo não informado". */
function vazioParaNull(valor) {
  return valor === "" ? null : valor;
}

function valorCampo(id) {
  return vazioParaNull(document.getElementById(id).value);
}

function valorNumerico(id) {
  const valor = document.getElementById(id).value;
  return valor === "" ? null : Number(valor);
}

/**
 * Monta o DTO de Conclusao a partir do formulário. Se todos os campos
 * estiverem vazios, retorna null — já que essa seção é opcional e a
 * API espera null, não um objeto com todos os campos vazios.
 */
function montarConclusao() {
  const dto = {
    dataInvestigacao: valorCampo("dataInvestigacao"),
    classificacaoFinal: valorCampo("classificacaoFinal"),
    criterioConfirmacao: valorCampo("criterioConfirmacao"),
    evolucaoCaso: valorCampo("evolucaoCaso"),
    dataObito: valorCampo("dataObito"),
    dataEncerramento: valorCampo("dataEncerramento"),
  };
  const todosVazios = Object.values(dto).every((v) => v === null);
  return todosVazios ? null : dto;
}

function montarInvestigador() {
  const dto = {
    codUnidadeSaude: valorCampo("codUnidadeSaude"),
    municipioUnidadeSaude: valorCampo("municipioUnidadeSaude"),
    nomeInvestigador: valorCampo("nomeInvestigador"),
    funcao: valorCampo("funcao"),
  };
  const todosVazios = Object.values(dto).every((v) => v === null);
  return todosVazios ? null : dto;
}

function montarDtoDoFormulario() {
  return {
    tipoNotificacao: valorCampo("tipoNotificacao"),
    agravo: valorCampo("agravo"),
    dataNotificacao: valorCampo("dataNotificacao"),
    ufNotificacao: valorCampo("ufNotificacao"),
    municipioNotificacao: valorCampo("municipioNotificacao"),
    unidadeSaude: valorCampo("unidadeSaude"),
    dadosPessoais: {
      dataPrimeiroSintomas: valorCampo("dataPrimeiroSintomas"),
      nomePaciente: valorCampo("nomePaciente"),
      dataNascimento: valorCampo("dataNascimento"),
      idade: valorNumerico("idade"),
      sexo: valorCampo("sexo"),
      gestante: valorCampo("gestante"),
      racaCor: valorCampo("racaCor"),
      escolaridade: valorCampo("escolaridade"),
      numeroCartaoSus: valorCampo("numeroCartaoSus"),
      nomeMae: valorCampo("nomeMae"),
    },
    dadosResidencia: {
      ufResidencia: valorCampo("ufResidencia"),
      municipioResidencia: valorCampo("municipioResidencia"),
      paisResidencia: valorCampo("paisResidencia"),
      distrito: valorCampo("distrito"),
      bairro: valorCampo("bairro"),
      logradouro: valorCampo("logradouro"),
      numero: valorCampo("numero"),
      geoCampo1: valorCampo("geoCampo1"),
      geoCampo2: valorCampo("geoCampo2"),
      pontoReferencia: valorCampo("pontoReferencia"),
      zona: valorCampo("zona"),
      complemento: valorCampo("complemento"),
      cep: valorCampo("cep"),
      telefone: valorCampo("telefone"),
    },
    conclusao: montarConclusao(),
    investigador: montarInvestigador(),
  };
}

/** Preenche o formulário com os dados de uma notificação existente (modo edição). */
function preencherFormulario(n) {
  document.getElementById("tipoNotificacao").value = n.tipoNotificacao ?? "";
  document.getElementById("agravo").value = n.agravo ?? "";
  document.getElementById("dataNotificacao").value = n.dataNotificacao ?? "";
  document.getElementById("ufNotificacao").value = n.ufNotificacao ?? "";
  document.getElementById("municipioNotificacao").value = n.municipioNotificacao ?? "";
  document.getElementById("unidadeSaude").value = n.unidadeSaude ?? "";

  const dp = n.dadosPessoais ?? {};
  document.getElementById("dataPrimeiroSintomas").value = dp.dataPrimeiroSintomas ?? "";
  document.getElementById("nomePaciente").value = dp.nomePaciente ?? "";
  document.getElementById("dataNascimento").value = dp.dataNascimento ?? "";
  document.getElementById("idade").value = dp.idade ?? "";
  document.getElementById("sexo").value = dp.sexo ?? "";
  document.getElementById("gestante").value = dp.gestante ?? "";
  document.getElementById("racaCor").value = dp.racaCor ?? "";
  document.getElementById("escolaridade").value = dp.escolaridade ?? "";
  document.getElementById("numeroCartaoSus").value = dp.numeroCartaoSus ?? "";
  document.getElementById("nomeMae").value = dp.nomeMae ?? "";

  const dr = n.dadosResidencia ?? {};
  document.getElementById("ufResidencia").value = dr.ufResidencia ?? "";
  document.getElementById("municipioResidencia").value = dr.municipioResidencia ?? "";
  document.getElementById("paisResidencia").value = dr.paisResidencia ?? "";
  document.getElementById("distrito").value = dr.distrito ?? "";
  document.getElementById("bairro").value = dr.bairro ?? "";
  document.getElementById("logradouro").value = dr.logradouro ?? "";
  document.getElementById("numero").value = dr.numero ?? "";
  document.getElementById("geoCampo1").value = dr.geoCampo1 ?? "";
  document.getElementById("geoCampo2").value = dr.geoCampo2 ?? "";
  document.getElementById("pontoReferencia").value = dr.pontoReferencia ?? "";
  document.getElementById("zona").value = dr.zona ?? "";
  document.getElementById("complemento").value = dr.complemento ?? "";
  document.getElementById("cep").value = dr.cep ?? "";
  document.getElementById("telefone").value = dr.telefone ?? "";

  const c = n.conclusao ?? {};
  document.getElementById("dataInvestigacao").value = c.dataInvestigacao ?? "";
  document.getElementById("classificacaoFinal").value = c.classificacaoFinal ?? "";
  document.getElementById("criterioConfirmacao").value = c.criterioConfirmacao ?? "";
  document.getElementById("evolucaoCaso").value = c.evolucaoCaso ?? "";
  document.getElementById("dataObito").value = c.dataObito ?? "";
  document.getElementById("dataEncerramento").value = c.dataEncerramento ?? "";

  const inv = n.investigador ?? {};
  document.getElementById("codUnidadeSaude").value = inv.codUnidadeSaude ?? "";
  document.getElementById("municipioUnidadeSaude").value = inv.municipioUnidadeSaude ?? "";
  document.getElementById("nomeInvestigador").value = inv.nomeInvestigador ?? "";
  document.getElementById("funcao").value = inv.funcao ?? "";
}

async function carregarParaEdicao() {
  try {
    const notificacao = await api.buscarPorId(idNaUrl);
    preencherFormulario(notificacao);
  } catch (erro) {
    mostrarErro(`Não foi possível carregar a notificação: ${erro.message}`);
  }
}

form.addEventListener("submit", async (evento) => {
  evento.preventDefault();
  divMensagem.innerHTML = "";

  const dto = montarDtoDoFormulario();

  try {
    if (modoEdicao) {
      await api.atualizar(idNaUrl, dto);
      mostrarSucesso("Notificação atualizada com sucesso!");
    } else {
      await api.criar(dto);
      mostrarSucesso("Notificação criada com sucesso!");
      form.reset();
    }
    setTimeout(() => {
      window.location.href = "index.html";
    }, 1200);
  } catch (erro) {
    mostrarErro(erro.message);
  }
});

if (modoEdicao) {
  tituloPagina.textContent = `SINAN — Editar Notificação #${idNaUrl}`;
  carregarParaEdicao();
}
