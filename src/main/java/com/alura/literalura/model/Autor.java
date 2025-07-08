package com.alura.literalura.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeMorte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosLivro dadosLivro) {
        this.nome = dadosLivro.autores().getFirst().nome();
        this.anoDeNascimento = dadosLivro.autores().getFirst().anoNascimento();
        this.anoDeMorte = dadosLivro.autores().getFirst().anoFalecimento();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeMorte() {
        return anoDeMorte;
    }

    public void setAnoDeMorte(Integer anoDeMorte) {
        this.anoDeMorte = anoDeMorte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void adicionarLivro(Livro livro) {
        livro.setAutor(this);
        this.livros.add(livro);
    }

    @Override
    public String toString() {
        return """
                ============AUTOR===============
                Nome: %s
                Ano de Nascimento: %s
                Ano de Morte: %s
                ================================
                """.formatted(nome, anoDeNascimento, anoDeMorte);
    }
}
