# TODO List — Acelera ZG (K1-T3)

Backend em Java para uma aplicação TODO List, desenvolvido como parte do
desafio (K1-T3) do Acelera ZG. O frontend está sendo implementado na
trilha de JavaScript, espelhando o modelo e as regras de negócio do
backend.

## Tecnologias utilizadas

- **Java 21** (JDK)
- **Gradle** como ferramenta de build
- Persistência simples em arquivo `.csv`, usando apenas a biblioteca
  padrão do Java (`java.nio.file`), sem bibliotecas externas de CSV
- **HTML, CSS e JavaScript puros** para o frontend,
  reaproveitando o modelo de dados e as regras de validação do backend

## Como executar

### Backend

Pré-requisito: JDK 21 instalado.

```bash
./gradlew run
```

(no Windows, use `gradlew.bat run`)

O programa abre um menu interativo no terminal. Os dados são salvos
automaticamente em `tasks.csv` a cada alteração, então nada se perde ao
fechar o programa.

### Frontend

Pré-requisito: nenhum (não depende de instalação de pacotes).

Abra o arquivo `Frontend/index.html` diretamente no navegador (ou use uma
extensão tipo "Live Server" no VS Code). Por enquanto, o frontend
funciona com os dados em memória — ainda não há comunicação com o
backend Java.

## Estrutura do projeto

### Backend (`src/main/java`)

- `model` — entidades `Task` e o enum `Status` (TODO, DOING, DONE)
- `repository` — `TaskRepository`, responsável por guardar as tarefas em
  memória (numa lista sempre ordenada por prioridade) e delegar a
  persistência ao `TaskFileStorage`
- `persistence` — `TaskFileStorage`, leitura/escrita do arquivo `tasks.csv`
- `ui` — `ConsoleMenu`, o menu de terminal que interage com o usuário

### Frontend (`Frontend`)

- `index.html` — estrutura da página: formulário de criar/editar tarefa
  e lista de tarefas com filtro por status
- `style.css` — estilos, incluindo escala de cores por prioridade (1 a 5)
  e por status (TODO/DOING/DONE)
- `script.js` — lógica em JavaScript: array de tarefas em memória,
  inserção ordenada por prioridade, validação (nome obrigatório,
  prioridade entre 1 e 5) e renderização da lista

## Modelo de dados (`Task`)

| Campo | Tipo | Regras |
|---|---|---|
| `nome` | texto | não pode ser vazio |
| `dataTermino` | data + hora | — |
| `prioridade` | número | entre 1 e 5 |
| `categoria` | texto | — |
| `status` | `TODO` \| `DOING` \| `DONE` | — |

## Próximos passos

- [ ] Conectar o frontend ao backend (API ou outra forma de comunicação)
- [ ] Persistência do frontend (hoje os dados em JS somem ao recarregar a página)
- [ ] Alarmes com aviso antes do término da tarefa
