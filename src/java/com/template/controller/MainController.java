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

    /*
     * As dependências são recebidas pelo construtor.
     */
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

    // Configura as colunas da TableView.
    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));
    }

    // Permite somente números nos campos de ID e ano.
    private void configurarCamposNumericos() {
        txtAno.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*") || valorNovo.length() > 4) {
                txtAno.setText(valorAntigo);}});

        txtId.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*")) {
                txtId.setText(valorAntigo);}});
    }

    // Preenche os campos quando um livro é selecionado.
    private void configurarSelecaoDaTabela() {
        tabelaLivros.getSelectionModel().selectedItemProperty().addListener((observable, livroAntigo, livroSelecionado) -> {
            if (livroSelecionado != null) {preencherCampos(livroSelecionado);}});
    }


    // Carrega os livros na tabela.
    private void carregarLivros() {
        ObservableList<LivroDTO> lista = FXCollections.observableArrayList(livroService.listarLivros());tabelaLivros.setItems(lista);
    }
    // Cadastra um novo livro.
    @FXML private void btnCadastrarAction(ActionEvent event) {
        /*
         * O Controller utiliza a interface
         * ILivroValidator para executar a validação.
         */
        boolean valido = livroValidator.validarLivros(txtTitulo.getText(), txtAutor.getText(), txtAno.getText());
        // Se houver erro, interrompe o cadastro.
        if (!valido) {
            return;
        }
        // Solicita ao Service o cadastro.
        livroService.cadastrarLivro(txtTitulo.getText(), txtAutor.getText(), txtAno.getText());
        // Atualiza a tabela.
        carregarLivros(); limparCampos();
    }
    // Atualiza a tabela.
    @FXML private void btnListarAction(ActionEvent event) {carregarLivros();}

    // Busca um livro pelo ID.

    @FXML private void btnBuscarAction(ActionEvent event) {
        LivroDTO livro = livroService.buscarPorId(txtId.getText());// Se encontrou, preenche os campos.
        if (livro != null) {
            preencherCampos(livro);
        }
    }

    // Atualiza um livro.
    @FXML private void btnAtualizarAction(ActionEvent event) {
        // Valida os dados antes da atualização.
        boolean valido = livroValidator.validarLivros(txtTitulo.getText(), txtAutor.getText(), txtAno.getText());
        if (!valido) {
            return;
        }
        // Solicita ao Service a atualização.
        livroService.atualizarLivro(txtId.getText(), txtTitulo.getText(), txtAutor.getText(), txtAno.getText()
        );
        carregarLivros();
        limparCampos();
    }

    // Exclui um livro.
    @FXML private void btnApagarAction(ActionEvent event) {
        livroService.deletarLivro(txtId.getText());
        carregarLivros();
        limparCampos();
    }
    // Limpa os campos da tela.
    @FXML private void btnLimparAction(ActionEvent event) {limparCampos();}

    // Coloca os dados do livro nos campos.
    private void preencherCampos(LivroDTO livro) {
        txtId.setText(String.valueOf(livro.getId()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor() );
        txtAno.setText(String.valueOf(livro.getAno_publicacao()));
    }

    private void limparCampos() {TelaUtil.limparCampos(txtId, txtTitulo, txtAutor, txtAno, tabelaLivros);}
}