package persistence;

import model.Status;
import model.Task;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskFileStorage {
    private final Path arquivo;

    public TaskFileStorage(Path arquivo) {
        this.arquivo = arquivo;
    }

    public List<Task> carregarTodas() {
        List<Task> tarefas = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return tarefas;
        }

        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                if (linha.isBlank()) {
                    continue;
                }
                tarefas.add(parseLinha(linha));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao ler o arquivo de tarefa.", e);
        }
        return tarefas;
    }

    public void salvarTodas(List<Task> tarefas) {
        try {
            List<String> linhas = new ArrayList<>();
            for (Task t : tarefas) {
                linhas.add(formatarLinha(t));
            }
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao salvar o arquivo de tarefas.", e);
        }
    }

    private String formatarLinha(Task t) {
        return t.getId() + ";"
                + t.getNome() + ";"
                + t.getDataTermino() + ";"
                + t.getPrioridade() + ";"
                + t.getCategoria() + ";"
                + t.getStatus().name();
    }

    private Task parseLinha(String linha) {
        String[] campos = linha.split(";");

        int id = Integer.parseInt(campos[0]);
        String nome = campos[1];
        LocalDate dataTermino = LocalDate.parse(campos[2]);
        int prioridade = Integer.parseInt(campos[3]);
        String categoria = campos[4];
        Status status = Status.valueOf(campos[5]);

        return new Task(id, nome, dataTermino, prioridade, categoria, status);
    }
}
