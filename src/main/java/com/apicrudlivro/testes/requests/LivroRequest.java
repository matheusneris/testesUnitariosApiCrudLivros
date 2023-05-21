package com.apicrudlivro.testes.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LivroRequest {

    @NotBlank
    private String titulo;
    @NotBlank
    private String resumoLivro;
    private String sumario;
    private BigDecimal preco;
    private int numeroPaginas;
    private String isbn;
    private String dataPublicacao;

}
