package repository;

import model.Status;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {


    private final List<Task> tasks =  new ArrayList<>();

    private int proximoId = 1;

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
        return tasks.removeIf(t -> t.getId() == id);
    }

    public List<Task> listarPorCategoria(String categoria) {
        return tasks.stream().filter(t -> t.getCategoria() == categoria).collect(Collectors.toList());
    }

    public List<Task> listarPorPrioridade(int prioridade) {
        return tasks.stream().filter(t -> t.getPrioridade() == prioridade).collect(Collectors.toList());
    }

    public List<Task> listarPorStatus(Status status) {
        return tasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }


}
