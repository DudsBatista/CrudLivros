package com.template.util;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public final class DialogUtil {
    // Impede a criação de objetos dessa classe.
    private DialogUtil() {

    }
    // Exibe uma mensagem de aviso.
    public static void showWarning(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    // Exibe uma mensagem de erro.
    public static void mostrarErro(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Exibe uma mensagem de sucesso.
    public static void mostrarSucesso(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCESSO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Exibe uma confirmação e retorna true se o usuário confirmar.
    public static boolean confirmar(String titulo, String mensagem) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> resposta = alert.showAndWait();
        return resposta.isPresent() && resposta.get() == ButtonType.OK;
    }
}