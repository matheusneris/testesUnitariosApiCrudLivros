package com.apicrudlivro.testes.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroRequest {

    @NotBlank
    private String titulo;
    @NotBlank
    @Size(max = 500)
    private String resumoLivro;
    private String sumario;
    private BigDecimal preco;
    private int numeroPaginas;
    @NotBlank
    private String isbn;
    @NotBlank
    private String dataPublicacao;

}
