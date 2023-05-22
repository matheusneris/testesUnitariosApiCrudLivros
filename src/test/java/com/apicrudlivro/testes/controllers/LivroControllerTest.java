package com.apicrudlivro.testes.controllers;

import com.apicrudlivro.testes.ConfigTest;
import com.apicrudlivro.testes.models.LivroModel;
import com.apicrudlivro.testes.requests.LivroRequest;
import com.apicrudlivro.testes.services.LivroService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
class LivroControllerTest {

    @InjectMocks
    private LivroController livroController;

    @Mock
    private LivroService livroService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    }

    @Test
    void deveSalvarLivroIntegracao() throws Exception{
        JSONObject livro = new JSONObject();
        livro.put("titulo", "pai rico pai pobre");
        livro.put("resumoLivro", "teste resumo");
        livro.put("sumario", "teste sumario");
        livro.put("preco", 21.90);
        livro.put("numeroPaginas", 180);
        livro.put("isbn", "idisbn123");
        livro.put("dataPublicacao", "23/02/2180");

        mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .content(String.valueOf(livro))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<LivroModel> livros = livroService.listarLivros();

        Assertions.assertEquals("pai rico pai pobre", livros.get(0).getTitulo());
    }


    @Test
    void deveSalvarLivroTest() {
        LivroRequest livroRequest = new LivroRequest("do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(21.90), 190, "isbn123",
                "05/04/2025");
        doReturn(Optional.of(livroRequest)).when(livroService).buscarLivroPorIsbn(any());
        livroController.salvarLivro(livroRequest);
    }

    @Test
    void listarLivros() {
    }

    @Test
    void buscarLivroPeloIsbn() {
    }

    @Test
    void editarLivroPeloIsbn() {
    }

    @Test
    void deletarLivroPorIsbn() {
    }

    @Test
    void deveCriarLivroModelTransformandoLocalDateDeString(){
        LivroRequest livroRequest = new LivroRequest("do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(21.90), 190, "isbn123",
                "05/04/2025");
        LivroModel livroModelEsperado = new LivroModel(null, "do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(21.90), 190, "isbn123",
                LocalDate.of(2025,04,05));
        LivroModel livroModel = livroController.transformarDataStringToLocalDate(livroRequest);
        assertEquals(livroModelEsperado, livroModel);
    }

    @Test
    void deveValidarPrecoNaoPodeSerMenorQue20() throws Exception {
        LivroModel livroModel = new LivroModel(null, "do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(19), 190, "isbn123",
                LocalDate.of(2025,04,05));
        Exception exception = assertThrows(Exception.class, () -> livroController.validadorRequisitosLivroParaSalvar(livroModel));
        assertEquals("Preco nao pode ser menor que 20", exception.getMessage());
    }

    @Test
    void deveValidarPrecoNaoPodeSerNull() throws Exception {
        LivroModel livroModel = new LivroModel(null, "do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                null , 190, "isbn123",
                LocalDate.of(2025,04,05));
        Exception exception = assertThrows(Exception.class, () -> livroController.validadorRequisitosLivroParaSalvar(livroModel));
        assertEquals("Preco e obrigatorio", exception.getMessage());
    }

    @Test
    void deveValidarPaginasNaoPodeSerMenorQue100() throws Exception {
        LivroModel livroModel = new LivroModel(null, "do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(22) , 99, "isbn123",
                LocalDate.of(2025,04,05));
        Exception exception = assertThrows(Exception.class, () -> livroController.validadorRequisitosLivroParaSalvar(livroModel));
        assertEquals("O minimo de paginas e 100", exception.getMessage());
    }

    @Test
    void deveValidarDataObrigatoriamenteFutura() throws Exception {
        LivroModel livroModel = new LivroModel(null, "do mil ao milhao",
                "sem cortar o cafezinho", "sumario teste",
                BigDecimal.valueOf(22) , 980, "isbn123",
                LocalDate.of(1995,04,05));
        Exception exception = assertThrows(Exception.class, () -> livroController.validadorRequisitosLivroParaSalvar(livroModel));
        assertEquals("Data de publicacao deve ser no futuro", exception.getMessage());
    }

}