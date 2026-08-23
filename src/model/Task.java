//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String nome;
    private LocalDate dataTermino;
    private int prioridade;
    private String categoria;
    private Status status;

    public Task(int id, String nome, LocalDate dataTermino, int prioridade, String categoria, Status status) {
        this.id = id;
        this.setNome(nome);
        this.dataTermino = dataTermino;
        this.setPrioridade(prioridade);
        this.categoria = categoria;
        this.status = status;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }

    public void setPrioridade(int prioridade) {
        if (prioridade >= 1 && prioridade <= 5) {
            this.prioridade = prioridade;
        } else {
            throw new IllegalArgumentException("Prioridade deve estar entre 1 e 5.");
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataTermino() {
        return this.dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        int var10000 = this.id;
        return "#" + var10000 + " [P" + this.prioridade + "] " + this.nome + " (" + String.valueOf(this.status) + ") - " + this.categoria;
    }
}
