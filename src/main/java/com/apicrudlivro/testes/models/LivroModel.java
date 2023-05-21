package com.apicrudlivro.testes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String titulo;
    @Column(length = 500, nullable = false)
    @NotBlank
    private String resumoLivro;
    private String sumario;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false)
    private int numeroPaginas;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private LocalDate dataPublicacao;

}
