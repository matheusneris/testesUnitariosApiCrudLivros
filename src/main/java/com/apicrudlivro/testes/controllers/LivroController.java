package com.apicrudlivro.testes.controllers;

import com.apicrudlivro.testes.models.LivroModel;
import com.apicrudlivro.testes.requests.LivroRequest;
import com.apicrudlivro.testes.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/livros")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping()
    public ResponseEntity<Object> salvarLivro(@RequestBody @Valid LivroRequest livroRequest) {
        Optional<LivroModel> livroModelOptional = livroService.buscarLivroPorIsbn(livroRequest.getIsbn());
        if(livroModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Já existe um livro cadastrado com esse Isbn");
        }
        LivroModel livroModel = transformarDataStringToLocalDate(livroRequest);

        try {
            validadorRequisitosLivroParaSalvar(livroModel);
            return ResponseEntity.status(HttpStatus.OK).body(livroService.salvarLivro(livroModel));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<LivroModel>> listarLivros(){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.listarLivros());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Object> buscarLivroPeloIsbn(@PathVariable String isbn){
        Optional<LivroModel> livroModelOptional = livroService.buscarLivroPorIsbn(isbn);
        if(livroModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe livro com este Isbn");
        }

        return ResponseEntity.status(HttpStatus.OK).body(livroModelOptional);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> editarLivroPeloIsbn(@RequestBody @Valid LivroRequest livroRequest, @PathVariable String isbn){
        Optional<LivroModel> livroModelOptional = livroService.buscarLivroPorIsbn(isbn);
        if(livroModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe livro com este Isbn");
        }
        LivroModel livroModel = transformarDataStringToLocalDate(livroRequest);
        return ResponseEntity.status(HttpStatus.OK).body(livroService.editarLivro(livroModel, isbn));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deletarLivroPorIsbn(@PathVariable String isbn){
        Optional<LivroModel> livroModelOptional = livroService.buscarLivroPorIsbn(isbn);
        if(livroModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe livro com este Isbn");
        }
        livroService.deletarLivroPorIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso");
    }

    public LivroModel transformarDataStringToLocalDate(LivroRequest livroRequest){
        LivroModel livroModel = new LivroModel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        livroModel.setTitulo(livroRequest.getTitulo());
        livroModel.setResumoLivro(livroRequest.getResumoLivro());
        livroModel.setSumario(livroRequest.getSumario());
        livroModel.setPreco(livroRequest.getPreco());
        livroModel.setNumeroPaginas(livroRequest.getNumeroPaginas());
        livroModel.setIsbn(livroRequest.getIsbn());
        livroModel.setDataPublicacao(LocalDate.parse(livroRequest.getDataPublicacao(), formatter));

        return livroModel;
    }

    public void validadorRequisitosLivroParaSalvar(LivroModel livroModel) throws Exception {
        if(livroModel.getPreco() == null){
            throw new Exception("Preco e obrigatorio");
        } else if (livroModel.getPreco().compareTo(BigDecimal.valueOf(20))< 0) {
            throw new Exception("Preco nao pode ser menor que 20");
        }

        if(livroModel.getNumeroPaginas() < 100){
            throw new Exception("O minimo de paginas e 100");
        }

        if(livroModel.getDataPublicacao().isBefore(LocalDate.now().plusDays(1))){
            throw new Exception("Data de publicacao deve ser no futuro");
        }
    }

}