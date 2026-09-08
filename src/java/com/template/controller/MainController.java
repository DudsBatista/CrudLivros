package com.template.controller;

import com.template.model.dto.LivroDTO;
import com.template.service.ILivroService;
import com.template.util.TelaUtil;
import com.template.validator.ILivroValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno;

    @FXML private TableView<LivroDTO> tabelaLivros;
    @FXML private TableColumn<LivroDTO, Integer> colId;
    @FXML private TableColumn<LivroDTO, String> colTitulo;
    @FXML private TableColumn<LivroDTO, String> colAutor;
    @FXML private TableColumn<LivroDTO, Integer> colAno;

    private final ILivroService livroService;
    private final ILivroValidator livroValidator;

    // Recebe as dependências necessárias.
    public MainController(
            ILivroService livroService,
            ILivroValidator livroValidator
    ) {
        this.livroService = livroService;
        this.livroValidator = livroValidator;
    }

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCamposNumericos();
        configurarSelecaoDaTabela();
        carregarLivros();
    }

    // Configura as colunas da tabela.
    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));
    }

    // Permite somente números nos campos de ID e ano.
    private void configurarCamposNumericos() {
        txtAno.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*") || novo.length() > 4) {
                txtAno.setText(antigo);
            }
        });

        txtId.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                txtId.setText(antigo);
            }
        });
    }

    // Preenche os campos ao selecionar um livro.
    private void configurarSelecaoDaTabela() {
        tabelaLivros.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> {
                    if (selecionado != null) {
                        preencherCampos(selecionado);
                    }
                });
    }

    // Carrega os livros na tabela.
    private void carregarLivros() {
        ObservableList<LivroDTO> lista =
                FXCollections.observableArrayList(
                        livroService.listarLivros()
                );
        tabelaLivros.setItems(lista);
    }

    // Cadastra um novo livro.
    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        boolean valido = livroValidator.validarLivros(
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        if (!valido) return;

        livroService.cadastrarLivro(
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        carregarLivros();
        limparCampos();
    }

    // Atualiza a tabela.
    @FXML
    private void btnListarAction(ActionEvent event) {
        carregarLivros();
    }

    // Busca um livro pelo ID.
    @FXML
    private void btnBuscarAction(ActionEvent event) {
        LivroDTO livro = livroService.buscarPorId(txtId.getText());

        if (livro != null) {
            preencherCampos(livro);
        }
    }

    // Atualiza um livro.
    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        boolean valido = livroValidator.validarLivros(
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        if (!valido) return;

        livroService.atualizarLivro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        carregarLivros();
        limparCampos();
    }

    // Exclui um livro.
    @FXML
    private void btnApagarAction(ActionEvent event) {
        livroService.deletarLivro(txtId.getText());
        carregarLivros();
        limparCampos();
    }

    // Limpa os campos.
    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    // Coloca os dados do livro nos campos.
    private void preencherCampos(LivroDTO livro) {
        txtId.setText(String.valueOf(livro.getId()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtAno.setText(String.valueOf(livro.getAno_publicacao()));
    }

    private void limparCampos() {
        TelaUtil.limparCampos(
                txtId, txtTitulo, txtAutor, txtAno, tabelaLivros
        );
    }
}