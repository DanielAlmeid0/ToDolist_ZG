let tarefas = []

let proximoId = 1

let idEmEdicao = null

let filtroAtual = "TODOS"

const form = document.getElementById("task-form");
const inputTaskId = document.getElementById("task-id");
const inputNome = document.getElementById("nome");
const inputDataTermino = document.getElementById("dataTermino");
const selectPrioridade = document.getElementById("prioridade");
const inputCategoria = document.getElementById("categoria");
const selectStatus = document.getElementById("status");
const btnCancelar = document.getElementById("btn-cancelar");
const formTitulo = document.getElementById("form-title");
 
const erroNome = document.getElementById("erro-nome");
const erroDataTermino = document.getElementById("erro-dataTermino");
const erroPrioridade = document.getElementById("erro-prioridade");
 
const tbody = document.getElementById("tarefas-tbody");
const listaVazia = document.getElementById("lista-vazia");
const filtroStatus = document.getElementById("filtro-status");

/**
 * Insere a tarefa na posição correta do array, mantendo a lista
 * sempre ordenada por prioridade.
 */

function inserirTarefaOrdenada(tarefa) {
  let posicao = tarefas.findIndex((t) => t.prioridade < tarefa.prioridade);
  if (posicao === -1) {
    tarefas.push(tarefa);
  } else {
    tarefas.splice(posicao, 0, tarefa);
  }
}

/**
 * Valida os campos do formulário. Retorna true se está tudo certo;
 * caso contrário, escreve as mensagens de erro nos spans e retorna false.
 */

function validarFormulario({ nome, dataTermino, prioridade }) {
  let valido = true;
 
  erroNome.textContent = "";
  erroDataTermino.textContent = "";
  erroPrioridade.textContent = "";
 
  if (!nome || nome.trim() === "") {
    erroNome.textContent = "O nome não pode ser vazio.";
    valido = false;
  }
 
  if (!dataTermino) {
    erroDataTermino.textContent = "Informe a data e hora de término.";
    valido = false;
  }
 
  const prioridadeNum = Number(prioridade);
  if (!prioridade || prioridadeNum < 1 || prioridadeNum > 5) {
    erroPrioridade.textContent = "A prioridade deve estar entre 1 e 5.";
    valido = false;
  }
 
  return valido;
}

// ============================================
// Renderização
// ============================================

const LABELS_STATUS = {
  TODO: "A fazer",
  DOING: "Em andamento",
  DONE: "Concluída",
};
 
function formatarDataHora(valorDatetimeLocal) {
  const data = new Date(valorDatetimeLocal);
  if (Number.isNaN(data.getTime())) return "-";
  return data.toLocaleString("pt-BR", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}
 
function criarLinhaTarefa(tarefa) {
  const tr = document.createElement("tr");
 
  const tdNome = document.createElement("td");
  tdNome.textContent = tarefa.nome;
 
  const tdTermino = document.createElement("td");
  tdTermino.textContent = formatarDataHora(tarefa.dataTermino);
 
  const tdPrioridade = document.createElement("td");
  const seloPrioridade = document.createElement("span");
  seloPrioridade.className = "selo-prioridade";
  seloPrioridade.dataset.prioridade = tarefa.prioridade;
  seloPrioridade.textContent = tarefa.prioridade;
  tdPrioridade.appendChild(seloPrioridade);
 
  const tdCategoria = document.createElement("td");
  tdCategoria.textContent = tarefa.categoria || "-";
 
  const tdStatus = document.createElement("td");
  const seloStatus = document.createElement("span");
  seloStatus.className = "selo-status";
  seloStatus.dataset.status = tarefa.status;
  seloStatus.textContent = LABELS_STATUS[tarefa.status];
  tdStatus.appendChild(seloStatus);
 
  const tdAcoes = document.createElement("td");
  const divAcoes = document.createElement("div");
  divAcoes.className = "acoes-linha";
 
  const btnEditar = document.createElement("button");
  btnEditar.type = "button";
  btnEditar.textContent = "Editar";
  btnEditar.className = "btn-secundario";
  btnEditar.addEventListener("click", () => entrarModoEdicao(tarefa.id));
 
  const btnExcluir = document.createElement("button");
  btnExcluir.type = "button";
  btnExcluir.textContent = "Excluir";
  btnExcluir.className = "btn-secundario";
  btnExcluir.addEventListener("click", () => excluirTarefa(tarefa.id));
 
  divAcoes.appendChild(btnEditar);
  divAcoes.appendChild(btnExcluir);
  tdAcoes.appendChild(divAcoes);
 
  tr.append(tdNome, tdTermino, tdPrioridade, tdCategoria, tdStatus, tdAcoes);
  return tr;
}

/**
 * Redesenha a tabela inteira a partir do array,
 * aplicando o filtro de status atual.
 */
function renderizarTarefas() {
  tbody.innerHTML = "";
 
  const tarefasFiltradas =
    filtroAtual === "TODOS"
      ? tarefas
      : tarefas.filter((t) => t.status === filtroAtual);
 
  if (tarefasFiltradas.length === 0) {
    listaVazia.hidden = false;
  } else {
    listaVazia.hidden = true;
    tarefasFiltradas.forEach((tarefa) => {
      tbody.appendChild(criarLinhaTarefa(tarefa));
    });
  }
}

// ============================================
// Ações do formulário (criar / editar / cancelar)
// ============================================
 
function limparFormulario() {
  form.reset();
  inputTaskId.value = "";
  idEmEdicao = null;
  formTitulo.textContent = "Nova tarefa";
  btnCancelar.hidden = true;
  erroNome.textContent = "";
  erroDataTermino.textContent = "";
  erroPrioridade.textContent = "";
}
 
function entrarModoEdicao(id) {
  const tarefa = tarefas.find((t) => t.id === id);
  if (!tarefa) return;
 
  idEmEdicao = id;
  inputTaskId.value = tarefa.id;
  inputNome.value = tarefa.nome;
  inputDataTermino.value = tarefa.dataTermino;
  selectPrioridade.value = tarefa.prioridade;
  inputCategoria.value = tarefa.categoria || "";
  selectStatus.value = tarefa.status;
 
  formTitulo.textContent = "Editar tarefa";
  btnCancelar.hidden = false;
 
  form.scrollIntoView({ behavior: "smooth", block: "start" });
}
 
function excluirTarefa(id) {
  tarefas = tarefas.filter((t) => t.id !== id);
 
  // Se a tarefa excluída era a que estava em edição, volta pro modo criação
  if (idEmEdicao === id) {
    limparFormulario();
  }
 
  renderizarTarefas();
}
 
function tratarEnvioFormulario(evento) {
  evento.preventDefault();
 
  const dadosFormulario = {
    nome: inputNome.value,
    dataTermino: inputDataTermino.value,
    prioridade: selectPrioridade.value,
    categoria: inputCategoria.value,
    status: selectStatus.value,
  };
 
  if (!validarFormulario(dadosFormulario)) return;
 
  if (idEmEdicao === null) {
    // Modo criação
    const novaTarefa = {
      id: proximoId++,
      nome: dadosFormulario.nome.trim(),
      dataTermino: dadosFormulario.dataTermino,
      prioridade: Number(dadosFormulario.prioridade),
      categoria: dadosFormulario.categoria.trim(),
      status: dadosFormulario.status,
    };
    inserirTarefaOrdenada(novaTarefa);
  } else {
    // Modo edição: atualiza os dados e reordena por prioridade
    const tarefaExistente = tarefas.find((t) => t.id === idEmEdicao);
    if (tarefaExistente) {
      tarefas = tarefas.filter((t) => t.id !== idEmEdicao);
      tarefaExistente.nome = dadosFormulario.nome.trim();
      tarefaExistente.dataTermino = dadosFormulario.dataTermino;
      tarefaExistente.prioridade = Number(dadosFormulario.prioridade);
      tarefaExistente.categoria = dadosFormulario.categoria.trim();
      tarefaExistente.status = dadosFormulario.status;
      inserirTarefaOrdenada(tarefaExistente);
    }
  }
 
  limparFormulario();
  renderizarTarefas();
}
 
// ============================================
// Eventos
// ============================================
 
form.addEventListener("submit", tratarEnvioFormulario);
btnCancelar.addEventListener("click", limparFormulario);
 
filtroStatus.addEventListener("change", (evento) => {
  filtroAtual = evento.target.value;
  renderizarTarefas();
});
 
// Primeira renderização (lista vazia no início)
renderizarTarefas();