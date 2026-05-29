package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController
{
    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno;

    @FXML private TableView<LivroDTO> tabelaLivros;

    @FXML private TableColumn<LivroDTO, Integer> colId;
    @FXML private TableColumn<LivroDTO, String> colTitulo;
    @FXML private TableColumn<LivroDTO, String> colAutor;
    @FXML private TableColumn<LivroDTO, Integer> colAno;

    @FXML private Button btnCadastrar;
    @FXML private Button btnListar;
    @FXML private Button btnApagar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnLimpar;
    @FXML private Button btnBuscar;

    private LivroDAO livroDAO = new LivroDAO();

    @FXML
    private void initialize()
    {
        System.out.println("FXML carregado!");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));

        carregarLivros();
    }


    private void carregarLivros()
    {
        try {
            ObservableList<LivroDTO> lista =
                    FXCollections.observableArrayList(livroDAO.listarLivros());

            tabelaLivros.setItems(lista);

        } catch (Exception e) {
            System.out.println("Erro ao carregar livros: " + e.getMessage());
        }
    }


    @FXML
    private void btnCadastrarAction(ActionEvent event)
    {
        LivroDTO livro = new LivroDTO();

        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText()));

        livroDAO.cadastrarLivro(livro);

        carregarLivros();
        limparCampos();
    }


    @FXML
    private void btnListarAction(ActionEvent event)
    {
        carregarLivros();
    }


    @FXML
    private void btnBuscarAction(ActionEvent event)
    {
        if (!txtId.getText().isEmpty())
        {
            int id = Integer.parseInt(txtId.getText());

            LivroDTO livro = livroDAO.buscarPorId(id);

            if (livro != null)
            {
                txtTitulo.setText(livro.getTitulo());
                txtAutor.setText(livro.getAutor());
                txtAno.setText(String.valueOf(livro.getAno_publicacao()));
            }
            else
            {
                System.out.println("Livro não encontrado!");
            }
        }
    }


    @FXML
    private void btnAtualizarAction(ActionEvent event)
    {
        LivroDTO livro = new LivroDTO();

        livro.setId(Integer.parseInt(txtId.getText()));
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText()));

        livroDAO.atualizarLivro(livro);

        carregarLivros();
        limparCampos();
    }


    @FXML
    private void btnApagarAction(ActionEvent event)
    {
        if (!txtId.getText().isEmpty())
        {
            int id = Integer.parseInt(txtId.getText());

            livroDAO.deletarLivro(id);

            carregarLivros();
            limparCampos();
        }
    }


    @FXML
    private void btnLimparAction(ActionEvent event)
    {
        limparCampos();
    }


    private void limparCampos()
    {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtAno.clear();
    }

    @FXML
    private void selecionarLivro()
    {
        LivroDTO livro = tabelaLivros.getSelectionModel().getSelectedItem();

        if (livro != null)
        {
            txtId.setText(String.valueOf(livro.getId()));
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAno_publicacao()));
        }
    }
}