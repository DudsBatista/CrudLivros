package com.template.model.dao;

import com.template.model.dto.LivroDTO;

import java.util.ArrayList;

/**
 * Interface que define as operações de persistência dos livros.
 */
public interface ILivroDAO {

    // Cadastra um novo livro.
    void cadastrarLivro(LivroDTO livro);

    // Retorna todos os livros cadastrados.
    ArrayList<LivroDTO> listarLivros();

    // Atualiza os dados de um livro existente.
    void atualizarLivro(LivroDTO livro);

    // Exclui um livro pelo seu ID.
    void deletarLivro(int id);

    // Busca um livro específico pelo seu ID.
    LivroDTO buscarPorId(int id);
}