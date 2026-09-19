Backend em Java para uma aplicação TODO List, desenvolvido como parte do
desafio (K1-T3) do Acelera ZG. O frontend será implementado futuramente
na trilha de JavaScript.

## Tecnologias utilizadas

- **Java 21** (JDK)
- **Gradle** como ferramenta de build (sem uso de frameworks como Spring,
  Micronaut ou Grails, conforme exigido pelo desafio)
- Persistência simples em arquivo `.csv`, usando apenas a biblioteca
  padrão do Java (`java.nio.file`), sem bibliotecas externas de CSV

## Como executar

Pré-requisito: JDK 17 ou superior instalado.

```bash
./gradlew run
```

(no Windows, use `gradlew.bat run`)

O programa abre um menu interativo no terminal. Os dados são salvos
automaticamente em `tasks.csv` a cada alteração, então nada se perde ao
fechar o programa.

## Estrutura do projeto

- `model` — entidades `Task` e o enum `Status` (TODO, DOING, DONE)
- `repository` — `TaskRepository`, responsável por guardar as tarefas em
  memória (numa lista sempre ordenada por prioridade) e delegar a
  persistência ao `TaskFileStorage`
- `persistence` — `TaskFileStorage`, leitura/escrita do arquivo `tasks.csv`
- `ui` — `ConsoleMenu`, o menu de terminal que interage com o usuário
