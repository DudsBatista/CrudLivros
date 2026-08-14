package com.template.validator;

import java.time.Year;

/**
 * Valida os dados informados para um livro.
 */
public class LivroValidator {

    /**
     * Valida título, autor e ano de publicação.
     *
     * @param titulo título do livro
     * @param autor autor do livro
     * @param anoTexto ano digitado pelo usuário
     * @return mensagem de erro ou null quando os dados são válidos
     */
    public static String validar(
            String titulo,
            String autor,
            String anoTexto
    ) {
        if (titulo == null || titulo.isBlank()) {
            return "Informe o título do livro.";
        }

        if (autor == null || autor.isBlank()) {
            return "Informe o autor do livro.";
        }

        if (anoTexto == null || anoTexto.isBlank()) {
            return "Informe o ano de publicação.";
        }

        int ano;

        try {
            ano = Integer.parseInt(anoTexto);
        } catch (NumberFormatException e) {
            return "O ano deve conter apenas números.";
        }

        int anoAtual = Year.now().getValue();

        if (ano < 1000 || ano > anoAtual) {
            return "Informe um ano entre 1000 e " + anoAtual + ".";
        }

        return null;
    }
}
