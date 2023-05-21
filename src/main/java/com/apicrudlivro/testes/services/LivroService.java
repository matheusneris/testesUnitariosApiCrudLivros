package com.apicrudlivro.testes.services;

import com.apicrudlivro.testes.models.LivroModel;
import com.apicrudlivro.testes.repositories.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private LivroRepository livroRepository;

    public LivroService (LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    @Transactional
    public LivroModel salvarLivro(LivroModel livroModel){
        return livroRepository.save(livroModel);
    }

    public List<LivroModel> listarLivros(){
        return livroRepository.findAll();
    }

    public Optional<LivroModel> buscarLivroPorIsbn(String isbn){
        Optional<LivroModel> livroModelOptional = livroRepository.findByIsbn(isbn);
        if(livroModelOptional.isPresent()){
            return livroModelOptional;
        }
        livroModelOptional = Optional.empty();
        return livroModelOptional;
    }

    @Transactional
    public LivroModel editarLivro(LivroModel registroNovo, String isbn){
        LivroModel registroAntigo = livroRepository.findByIsbn(isbn).get();

        registroAntigo.setResumoLivro(registroNovo.getResumoLivro());
        registroAntigo.setPreco(registroNovo.getPreco());
        registroAntigo.setSumario(registroNovo.getSumario());
        registroAntigo.setTitulo(registroNovo.getTitulo());
        registroAntigo.setDataPublicacao(registroNovo.getDataPublicacao());
        registroAntigo.setNumeroPaginas(registroNovo.getNumeroPaginas());

        return livroRepository.save(registroAntigo);
    }

    @Transactional
    public void deletarLivroPorIsbn(String isbn){
        livroRepository.deleteByIsbn(isbn);
    }
}
