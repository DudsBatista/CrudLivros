package com.template.service;

import com.template.model.dao.LivroDAO;
import com.template.model.dto.LivroDTO;
import com.template.util.DialogUtil;
import com.template.validator.LivroValidator;

import java.util.ArrayList;


/**
 * Classe responsável pelas operações relacionadas aos livros.
 * O Service faz a ligação entre o Controller e o DAO.
 */
public class LivroService {

    // DAO responsável pelo acesso ao banco
    private final LivroDAO livroDAO = new LivroDAO();

    public ArrayList<LivroDTO> listarLivros() {
        try {
            return livroDAO.listarLivros();
        } catch (Exception e) {
            DialogUtil.mostrarErro(
                    "Erro ao carregar os livros."
            );
            return new ArrayList<>();
        }
    }
    public void cadastrarLivro(
            String titulo,
            String autor,
            String anoTexto
    ) {
        // Remove espaços desnecessários
        titulo = titulo.trim();
        autor = autor.trim();
        anoTexto = anoTexto.trim();

        // Valida os dados
        String erro = LivroValidator.validar(
                titulo,
                autor,
                anoTexto
        );
        // Se houver erro, mostra a mensagem
        if (erro != null) {

            DialogUtil.mostrarErro(erro);

            return;
        }

        // Cria o objeto LivroDTO
        LivroDTO livro = new LivroDTO();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno_publicacao(
                Integer.parseInt(anoTexto)
        );

        try {
            // Envia o livro para o DAO
            livroDAO.cadastrarLivro(livro);

            DialogUtil.mostrarSucesso(
                    "Livro cadastrado com sucesso!"
            );

        } catch (Exception e) {

            DialogUtil.mostrarErro(
                    "Erro ao cadastrar o livro."
            );
        }
    }

    public LivroDTO buscarPorId(String idTexto) {

        // Verifica se o ID foi preenchido
        if (idTexto == null || idTexto.isBlank()) {
            DialogUtil.mostrarErro(
                    "Digite um ID para buscar."
            );
            return null;
        }
        int id;
        // Converte o ID para inteiro
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );
            return null;
        }
        try {
            LivroDTO livro =
                    livroDAO.buscarPorId(id);
            // Verifica se encontrou
            if (livro == null) {
                DialogUtil.mostrarErro(
                        "Livro não encontrado."
                );
                return null;
            }
            DialogUtil.mostrarSucesso(
                    "Livro encontrado!"
            );
            return livro;
        } catch (Exception e) {
            DialogUtil.mostrarErro(
                    "Erro ao buscar o livro."
            );
            return null;
        }
    }
    public void atualizarLivro(
            String idTexto,
            String titulo,
            String autor,
            String anoTexto
    ) {

        // Verifica o ID
        if (idTexto == null || idTexto.isBlank()) {

            DialogUtil.mostrarErro(
                    "Selecione ou informe o ID do livro."
            );

            return;
        }
        // Limpa os espaços
        titulo = titulo.trim();
        autor = autor.trim();
        anoTexto = anoTexto.trim();

        // Valida os dados
        String erro = LivroValidator.validar(
                titulo,
                autor,
                anoTexto
        );
        if (erro != null) {
            DialogUtil.mostrarErro(erro);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );
            return;
        }
        // Cria o LivroDTO
        LivroDTO livro = new LivroDTO();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno_publicacao(
                Integer.parseInt(anoTexto)
        );
        try {
            livroDAO.atualizarLivro(livro);
            DialogUtil.mostrarSucesso(
                    "Dados atualizados com sucesso!"
            );
        } catch (Exception e) {
            DialogUtil.mostrarErro(
                    "Erro ao atualizar o livro."
            );
        }
    }


    /**
     * Exclui um livro pelo ID.
     */
    public void deletarLivro(String idTexto) {

        // Verifica se o ID foi preenchido
        if (idTexto == null || idTexto.isBlank()) {

            DialogUtil.mostrarErro(
                    "Digite ou selecione o ID para apagar."
            );
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idTexto); } catch (NumberFormatException e) {
            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );
            return;
        }
        // Confirma a exclusão
        boolean confirmou =
                DialogUtil.confirmar(
                        "Excluir livro",
                        "Tem certeza que deseja excluir este livro?"
                );
        if (!confirmou) {
            return;
        }
        try {
            livroDAO.deletarLivro(id);
            DialogUtil.mostrarSucesso(
                    "Livro excluído com sucesso!"
            );

        } catch (Exception e) {
            DialogUtil.mostrarErro(
                    "Erro ao excluir o livro."
            );
        }
    }
}