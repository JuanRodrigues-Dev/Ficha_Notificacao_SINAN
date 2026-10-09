import { api } from "./api.js";

const formFiltros = document.getElementById("form-filtros");
const corpoTabela = document.getElementById("corpo-tabela");
const divMensagem = document.getElementById("mensagem");
const divPaginacao = document.getElementById("paginacao");
const infoPagina = document.getElementById("info-pagina");
const btnAnterior = document.getElementById("btn-anterior");
const btnProxima = document.getElementById("btn-proxima");
const btnLimpar = document.getElementById("btn-limpar");

// pagina interna é 0-based (como o Spring Data), mas exibimos 1-based
let paginaAtual = 0;

function lerFiltrosDoFormulario() {
  const dados = new FormData(formFiltros);
  return {
    agravo: dados.get("agravo"),
    nomePaciente: dados.get("nomePaciente"),
    ufResidencia: dados.get("ufResidencia"),
    municipioNotificacao: dados.get("municipioNotificacao"),
    sexo: dados.get("sexo"),
    dataNotificacaoInicio: dados.get("dataNotificacaoInicio"),
    dataNotificacaoFim: dados.get("dataNotificacaoFim"),
    ordenarPor: dados.get("ordenarPor"),
    ordem: dados.get("ordem"),
    tamanho: dados.get("tamanho"),
    duplicadas: document.getElementById("f-duplicadas").checked,
    pagina: paginaAtual + 1, // API espera 1-based
  };
}

function mostrarErro(mensagem) {
  divMensagem.innerHTML = `<div class="mensagem-erro">${mensagem}</div>`;
}

function limparMensagem() {
  divMensagem.innerHTML = "";
}

function formatarData(data) {
  if (!data) return "-";
  const [ano, mes, dia] = data.split("-");
  return `${dia}/${mes}/${ano}`;
}

function renderizarLinhas(notificacoes, apenasDuplicadas) {
  if (notificacoes.length === 0) {
    corpoTabela.innerHTML = `<tr><td colspan="6" class="vazio">Nenhuma notificação encontrada.</td></tr>`;
    return;
  }

  corpoTabela.innerHTML = notificacoes
    .map((n) => `
      <tr class="${apenasDuplicadas ? "duplicada" : ""}">
        <td>${n.id}</td>
        <td>${n.agravo}</td>
        <td>${n.dadosPessoais?.nomePaciente ?? "-"}</td>
        <td>${formatarData(n.dataNotificacao)}</td>
        <td>${n.municipioNotificacao} / ${n.ufNotificacao}</td>
        <td>
          <a href="cadastro.html?id=${n.id}">Editar</a>
          &nbsp;|&nbsp;
          <button class="btn-perigo" data-id="${n.id}">Excluir</button>
        </td>
      </tr>
    `)
    .join("");

  corpoTabela.querySelectorAll("button[data-id]").forEach((botao) => {
    botao.addEventListener("click", () => excluirNotificacao(botao.dataset.id));
  });
}

async function excluirNotificacao(id) {
  if (!confirm(`Tem certeza que deseja excluir a notificação #${id}?`)) return;
  try {
    await api.deletar(id);
    carregarListagem();
  } catch (erro) {
    mostrarErro(erro.message);
  }
}

function atualizarPaginacao(pagina) {
  if (pagina.totalPages <= 1) {
    divPaginacao.style.display = "none";
    return;
  }
  divPaginacao.style.display = "flex";
  infoPagina.textContent = `Página ${pagina.number + 1} de ${pagina.totalPages} (${pagina.totalElements} notificações)`;
  btnAnterior.disabled = pagina.first;
  btnProxima.disabled = pagina.last;
}

async function carregarListagem() {
  limparMensagem();
  corpoTabela.innerHTML = `<tr><td colspan="6" class="vazio">Carregando...</td></tr>`;

  const filtros = lerFiltrosDoFormulario();
  try {
    const pagina = await api.listar(filtros);
    renderizarLinhas(pagina.content, filtros.duplicadas);
    atualizarPaginacao(pagina);
  } catch (erro) {
    corpoTabela.innerHTML = `<tr><td colspan="6" class="vazio">Erro ao carregar dados.</td></tr>`;
    mostrarErro(erro.message);
  }
}

formFiltros.addEventListener("submit", (evento) => {
  evento.preventDefault();
  paginaAtual = 0;
  carregarListagem();
});

btnLimpar.addEventListener("click", () => {
  formFiltros.reset();
  paginaAtual = 0;
  carregarListagem();
});

btnAnterior.addEventListener("click", () => {
  if (paginaAtual > 0) {
    paginaAtual--;
    carregarListagem();
  }
});

btnProxima.addEventListener("click", () => {
  paginaAtual++;
  carregarListagem();
});

carregarListagem();
