package com.template;

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

public class MainController
{
    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno;

    // A nova Label que vai mostrar as mensagens em verde embaixo dos campos
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

    private LivroDAO livroDAO = new LivroDAO();

    @FXML
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
    private void initialize()
    {
        System.out.println("FXML carregado!");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));


        // FILTRO DO CAMPO ANO: Impede letras e limita a no máximo 4 caracteres
        txtAno.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            // Corrigido: Usando 'valorNovo' e 'valorAntigo' em vez de inglês
            if (!valorNovo.matches("\\d*") || valorNovo.length() > 4) {
                txtAno.setText(valorAntigo);
            }
        });


        // FILTRO DO CAMPO ID: Impede letras
        txtId.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            // Corrigido: Usando 'valorNovo' e 'valorAntigo' em vez de inglês
            if (!valorNovo.matches("\\d*")) {
                txtId.setText(valorAntigo);
            }
        });

        // FILTRO DO CAMPO ANO: Impede letras e limita a no máximo 4 caracteres
        txtAno.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            // Corrigido: Usando 'valorNovo' e 'valorAntigo' em vez de inglês
            if (!valorNovo.matches("\\d*") || valorNovo.length() > 4) {
                txtAno.setText(valorAntigo);
            }
        });


        // FILTRO DO CAMPO ID: Impede letras
        txtId.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*")) {
                txtId.setText(valorAntigo);   // Corrigido: Usando 'valorNovo' e 'valorAntigo' em vez de inglês
            }
        });
        lblMensagem.setText("");//limpa a label qnd inicia

        carregarLivros();
    }


    @FXML
    private void btnCadastrarAction(ActionEvent event)
    {
        if (txtTitulo.getText().isEmpty() || txtAno.getText().isEmpty()) {
            mostrarMensagemNaTela("Por favor, preencha o Título ou/e Ano!", true);
            return;
        }

        LivroDTO livro = new LivroDTO();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText()));

        livroDAO.cadastrarLivro(livro);

        mostrarMensagemNaTela("Livro cadastrado com sucesso!", false);

        carregarLivros();
        limparCampos();
    }

    @FXML
    private void btnListarAction(ActionEvent event)
    {
        carregarLivros();
        mostrarMensagemNaTela("Lista de livros atualizada!", false);
    }

    @FXML
    private void btnBuscarAction(ActionEvent event)
    {
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Digite um ID para buscar!", true);
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        LivroDTO livro = livroDAO.buscarPorId(id);

        if (livro != null)
        {
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAno_publicacao()));
            mostrarMensagemNaTela("Livro encontrado!", false);
        }
        else
        {
            mostrarMensagemNaTela("Livro não encontrado!", true);
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event)
    {
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Selecione um livro pelo ID antes de atualizar!", true);
            return;
        }

        LivroDTO livro = new LivroDTO();
        livro.setId(Integer.parseInt(txtId.getText()));
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText()));

        livroDAO.atualizarLivro(livro);

        mostrarMensagemNaTela("Dados atualizados com sucesso!", false);

        carregarLivros();
        limparCampos();
    }

    @FXML
    private void btnApagarAction(ActionEvent event)
    {
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Digite ou selecione o ID para apagar!", true);
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        livroDAO.deletarLivro(id);

        mostrarMensagemNaTela("Livro excluído com sucesso!", false);

        carregarLivros();
        limparCampos();
    }

    @FXML
    private void btnLimparAction(ActionEvent event)
    {
        limparCampos();
        mostrarMensagemNaTela("Campos limpos!", false);
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
            lblMensagem.setText(""); // Limpa avisos antigos ao clicar em um item da tabela
        }
    }

    // Altera o texto e a cor da Label na tela
    private void mostrarMensagemNaTela(String texto, boolean ehErro) {
        lblMensagem.setText(texto);
        if (ehErro) {
            // Se for um aviso/erro, pinta o texto de vermelho escuro
            lblMensagem.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
        } else {
            // Se for sucesso, pinta o texto de verde escuro
            lblMensagem.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");
        }
    }
}