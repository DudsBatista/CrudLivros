package com.template.controller;

import com.template.model.dao.LivroDAO;
import com.template.model.dto.LivroDTO;
import com.template.validator.LivroValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno;
    @FXML private Label lblMensagem;
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


    private final LivroDAO livroDAO = new LivroDAO();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCamposNumericos();
        configurarSelecaoDaTabela();

        lblMensagem.setText("");
        carregarLivros();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo")
        );

        colAutor.setCellValueFactory(
                new PropertyValueFactory<>("autor")
        );

        colAno.setCellValueFactory(
                new PropertyValueFactory<>("ano_publicacao")
        );
    }

    private void configurarCamposNumericos() {
        txtAno.textProperty().addListener(
                (observable, valorAntigo, valorNovo) -> {
                    if (!valorNovo.matches("\\d*")
                            || valorNovo.length() > 4) {
                        txtAno.setText(valorAntigo);
                    }
                }
        );

        txtId.textProperty().addListener(
                (observable, valorAntigo, valorNovo) -> {
                    if (!valorNovo.matches("\\d*")) {
                        txtId.setText(valorAntigo);
                    }
                }
        );
    }

    private void configurarSelecaoDaTabela() {
        tabelaLivros.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, livroAntigo, livroSelecionado) -> {
                    if (livroSelecionado != null) {
                        preencherCampos(livroSelecionado);
                    }
                });
    }

    @FXML
    private void carregarLivros() {
        try {
            ObservableList<LivroDTO> lista =
                    FXCollections.observableArrayList(
                            livroDAO.listarLivros()
                    );

            tabelaLivros.setItems(lista);

        } catch (Exception e) {
            mostrarMensagemNaTela(
                    "Erro ao carregar os livros.",
                    true
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String anoTexto = txtAno.getText().trim();

        String erro = LivroValidator.validar(
                titulo,
                autor,
                anoTexto
        );

        if (erro != null) {
            mostrarMensagemNaTela(erro, true);
            return;
        }

        LivroDTO livro = new LivroDTO();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno_publicacao(
                Integer.parseInt(anoTexto)
        );

        try {
            livroDAO.cadastrarLivro(livro);

            mostrarMensagemNaTela(
                    "Livro cadastrado com sucesso!",
                    false
            );

            carregarLivros();
            limparCampos();

        } catch (Exception e) {
            mostrarMensagemNaTela(
                    "Erro ao cadastrar o livro.",
                    true
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void btnListarAction(ActionEvent event) {
        carregarLivros();
        mostrarMensagemNaTela(
                "Lista de livros atualizada!",
                false
        );
    }

    @FXML
    private void btnBuscarAction(ActionEvent event) {
        if (txtId.getText().isBlank()) {
            mostrarMensagemNaTela(
                    "Digite um ID para buscar.",
                    true
            );
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        try {
            LivroDTO livro = livroDAO.buscarPorId(id);

            if (livro == null) {
                mostrarMensagemNaTela(
                        "Livro não encontrado.",
                        true
                );
                return;
            }

            preencherCampos(livro);

            mostrarMensagemNaTela(
                    "Livro encontrado!",
                    false
            );

        } catch (Exception e) {
            mostrarMensagemNaTela(
                    "Erro ao buscar o livro.",
                    true
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (txtId.getText().isBlank()) {
            mostrarMensagemNaTela(
                    "Selecione ou informe o ID do livro.",
                    true
            );
            return;
        }

        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String anoTexto = txtAno.getText().trim();

        String erro = LivroValidator.validar(
                titulo,
                autor,
                anoTexto
        );

        if (erro != null) {
            mostrarMensagemNaTela(erro, true);
            return;
        }

        LivroDTO livro = new LivroDTO();
        livro.setId(Integer.parseInt(txtId.getText()));
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno_publicacao(
                Integer.parseInt(anoTexto)
        );

        try {
            livroDAO.atualizarLivro(livro);

            mostrarMensagemNaTela(
                    "Dados atualizados com sucesso!",
                    false
            );

            carregarLivros();
            limparCampos();

        } catch (Exception e) {
            mostrarMensagemNaTela(
                    "Erro ao atualizar o livro.",
                    true
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void btnApagarAction(ActionEvent event) {
        if (txtId.getText().isBlank()) {
            mostrarMensagemNaTela(
                    "Digite ou selecione o ID para apagar.",
                    true
            );
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        try {
            livroDAO.deletarLivro(id);

            mostrarMensagemNaTela(
                    "Livro excluído com sucesso!",
                    false
            );

            carregarLivros();
            limparCampos();

        } catch (Exception e) {
            mostrarMensagemNaTela(
                    "Erro ao excluir o livro.",
                    true
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        mostrarMensagemNaTela(
                "Campos limpos!",
                false
        );
    }

    @FXML
    private void selecionarLivro() {
        LivroDTO livro = tabelaLivros
                .getSelectionModel()
                .getSelectedItem();

        if (livro != null) {
            preencherCampos(livro);
        }
    }

    private void preencherCampos(LivroDTO livro) {
        txtId.setText(String.valueOf(livro.getId()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtAno.setText(
                String.valueOf(livro.getAno_publicacao())
        );
        lblMensagem.setText("");
    }

    private void limparCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtAno.clear();
        tabelaLivros.getSelectionModel().clearSelection();
    }

    private void mostrarMensagemNaTela(
            String texto,
            boolean ehErro
    ) {
        lblMensagem.setText(texto);

        if (ehErro) {
            lblMensagem.setStyle(
                    "-fx-text-fill: #cc0000; "
                            + "-fx-font-weight: bold;"
            );
        } else {
            lblMensagem.setStyle(
                    "-fx-text-fill: #008000; "
                            + "-fx-font-weight: bold;"
            );
        }
    }
}
