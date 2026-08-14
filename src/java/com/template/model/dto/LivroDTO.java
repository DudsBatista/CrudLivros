package com.template.model.dto;

/**
 * Representa os dados de um livro.
 */
public class LivroDTO {

    private int id;
    private String titulo;
    private String autor;
    private int ano_publicacao;

    public LivroDTO() {
    }

    public LivroDTO(
            int id,
            String titulo,
            String autor,
            int ano_publicacao
    ) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano_publicacao = ano_publicacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno_publicacao() {
        return ano_publicacao;
    }

    public void setAno_publicacao(int ano_publicacao) {
        this.ano_publicacao = ano_publicacao;
    }
}
