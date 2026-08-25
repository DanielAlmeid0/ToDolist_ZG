package repository;

import model.Task;

import java.util.ArrayList;
import java.util.List;

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


        tasks.add(salva);
        proximoId++;
        return salva;
    }

    public List<Task> listarTodas() {
        return new ArrayList<>(tasks);
    }

    public boolean removeerPorId(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }
}
