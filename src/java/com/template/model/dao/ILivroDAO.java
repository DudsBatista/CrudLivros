package com.template.model.dao;

import com.template.model.dto.LivroDTO;

import java.util.ArrayList;

/** Contrato de persistência dos livros. */
public interface ILivroDAO {

    void cadastrarLivro(LivroDTO livro);

    ArrayList<LivroDTO> listarLivros();

    void atualizarLivro(LivroDTO livro);

    void deletarLivro(int id);

    LivroDTO buscarPorId(int id);
}
