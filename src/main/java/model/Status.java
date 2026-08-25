package main.java.model;

public enum Status {
    TODO("A Fazer"),
    DOING("Em Andamento"),
    DONE("Concluida");

    private final String descricao;

    private Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
