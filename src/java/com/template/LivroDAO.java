package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LivroDAO {

    private static final Logger logger = Logger.getLogger(LivroDAO.class.getName());

    public void cadastrarLivro(LivroDTO livro) {
        String sql = "INSERT INTO livros (titulo, autor, ano_publicacao) VALUES (?, ?, ?)";

        try (Connection con = new Conexao().conectabBD();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getAno_publicacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar livro", e);
        }
    }

    public List<LivroDTO> listarLivros() {
        List<LivroDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection con = new Conexao().conectabBD();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LivroDTO livro = new LivroDTO();

                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAno_publicacao(rs.getInt("ano_publicacao"));

                lista.add(livro);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar livros", e);
        }

        return lista;
    }

    public void atualizarLivro(LivroDTO livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, ano_publicacao = ? WHERE id = ?";

        try (Connection con = new Conexao().conectabBD();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getAno_publicacao());
            ps.setInt(4, livro.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar livro", e);
        }
    }

    public void deletarLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection con = new Conexao().conectabBD();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar livro", e);
        }
    }

    public LivroDTO buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        LivroDTO livro = null;

        try (Connection con = new Conexao().conectabBD();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                livro = new LivroDTO();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAno_publicacao(rs.getInt("ano_publicacao"));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar livro por ID", e);
        }

        return livro;
    }
}