package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);

    // Busca autores que possuem livros cadastrados
    @Query("SELECT DISTINCT a FROM Autor a WHERE a.livros IS NOT EMPTY ORDER BY a.nome")
    List<Autor> findAutoresComLivros();

    @Query("SELECT a FROM Autor a WHERE a.anoDeNascimento <= :ano AND (a.anoDeMorte IS NULL OR a.anoDeMorte >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
}
