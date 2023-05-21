package com.apicrudlivro.testes.repositories;

import com.apicrudlivro.testes.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {

    Optional<LivroModel> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);

}
