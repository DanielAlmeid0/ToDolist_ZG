package ui;

import repository.TaskRepository;
import model.Status;
import model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private TaskRepository repository;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(TaskRepository repository) {
        this.repository = repository;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            exibirOpcoes();

            String opcao = scanner.nextLine().trim();

            try {
                switch (opcao) {
                    case "1":
                        criarTarefa();
                        break;
                    case "2":
                        listarTarefas();
                        break;
                    case "3":
                        removerTarefa();
                        break;
                    case "4":
                        listarPorCategoria();
                        break;
                    case "5":
                        listarPorPrioridade();
                        break;
                    case "6":
                        listarPorStatus();
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("Até logo!");
                        break;
                    default:
                        System.out.println(">> Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Você digitou um valor não numérico.");
            } catch (IllegalArgumentException e) {
                System.out.println(">> Erro: " + e.getMessage());
            }
        }
    }

    public void exibirOpcoes() {
        System.out.println();
        System.out.println("---------- MENU ----------");
        System.out.println("1 - Criar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Remover tarefas");
        System.out.println("4 - Listar por categoria");
        System.out.println("5 - Listar por prioridade");
        System.out.println("6 - Listar por status");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void criarTarefa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de término (yyyy-MM-dd, ex: 2026-09-01): ");
        LocalDate dataTermino = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Prioridade (1 a 5): ");
        int prioridade = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Status (TODO, DOING ou DONE): ");
        Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());

        Task criada = repository.criar(new Task(0, nome, dataTermino, prioridade, categoria, status ));
        System.out.println(">> Tarefa criada: " + criada);
    }

    private void listarTarefas() {
        System.out.println("\n-- Tarefas --");
        if (repository.listarTodas().isEmpty()) {
            System.out.println("(nenhuma tarefa cadastrada)");
            return;
        }
        for (Task t : repository.listarTodas()) {
            System.out.println(t);
        }
    }

    private void removerTarefa() {
        System.out.println("Id da tarefa a remover: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        boolean removida = repository.removeerPorId(id);
        if (removida) {
            System.out.println(">> Tarefa removida.");
        } else {
            System.out.println(">> Nenhuma tarefa encontrada com esse id.");
        }
    }

    private void listarPorCategoria() {
        System.out.println("Categoria: ");
        String categoria = scanner.nextLine();
        List<Task> resultado = repository.listarPorCategoria(categoria);
        imprimirLista(resultado);
    }

    private void listarPorPrioridade() {
        System.out.print("Prioridade (1 a 5): ");
        int prioridade = Integer.parseInt(scanner.nextLine().trim());
        List<Task> resultado = repository.listarPorPrioridade(prioridade);
        imprimirLista(resultado);
    }

    private void listarPorStatus() {
        System.out.printf("Status (TODO, DOING ou DONE): ");
        Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());
        List<Task> resultado = repository.listarPorStatus(status);
        imprimirLista(resultado);
    }
    private void imprimirLista(List<Task> tarefas) {
        System.out.println();
        if (tarefas.isEmpty()) {
            System.out.println("(nenhuma tarefa encontrada)");
            return;
        }
        for (Task t : tarefas) {
            System.out.println(t);
        }
    }
}
