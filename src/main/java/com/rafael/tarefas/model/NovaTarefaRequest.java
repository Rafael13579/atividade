package com.rafael.tarefas.model;

import jakarta.validation.constraints.NotBlank;

public record NovaTarefaRequest(@NotBlank(message = "O campo titulo é obrigatório") String titulo) {
}
