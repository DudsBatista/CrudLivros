package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


/**
 * Classe responsável por exibir mensagens
 * e confirmações ao usuário.
 */
public final class DialogUtil {

    private DialogUtil() {
    }
    public static boolean showError(String mensagem) {
        Alert alert =
                new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> resposta =
                alert.showAndWait();
        return resposta.isPresent()
                && resposta.get() == ButtonType.OK;
    }
    /*Exibe uma mensagem de erro.   */
    public static void mostrarErro(String mensagem) {

        showError(mensagem);
    }
    /**
     * Exibe uma mensagem de sucesso.
     */
    public static void mostrarSucesso(String mensagem) {
        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCESSO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Pergunta ao usuário se ele deseja continuar.
     */
    public static boolean confirmar(
            String titulo,
            String mensagem
    ) {
        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> resposta =
                alert.showAndWait();
        return resposta.isPresent()
                && resposta.get() == ButtonType.OK;
    }
}