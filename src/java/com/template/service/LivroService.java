package com.template.service;

import com.template.model.dao.LivroDAO;
import com.template.model.dto.LivroDTO;
import com.template.util.DialogUtil;

import java.util.ArrayList;

/*
 * Service responsável pelas operações relacionadas aos livros.
 *
 * Ele faz a comunicação entre o Controller e o DAO.
 */
public class LivroService
        implements ILivroService {

    // DAO responsável pelo acesso ao banco de dados.
    private final LivroDAO livroDAO =
            new LivroDAO();

    @Override
    public ArrayList<LivroDTO> listarLivros() {

        try {

            // Solicita ao DAO a lista de livros.
            return livroDAO.listarLivros();

        } catch (Exception e) {

            // Mostra uma mensagem amigável ao usuário.
            DialogUtil.mostrarErro(
                    "Erro ao carregar os livros."
            );

            return new ArrayList<>();
        }
    }

    @Override
    public void cadastrarLivro(
            String titulo,
            String autor,
            String anoTexto
    ) {

        // Cria um objeto para armazenar os dados do livro.
        LivroDTO livro =
                new LivroDTO();

        livro.setTitulo(
                titulo.trim()
        );

        livro.setAutor(
                autor.trim()
        );

        livro.setAno_publicacao(
                Integer.parseInt(
                        anoTexto.trim()
                )
        );

        try {

            // Envia o livro para o DAO cadastrar no banco.
            livroDAO.cadastrarLivro(
                    livro
            );

            DialogUtil.mostrarSucesso(
                    "Livro cadastrado com sucesso!"
            );

        } catch (Exception e) {

            DialogUtil.mostrarErro(
                    "Erro ao cadastrar o livro."
            );
        }
    }

    @Override
    public LivroDTO buscarPorId(
            String idTexto
    ) {

        // Verifica se o ID foi informado.
        if (idTexto == null
                || idTexto.isBlank()) {

            DialogUtil.mostrarErro(
                    "Digite um ID para buscar."
            );

            return null;
        }

        int id;

        try {

            // Converte o ID de String para inteiro.
            id = Integer.parseInt(
                    idTexto
            );

        } catch (NumberFormatException e) {

            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );

            return null;
        }

        try {

            // Procura o livro no banco.
            LivroDTO livro =
                    livroDAO.buscarPorId(id);

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

    @Override
    public void atualizarLivro(
            String idTexto,
            String titulo,
            String autor,
            String anoTexto
    ) {

        // Verifica se o ID foi informado.
        if (idTexto == null
                || idTexto.isBlank()) {

            DialogUtil.mostrarErro(
                    "Selecione ou informe o ID do livro."
            );

            return;
        }

        int id;

        try {

            // Converte o ID para inteiro.
            id = Integer.parseInt(
                    idTexto
            );

        } catch (NumberFormatException e) {

            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );

            return;
        }

        // Cria o objeto com os novos dados.
        LivroDTO livro =
                new LivroDTO();

        livro.setId(id);

        livro.setTitulo(
                titulo.trim()
        );

        livro.setAutor(
                autor.trim()
        );

        livro.setAno_publicacao(
                Integer.parseInt(
                        anoTexto.trim()
                )
        );

        try {

            // Envia os dados atualizados ao DAO.
            livroDAO.atualizarLivro(
                    livro
            );

            DialogUtil.mostrarSucesso(
                    "Dados atualizados com sucesso!"
            );

        } catch (Exception e) {

            DialogUtil.mostrarErro(
                    "Erro ao atualizar o livro."
            );
        }
    }

    @Override
    public void deletarLivro(
            String idTexto
    ) {

        // Verifica se o ID foi informado.
        if (idTexto == null
                || idTexto.isBlank()) {

            DialogUtil.mostrarErro(
                    "Digite ou selecione o ID para apagar."
            );

            return;
        }

        int id;

        try {

            // Converte o ID para inteiro.
            id = Integer.parseInt(
                    idTexto
            );

        } catch (NumberFormatException e) {

            DialogUtil.mostrarErro(
                    "O ID deve conter apenas números."
            );

            return;
        }

        // Pergunta ao usuário antes de excluir.
        boolean confirmou =
                DialogUtil.confirmar(
                        "Excluir livro",
                        "Tem certeza que deseja excluir este livro?"
                );

        if (!confirmou) {
            return;
        }

        try {

            // Solicita ao DAO a exclusão.
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