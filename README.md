# API SINAN — Programação para a Web I

API REST para gerenciamento de notificações de agravos, baseada na Ficha
de Notificação/Conclusão do SINAN (Ministério da Saúde).

## Integrantes

- Juan Rodrigues — JuanRodriguesDev

## Requisitos

- Java 17+ (testado com Java 21)
- Maven 3.8+
- Spring Boot 4.1.1

O projeto usa **H2** (banco em memória) por padrão, não é necessário instalar PostgreSQL para rodar.

## Como executar

Pelo IntelliJ: rode a classe `ApiSinanApplication`.

Pelo terminal:
```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

Console do H2 (visualizar o banco): `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:sinandb`, usuário `sa`, sem senha)

> O banco é em memória: todos os dados são apagados a cada restart. Um
> arquivo `data.sql` popula automaticamente alguns registros de teste
> (incluindo um par de notificações propositalmente duplicadas, para
> testar o filtro de duplicidade).

## Arquitetura

A ficha do SINAN foi modelada em **5 entidades separadas**, uma para cada
seção do formulário original, relacionadas via `@OneToOne` unidirecional
a partir de `Notificacao` (entidade raiz):

| Entidade | Seção da ficha |
|---|---|
| `Notificacao` | Dados Gerais |
| `DadosPessoais` | Notificação Individual |
| `DadosResidencia` | Dados de Residência |
| `Conclusao` | Conclusão |
| `Investigador` | Investigador |

A API não expõe as entidades JPA diretamente — usa **DTOs** de entrada
(`*RequestDTO`) e saída (`*ResponseDTO`) para desacoplar o contrato da
API da estrutura do banco.

## Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/notificacao` | Lista com filtros opcionais |
| GET | `/notificacao/{id}` | Busca uma notificação por id |
| POST | `/notificacao` | Cria uma notificação |
| PUT | `/notificacao/{id}` | Atualiza uma notificação |
| DELETE | `/notificacao/{id}` | Remove uma notificação |

### Filtros disponíveis (`GET /notificacao`)

```
GET /notificacao?agravo=Dengue
GET /notificacao?nomePaciente=Maria
GET /notificacao?ufResidencia=PB
GET /notificacao?municipioNotificacao=Cajazeiras
GET /notificacao?sexo=F
GET /notificacao?dataNotificacaoInicio=2026-09-01&dataNotificacaoFim=2026-09-30
GET /notificacao?duplicadas=true
```

Os filtros podem ser combinados livremente (ex: `?agravo=Dengue&ufResidencia=PB`).

## Regras de negócio implementadas

- **RN01 (duplicidade)** — `?duplicadas=true` agrupa notificações por
  agravo, nome do paciente, data de nascimento e nome da mãe (ignorando
  maiúsculas/minúsculas e espaços), marcando como duplicadas as que têm
  datas de notificação com diferença de até 3 dias. Registros com algum
  desses campos em branco são ignorados na verificação. Implementada via
  query SQL nativa (self-join) no `NotificacaoRepository`.
- **RN02 (obrigatoriedade condicional)** — idade só é obrigatória se a
  data de nascimento não for informada; gestante só é obrigatória quando
  o sexo do paciente é feminino.
- **RN03 (residência)** — UF e município são obrigatórios para pacientes
  residentes no Brasil; país é obrigatório para residentes no exterior.

Violações de RN02/RN03 retornam `422 Unprocessable Entity`.

## Tratamento de erros

Todas as respostas de erro seguem o padrão **Problem Detail** (RFC 9457),
via `GlobalExceptionHandler`:

- `400` — dados malformados ou parâmetro inválido
- `404` — recurso não encontrado
- `422` — violação de regra de negócio (RN02/RN03)

## Em andamento / pendente

- **Paginação e ordenação** (desafio opcional do enunciado) — em
  implementação, pausado temporariamente.
- **Front-end HTML/CSS/JS puro** — opcional, ainda não iniciado.
