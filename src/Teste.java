import java.time.LocalDate;
import java.util.List;

import Repository.TaskRepository;
import model.Status;
import model.Task;

public class Teste {
    static void main(String[] args) {
        TaskRepository repository = new TaskRepository();

        repository.criar(new Task(0, "Estudar Java", LocalDate.of(2006, 9, 1), 2, "Estudos", Status.TODO ));
        repository.criar(new Task(0, "Fazer compras", LocalDate.of(2026, 8, 25), 1, "Casa", Status.TODO));
        repository.criar(new Task(0, "Ler um livro", LocalDate.of(2026, 10, 1), 4, "Lazer", Status.DOING));


        System.out.println("-- Todas as tarefas --");
        for (Task t : repository.listarTodas()) {
            System.out.println(t);
        }

        System.out.println("\n-- Removendo a tarefa #2 --");
        boolean removeu = repository.removeerPorId(2);
        System.out.println("Removeu? " + removeu);

        System.out.println("\n-- Lista depois da remoção--");
        for (Task t : repository.listarTodas()) {
            System.out.println(t);
        }

        System.out.println("\n-- Testando a copia --");
        List<Task> copia = repository.listarTodas();
        copia.add(new Task(0, "Tarefa fantasma", LocalDate.now(), 3, "Teste", Status.TODO));

        System.out.println("Tamanho da copia: " + copia.size());
        System.out.println("Tamanho real no repository: " + repository.listarTodas().size());
    }
}