package com.rafael.tarefas.model;

public class Tarefa {

    private final Long id;
    private final String titulo;
    private boolean concluida;

    public Tarefa(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.concluida = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void concluir() {
        this.concluida = true;
    }
}
