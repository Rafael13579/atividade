package com.rafael.tarefas.controller;

import com.rafael.tarefas.model.NovaTarefaRequest;
import com.rafael.tarefas.model.Tarefa;
import com.rafael.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TarefaController {

    private final TarefaService service;
    private final String ambiente;

    public TarefaController(TarefaService service, @Value("${app.ambiente}") String ambiente) {
        this.service = service;
        this.ambiente = ambiente;
    }

    @GetMapping("/")
    public Map<String, String> info() {
        return Map.of("app", "tarefas", "ambiente", ambiente);
    }

    @GetMapping("/api/tarefas")
    public List<Tarefa> listar() {
        return service.listar();
    }

    @PostMapping("/api/tarefas")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa criar(@Valid @RequestBody NovaTarefaRequest request) {
        return service.criar(request.titulo());
    }

    @PatchMapping("/api/tarefas/{id}/concluir")
    public ResponseEntity<Tarefa> concluir(@PathVariable Long id) {
        return service.concluir(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
