package com.alura.literalura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Integer numeroDownloads;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = traduzirIdiomas(dadosLivro.idiomas().getFirst());
        this.numeroDownloads = dadosLivro.numeroDownloads();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idioma;
    }

    public void setIdiomas(String idiomas) {
        this.idioma = idiomas;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return """
           =====================Livro=====================
           Título: %s
           Autor: %s
           Idioma: %s
           Número de Downloads: %d
           ===============================================
           """.formatted(titulo, autor.getNome(), idioma, numeroDownloads);
    }


    private String traduzirIdiomas(String idioma) {
        return switch (idioma) {
                    case "en" -> "Inglês";
                    case "pt" -> "Português";
                    case "fr" -> "Francês";
                    case "es" -> "Espanhol";
                    default -> "Outro";
                };
    }

    private String converterParaString(List<String> lista) {
        return String.join(", ", lista);
    }

}
