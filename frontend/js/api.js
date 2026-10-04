// Centraliza toda comunicação com a API. Nenhuma outra parte do
// front-end deve chamar fetch() diretamente para /notificacao.

const API_BASE = "http://localhost:8080/notificacao";

/**
 * Monta a URL de listagem a partir de um objeto de filtros,
 * ignorando campos vazios/undefined.
 */
function montarUrlListagem(filtros) {
  const params = new URLSearchParams();
  Object.entries(filtros).forEach(([chave, valor]) => {
    if (valor !== null && valor !== undefined && valor !== "") {
      params.append(chave, valor);
    }
  });
  const query = params.toString();
  return query ? `${API_BASE}?${query}` : API_BASE;
}

/**
 * Trata uma resposta HTTP: se não for 2xx, extrai o Problem Detail
 * (RFC 9457) devolvido pela API e lança um erro com a mensagem certa.
 */
async function tratarResposta(resposta) {
  if (resposta.ok) {
    if (resposta.status === 204) return null; // DELETE não tem corpo
    return resposta.json();
  }

  let detalhe = `Erro ${resposta.status} ao comunicar com a API`;
  try {
    const problema = await resposta.json();
    detalhe = problema.detail || detalhe;
    if (problema.erros) {
      detalhe += "\n" + problema.erros.join("\n");
    }
  } catch (_) {
    // resposta sem corpo JSON; mantém a mensagem genérica
  }
  throw new Error(detalhe);
}

export const api = {
  async listar(filtros) {
    const resposta = await fetch(montarUrlListagem(filtros));
    return tratarResposta(resposta);
  },

  async buscarPorId(id) {
    const resposta = await fetch(`${API_BASE}/${id}`);
    return tratarResposta(resposta);
  },

  async criar(dto) {
    const resposta = await fetch(API_BASE, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dto),
    });
    return tratarResposta(resposta);
  },

  async atualizar(id, dto) {
    const resposta = await fetch(`${API_BASE}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dto),
    });
    return tratarResposta(resposta);
  },

  async deletar(id) {
    const resposta = await fetch(`${API_BASE}/${id}`, { method: "DELETE" });
    return tratarResposta(resposta);
  },
};
