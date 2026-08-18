package com.template.controller;

import com.template.model.dto.LivroDTO;
import com.template.service.LivroService;
import com.template.util.TelaUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML private Button btnCadastrar;
    @FXML private Button btnListar;
    @FXML private Button btnApagar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnLimpar;
    @FXML private Button btnBuscar;

    private final LivroService livroService = new LivroService();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCamposNumericos();
        configurarSelecaoDaTabela();
        carregarLivros();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));
    }

    private void configurarCamposNumericos() {
        txtAno.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*") || valorNovo.length() > 4) {
                txtAno.setText(valorAntigo);
            }
        });

        txtId.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*")) {
                txtId.setText(valorAntigo);
            }
        });
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
        ObservableList<LivroDTO> lista =
                FXCollections.observableArrayList(
                        livroService.listarLivros()
                );

        tabelaLivros.setItems(lista);
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        livroService.cadastrarLivro(
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        carregarLivros();

        TelaUtil.limparCampos(
                txtId,
                txtTitulo,
                txtAutor,
                txtAno,
                tabelaLivros
        );
    }

    @FXML
    private void btnListarAction(ActionEvent event) {
        carregarLivros();
    }

    @FXML
    private void btnBuscarAction(ActionEvent event) {
        LivroDTO livro = livroService.buscarPorId(txtId.getText());

        if (livro != null) {
            preencherCampos(livro);
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        livroService.atualizarLivro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                txtAno.getText()
        );

        carregarLivros();

        TelaUtil.limparCampos(
                txtId,
                txtTitulo,
                txtAutor,
                txtAno,
                tabelaLivros
        );
    }

    @FXML
    private void btnApagarAction(ActionEvent event) {
        livroService.deletarLivro(txtId.getText());

        carregarLivros();

        TelaUtil.limparCampos(
                txtId,
                txtTitulo,
                txtAutor,
                txtAno,
                tabelaLivros
        );
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        TelaUtil.limparCampos(
                txtId,
                txtTitulo,
                txtAutor,
                txtAno,
                tabelaLivros
        );
    }

    private void preencherCampos(LivroDTO livro) {
        txtId.setText(String.valueOf(livro.getId()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtAno.setText(String.valueOf(livro.getAno_publicacao()));
    }

    @FXML
    private void selecionarLivro() {
        LivroDTO livro = tabelaLivros.getSelectionModel().getSelectedItem();

        if (livro != null) {
            preencherCampos(livro);
        }
    }
}