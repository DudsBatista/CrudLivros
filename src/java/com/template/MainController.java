
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
    @FXML private TextField txtNome;
    @FXML private TextField txtSenha;

    @FXML private TableView<LivroDTO> tabelaUsuarios;
    @FXML private TableColumn<LivroDTO, Integer> colId;
    @FXML private TableColumn<LivroDTO, String> colNome;

    @FXML private Button btnCadastrar;
    @FXML private Button btnListar;
    @FXML private Button btnApagar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpar;
    @FXML private Button btnAtualizar;

    private LivroDAO livtoDAO = new LivroDAO();

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
       LivroDTO objLivroDTO = new LivroDTO();
        objLivroDTO.setNome(txtNome.getText());
        usuarioDAO.cadastrarUsuario(objLivroDTO);
        btnListarAction(event);
        btnLimparAction(event);
    }

    @FXML
    private void btnListarAction(ActionEvent event) {
        try {
            ObservableList<LivroDTO> lista = FXCollections.observableArrayList(LivroDAO.listarUsuarios());
            tabelaUsuarios.setItems(lista);
        } catch (Exception e) {
            System.out.println("Erro ao listar na tabela: " + e.getMessage());
        }
    }

    @FXML
    private void btnBuscarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            int id = Integer.parseInt(txtId.getText());
           LivroDTO dto = LivroDTO.buscarUsuarioPorId(id);

            if (dto != null) {
                txtNome.setText(dto.getNome());
            } else {
                System.out.println("Usuário não encontrado!");
            }
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        LivroDTO objLivroDTO = new LivroDTO();
        objLivroDTO.setId(Integer.parseInt(txtId.getText()));
        objLivroDTO.setNome(txtNome.getText());

        LivroDAO.alterarUsuario(objLivroDTO);

        btnListarAction(event);
        btnLimparAction(event);
    }

    @FXML
    private void btnApagarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            int id = Integer.parseInt(txtId.getText());
            LivroDAO.excluirUsuario(id);

            btnListarAction(event);
            btnLimparAction(event);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtId.clear();
        txtNome.clear();
        if (txtSenha != null) txtSenha.clear();
    }

    @FXML
    private void initialize()
    {
        System.out.println("FXML loaded successfully!");
        if (colId != null && colNome != null) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            btnListarAction(null);
        }
    }
}