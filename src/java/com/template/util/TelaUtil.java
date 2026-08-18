package com.template.util;

import com.template.model.dto.LivroDTO;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public final class TelaUtil {


    public static void limparCampos(
            TextField txtId,
            TextField txtTitulo,
            TextField txtAutor,
            TextField txtAno,
            TableView<LivroDTO> tabelaLivros) {

        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtAno.clear();
        tabelaLivros.getSelectionModel().clearSelection();
    }
}