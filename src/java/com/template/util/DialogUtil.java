package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Classe responsável por exibir mensagens e confirmações ao usuário.
 */
public final class DialogUtil {

    private DialogUtil() {
        // Impede a criação de objetos dessa classe utilitária.
    }

    /**
     * Exibe uma mensagem de erro.
     *
     * Este método mantém o nome showError porque o LivroDAO
     * já está importando e utilizando esse método.
     *
     * @param mensagem mensagem que será exibida
     * @return true quando o usuário fecha a janela no botão OK
     */
    public static boolean showError(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> resposta = alert.showAndWait();

        return resposta.isPresent()
                && resposta.get() == ButtonType.OK;
    }

    /**
     * Exibe uma mensagem de erro usando um nome em português.
     *
     * @param mensagem mensagem que será exibida
     */
    public static void mostrarErro(String mensagem) {
        showError(mensagem);
    }

    /**
     * Exibe uma mensagem de sucesso.
     *
     * @param mensagem mensagem que será exibida
     */
    public static void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCESSO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Pergunta ao usuário se ele deseja continuar uma operação.
     *
     * @param titulo título da janela
     * @param mensagem pergunta exibida ao usuário
     * @return true se o usuário clicar em OK
     */
    public static boolean confirmar(
            String titulo,
            String mensagem
    ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> resposta = alert.showAndWait();

        return resposta.isPresent()
                && resposta.get() == ButtonType.OK;
    }
}
