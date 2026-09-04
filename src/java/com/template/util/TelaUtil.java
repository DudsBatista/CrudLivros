package com.template.util;

import com.template.model.dto.LivroDTO;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/*
 * Classe responsável pelas operações relacionadas à tela.
 *
 * Neste caso, ela centraliza a limpeza dos campos.
 */
public final class TelaUtil {

    // Impede a criação de objetos dessa classe.
    private TelaUtil() {
    }

    // Limpa os campos e a seleção da tabela.
    public static void limparCampos(
            TextField txtId,
            TextField txtTitulo,
            TextField txtAutor,
            TextField txtAno,
            TableView<LivroDTO> tabelaLivros
    ) {

        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtAno.clear();

        tabelaLivros
                .getSelectionModel()
                .clearSelection();
    }
}