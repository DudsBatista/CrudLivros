package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.LivroDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * DAO responsável pelo acesso ao banco de dados.
 *
 * Toda operação SQL relacionada aos livros fica nesta classe.
 */
public class LivroDAO implements ILivroDAO {

    // Logger utilizado para registrar erros do banco de dados.
    private static final Logger logger =
            Logger.getLogger(LivroDAO.class.getName());

    // Cadastra um novo livro no banco de dados.
    @Override
    public void cadastrarLivro(LivroDTO livro) {

        // Comando SQL para inserir um novo livro.
        String sql = "INSERT INTO livros "
                + "(titulo, autor, ano_publicacao) "
                + "VALUES (?, ?, ?)";

        try (
                Connection con = new Conexao().conectabBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            // Define os valores dos parâmetros da consulta.
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getAno_publicacao());

            // Executa o comando de inserção.
            ps.executeUpdate();

        } catch (SQLException e) {

            // Registra o erro no log.
            logger.log(
                    Level.SEVERE,
                    "Erro ao cadastrar livro",
                    e
            );

            // Informa ao Service que ocorreu um erro.
            throw new RuntimeException(
                    "Erro ao cadastrar livro.",
                    e
            );
        }
    }

    // Retorna todos os livros cadastrados no banco.
    @Override
    public ArrayList<LivroDTO> listarLivros() {

        ArrayList<LivroDTO> lista = new ArrayList<>();

        // Comando SQL para buscar todos os livros.
        String sql = "SELECT * FROM livros";

        try (
                Connection con = new Conexao().conectabBD();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            // Percorre todos os registros encontrados.
            while (rs.next()) {

                // Cria um objeto para armazenar os dados do livro.
                LivroDTO livro = new LivroDTO();

                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAno_publicacao(
                        rs.getInt("ano_publicacao")
                );

                // Adiciona o livro à lista.
                lista.add(livro);
            }

        } catch (SQLException e) {

            // Registra o erro no log.
            logger.log(
                    Level.SEVERE,
                    "Erro ao listar livros",
                    e
            );

            throw new RuntimeException(
                    "Erro ao listar livros.",
                    e
            );
        }

        return lista;
    }

    // Atualiza os dados de um livro existente.
    @Override
    public void atualizarLivro(LivroDTO livro) {

        // Comando SQL para atualizar um livro pelo ID.
        String sql = "UPDATE livros "
                + "SET titulo = ?, "
                + "autor = ?, "
                + "ano_publicacao = ? "
                + "WHERE id = ?";

        try (
                Connection con = new Conexao().conectabBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            // Define os novos valores do livro.
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getAno_publicacao());
            ps.setInt(4, livro.getId());

            // Executa a atualização.
            ps.executeUpdate();

        } catch (SQLException e) {

            // Registra o erro no log.
            logger.log(
                    Level.SEVERE,
                    "Erro ao atualizar livro",
                    e
            );

            throw new RuntimeException(
                    "Erro ao atualizar livro.",
                    e
            );
        }
    }

    // Exclui um livro do banco pelo seu ID.
    @Override
    public void deletarLivro(int id) {

        // Comando SQL para excluir o livro.
        String sql = "DELETE FROM livros "
                + "WHERE id = ?";

        try (
                Connection con = new Conexao().conectabBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            // Define o ID do livro que será excluído.
            ps.setInt(1, id);

            // Executa a exclusão.
            ps.executeUpdate();

        } catch (SQLException e) {

            // Registra o erro no log.
            logger.log(
                    Level.SEVERE,
                    "Erro ao deletar livro",
                    e
            );

            throw new RuntimeException(
                    "Erro ao deletar livro.",
                    e
            );
        }
    }

    // Busca um livro específico pelo seu ID.
    @Override
    public LivroDTO buscarPorId(int id) {

        // Comando SQL para buscar um livro pelo ID.
        String sql = "SELECT * FROM livros "
                + "WHERE id = ?";

        LivroDTO livro = null;

        try (
                Connection con = new Conexao().conectabBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            // Define o ID que será pesquisado.
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                // Verifica se foi encontrado algum livro.
                if (rs.next()) {

                    // Cria o objeto e preenche seus dados.
                    livro = new LivroDTO();

                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setAno_publicacao(
                            rs.getInt("ano_publicacao")
                    );
                }
            }

        } catch (SQLException e) {

            // Registra o erro no log.
            logger.log(
                    Level.SEVERE,
                    "Erro ao buscar livro por ID",
                    e
            );

            throw new RuntimeException(
                    "Erro ao buscar livro por ID.",
                    e
            );
        }

        // Retorna o livro encontrado ou null caso não exista.
        return livro;
    }
}