package repository;

import model.Status;
import model.Task;
import persistence.TaskFileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {


    private final List<Task> tasks =  new ArrayList<>();
    private int proximoId = 1;
    private final TaskFileStorage storage;

    public TaskRepository(TaskFileStorage storage) {
        this.storage = storage;

        List<Task> carregadas = storage.carregarTodas();
        tasks.addAll(carregadas);

        int maiorId = 0;
        for (Task t : carregadas) {
            if (t.getId() > maiorId) {
                maiorId = t.getId();
            }
        }
        this.proximoId = maiorId + 1;
    }

    public Task criar(Task task) {
        Task salva = new Task(
                proximoId,
                task.getNome(),
                task.getDataTermino(),
                task.getPrioridade(),
                task.getCategoria(),
                task.getStatus());


        proximoId++;

        int posicao = encontrarPosicaoIdeal(salva.getPrioridade());
        tasks.add(posicao, salva);

        storage.salvarTodas(tasks);
        return salva;
    }

    private int encontrarPosicaoIdeal(int prioridade) {
        int indice = 0;

        while (indice < tasks.size() && tasks.get(indice).getPrioridade() <= prioridade) {
            indice++;
        }
        return indice;
    }

    public List<Task> listarTodas() {
        return new ArrayList<>(tasks);
    }

    public boolean removeerPorId(int id) {
        boolean removeu = tasks.removeIf(t -> t.getId() == id);
        if (removeu) {
            storage.salvarTodas(tasks);
        }
        return removeu;
    }

    public List<Task> listarPorCategoria(String categoria) {
        return tasks.stream()
                .filter(t -> t.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Task> listarPorPrioridade(int prioridade) {
        return tasks.stream().filter(t -> t.getPrioridade() == prioridade).collect(Collectors.toList());
    }

    public List<Task> listarPorStatus(Status status) {
        return tasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }


}
