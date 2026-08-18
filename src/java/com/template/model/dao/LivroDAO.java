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


/**
 * Classe responsável pelo acesso ao banco de dados
 * relacionado aos livros.
 */
public class LivroDAO {

    private static final Logger logger =
            Logger.getLogger(
                    LivroDAO.class.getName()
            );


    /**
     * Cadastra um livro no banco.
     */
    public void cadastrarLivro(LivroDTO livro) {

        String sql =
                "INSERT INTO livros "
                        + "(titulo, autor, ano_publicacao) "
                        + "VALUES (?, ?, ?)";


        try (
                Connection con =
                        new Conexao().conectabBD();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    livro.getTitulo()
            );

            ps.setString(
                    2,
                    livro.getAutor()
            );

            ps.setInt(
                    3,
                    livro.getAno_publicacao()
            );

            ps.executeUpdate();


        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao cadastrar livro",
                    e
            );

            // Lança o erro para o Service tratar
            throw new RuntimeException(
                    "Erro ao cadastrar livro.",
                    e
            );
        }
    }


    /**
     * Lista todos os livros.
     */
    public ArrayList<LivroDTO> listarLivros() {

        ArrayList<LivroDTO> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM livros";


        try (
                Connection con =
                        new Conexao().conectabBD();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {


            while (rs.next()) {

                LivroDTO livro =
                        new LivroDTO();

                livro.setId(
                        rs.getInt("id")
                );

                livro.setTitulo(
                        rs.getString("titulo")
                );

                livro.setAutor(
                        rs.getString("autor")
                );

                livro.setAno_publicacao(
                        rs.getInt("ano_publicacao")
                );

                lista.add(livro);
            }


        } catch (SQLException e) {

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


    /**
     * Atualiza um livro.
     */
    public void atualizarLivro(LivroDTO livro) {

        String sql =
                "UPDATE livros "
                        + "SET titulo = ?, "
                        + "autor = ?, "
                        + "ano_publicacao = ? "
                        + "WHERE id = ?";


        try (
                Connection con =
                        new Conexao().conectabBD();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    livro.getTitulo()
            );

            ps.setString(
                    2,
                    livro.getAutor()
            );

            ps.setInt(
                    3,
                    livro.getAno_publicacao()
            );

            ps.setInt(
                    4,
                    livro.getId()
            );

            ps.executeUpdate();


        } catch (SQLException e) {

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


    /**
     * Deleta um livro pelo ID.
     */
    public void deletarLivro(int id) {

        String sql =
                "DELETE FROM livros WHERE id = ?";


        try (
                Connection con =
                        new Conexao().conectabBD();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();


        } catch (SQLException e) {

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


    /**
     * Busca um livro pelo ID.
     */
    public LivroDTO buscarPorId(int id) {

        String sql =
                "SELECT * FROM livros WHERE id = ?";

        LivroDTO livro = null;


        try (
                Connection con =
                        new Conexao().conectabBD();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                livro =
                        new LivroDTO();

                livro.setId(
                        rs.getInt("id")
                );

                livro.setTitulo(
                        rs.getString("titulo")
                );

                livro.setAutor(
                        rs.getString("autor")
                );

                livro.setAno_publicacao(
                        rs.getInt("ano_publicacao")
                );
            }


        } catch (SQLException e) {

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


        return livro;
    }
}