package com.rafael.tarefas.service;

import com.rafael.tarefas.model.Tarefa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TarefaService {

    private final Map<Long, Tarefa> tarefas = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public List<Tarefa> listar() {
        return tarefas.values().stream()
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .toList();
    }

    public Tarefa criar(String titulo) {
        Tarefa tarefa = new Tarefa(proximoId.getAndIncrement(), titulo.trim());
        tarefas.put(tarefa.getId(), tarefa);
        return tarefa;
    }

    public Optional<Tarefa> concluir(Long id) {
        Tarefa tarefa = tarefas.get(id);
        if (tarefa == null) {
            return Optional.empty();
        }
        tarefa.concluir();
        return Optional.of(tarefa);
    }
}
