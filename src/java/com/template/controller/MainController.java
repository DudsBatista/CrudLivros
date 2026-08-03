package com.template.controller;

import com.template.model.dao.LivroDAO;
import com.template.model.dto.LivroDTO;
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
    // Campos de texto para entrada de dados do livro na interface do usuário
    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno; //variaveis novs

    // Label para exibir mensagens de feedback ao usuário (sucesso ou erro)
    @FXML private Label lblMensagem;

    // Tabela para exibir a lista de livros
    @FXML private TableView<LivroDTO> tabelaLivros;

    // Colunas da tabela, mapeadas para as propriedades do objeto LivroDTO
    @FXML private TableColumn<LivroDTO, Integer> colId;
    @FXML private TableColumn<LivroDTO, String> colTitulo;
    @FXML private TableColumn<LivroDTO, String> colAutor;
    @FXML private TableColumn<LivroDTO, Integer> colAno;

    // Botões de ação na interface do usuário
    @FXML private Button btnCadastrar;
    @FXML private Button btnListar;
    @FXML private Button btnApagar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnLimpar;
    @FXML private Button btnBuscar;

    // Instância da classe LivroDAO para interagir com o banco de dados
    private LivroDAO livroDAO = new LivroDAO();

    /**
     * Método responsável por carregar a lista de livros do banco de dados
     * e exibi-los na tabela da interface gráfica.
     */
    @FXML
    private void carregarLivros()
    {
        try {
            // Obtém a lista de livros do DAO e a converte para uma ObservableList
            ObservableList<LivroDTO> lista =
                    FXCollections.observableArrayList(livroDAO.listarLivros());

            // Define a lista de livros na TableView
            tabelaLivros.setItems(lista);

        } catch (Exception e) {
            // Em caso de erro, imprime a mensagem no console
            System.out.println("Erro ao carregar livros: " + e.getMessage());
        }
    }

    /**
     * Método de inicialização do controlador, chamado automaticamente após o carregamento do FXML.
     * Configura as colunas da tabela e adiciona listeners para validação de entrada nos campos de texto.
     */
    @FXML
    private void initialize()
    {
        System.out.println("FXML carregado!");

        // Configura as fábricas de valor para cada coluna da tabela,
        // associando-as às propriedades correspondentes em LivroDTO.
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));

        // Listener para o campo txtAno: impede a entrada de letras e limita a 4 caracteres.
        txtAno.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*") || valorNovo.length() > 4) {
                txtAno.setText(valorAntigo); // Reverte para o valor antigo se a entrada for inválida
            }
        });

        // Listener para o campo txtId: impede a entrada de letras.
        txtId.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*")) {
                txtId.setText(valorAntigo); // Reverte para o valor antigo se a entrada for inválida
            }
        });

        // O filtro para txtAno e txtId aparece duplicado no código original. Mantido para consistência,
        // mas idealmente deveria haver apenas uma definição para cada campo.
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

        lblMensagem.setText(""); // Limpa a label de mensagem ao iniciar a aplicação

        carregarLivros(); // Carrega os livros na tabela ao iniciar
    }

    /**
     * Manipulador de evento para o botão 'Cadastrar'. Valida os campos, cria um novo LivroDTO e o cadastra no banco de dados.
     * Atualiza a tabela e limpa os campos após o cadastro.
     * @param event O evento de ação gerado pelo botão.
     */
    @FXML
    private void btnCadastrarAction(ActionEvent event)
    {
        // Validação básica: verifica se Título ou Ano estão vazios
        if (txtTitulo.getText().isEmpty() || txtAno.getText().isEmpty()) {
            mostrarMensagemNaTela("Por favor, preencha o Título ou/e Ano!", true);
            return;
        }

        // Cria um novo objeto LivroDTO com os dados dos campos de texto
        LivroDTO livro = new LivroDTO();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText())); // Converte o ano para inteiro

        // Chama o método do DAO para cadastrar o livro
        livroDAO.cadastrarLivro(livro);

        // Exibe mensagem de sucesso
        mostrarMensagemNaTela("Livro cadastrado com sucesso!", false);

        carregarLivros(); // Recarrega a tabela para mostrar o novo livro
        limparCampos();   // Limpa os campos de entrada
    }

    /**
     * Manipulador de evento para o botão 'Listar'.
     * Recarrega a lista de livros na tabela.
     * @param event O evento de ação gerado pelo botão.
     */
    @FXML
    private void btnListarAction(ActionEvent event)
    {
        carregarLivros(); // Recarrega a lista de livros
        mostrarMensagemNaTela("Lista de livros atualizada!", false); // Exibe mensagem de atualização
    }

    /**
     * Manipulador de evento para o botão 'Buscar'.
     * Busca um livro pelo ID digitado e preenche os campos com os dados encontrados.
     * @param event O evento de ação gerado pelo botão.
     */
    @FXML
    private void btnBuscarAction(ActionEvent event)
    {
        // Validação: verifica se o campo ID está vazio
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Digite um ID para buscar!", true);
            return;
        }

        // Converte o ID para inteiro e busca o livro no DAO
        int id = Integer.parseInt(txtId.getText());
        LivroDTO livro = livroDAO.buscarPorId(id);

        // Se o livro for encontrado, preenche os campos de texto com seus dados
        if (livro != null)
        {
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAno_publicacao()));
            mostrarMensagemNaTela("Livro encontrado!", false);
        }
        else // Se o livro não for encontrado
        {
            mostrarMensagemNaTela("Livro não encontrado!", true);
        }
    }

    /**
     * Manipulador de evento para o botão 'Atualizar'.
     * Atualiza os dados de um livro existente no banco de dados com base no ID e nos campos preenchidos.
     * @param event O evento de ação gerado pelo botão.
     */

    @FXML
    private void btnAtualizarAction(ActionEvent event)
    {
        // Validação: verifica se o campo ID está vazio
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Selecione um livro pelo ID antes de atualizar!", true);
            return;
        }

        // Cria um objeto LivroDTO com os dados atualizados, incluindo o ID
        LivroDTO livro = new LivroDTO();
        livro.setId(Integer.parseInt(txtId.getText()));
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setAno_publicacao(Integer.parseInt(txtAno.getText()));

        // Chama o método do DAO para atualizar o livro
        livroDAO.atualizarLivro(livro);

        // Exibe mensagem de sucesso
        mostrarMensagemNaTela("Dados atualizados com sucesso!", false);

        carregarLivros(); // Recarrega a tabela
        limparCampos();   // Limpa os campos
    }

    /**
     * Manipulador de evento para o botão 'Apagar'.
     * Exclui um livro do banco de dados com base no ID digitado.
     * @param event O evento de ação gerado pelo botão.
     */
    @FXML
    private void btnApagarAction(ActionEvent event)
    {
        // Validação: verifica se o campo ID está vazio
        if (txtId.getText().isEmpty()) {
            mostrarMensagemNaTela("Digite ou selecione o ID para apagar!", true);
            return;
        }

        // Converte o ID para inteiro e chama o método do DAO para deletar o livro
        int id = Integer.parseInt(txtId.getText());
        livroDAO.deletarLivro(id);

        // Exibe mensagem de sucesso
        mostrarMensagemNaTela("Livro excluído com sucesso!", false);

        carregarLivros(); // Recarrega a tabela
        limparCampos();   // Limpa os campos
    }

    /**
     * Manipulador de evento para o botão 'Limpar'.
     * Limpa todos os campos de entrada de texto na interface.
     * @param event O evento de ação gerado pelo botão.
     */
    @FXML
    private void btnLimparAction(ActionEvent event)
    {
        limparCampos(); // Chama o método auxiliar para limpar os campos
        mostrarMensagemNaTela("Campos limpos!", false); // Exibe mensagem
    }

    /**
     * Método auxiliar para limpar o texto de todos os campos de entrada.
     */
    private void limparCampos()
    {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtAno.clear();
    }

    /**
     * Manipulador de evento para a seleção de um item na tabela de livros.
     * Preenche os campos de texto com os dados do livro selecionado.
     */
    @FXML
    private void selecionarLivro()
    {
        // Obtém o livro selecionado na tabela
        LivroDTO livro = tabelaLivros.getSelectionModel().getSelectedItem();

        // Se um livro foi selecionado, preenche os campos de texto
        if (livro != null)
        {
            txtId.setText(String.valueOf(livro.getId()));
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAno_publicacao()));
            lblMensagem.setText(""); // Limpa mensagens antigas ao selecionar um item
        }
    }
    /**
     * Método auxiliar para exibir mensagens na label de feedback (lblMensagem).
     * Altera o texto e a cor da mensagem com base se é um erro ou sucesso.
     * @param texto A mensagem a ser exibida.
     * @param ehErro Booleano que indica se a mensagem é de erro (true) ou sucesso (false).
     */


    private void mostrarMensagemNaTela(String texto, boolean ehErro) {
        lblMensagem.setText(texto);
        if (ehErro) {
            // Se for um aviso/erro, define a cor do texto para vermelho escuro
            lblMensagem.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
        } else {
            // Se for sucesso, define a cor do texto para verde escuro
            lblMensagem.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");
        }
    }
}