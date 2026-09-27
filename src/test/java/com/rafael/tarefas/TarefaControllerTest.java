package com.rafael.tarefas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TarefaControllerTest {

    @Autowired
    private MockMvc mvc;

    private void criarTarefa(String titulo) throws Exception {
        mvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"" + titulo + "\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void healthCheckRetornaUp() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void criaUmaTarefa() throws Exception {
        mvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Estudar CI/CD\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Estudar CI/CD"))
                .andExpect(jsonPath("$.concluida").value(false));
    }

    @Test
    void criarSemTituloRetorna400() throws Exception {
        mvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listaAsTarefasCriadas() throws Exception {
        criarTarefa("A");
        criarTarefa("B");
        mvc.perform(get("/api/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void concluiUmaTarefa() throws Exception {
        criarTarefa("A");
        mvc.perform(patch("/api/tarefas/1/concluir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concluida").value(true));
    }

    @Test
    void concluirTarefaInexistenteRetorna404() throws Exception {
        mvc.perform(patch("/api/tarefas/99/concluir"))
                .andExpect(status().isNotFound());
    }
}
