package com.template.validator;

import java.time.Year;


/**
 * Classe responsável por validar os dados informados para um livro.
 */
public class LivroValidator {

    public static String validar(
            String titulo,
            String autor,
            String anoTexto
    ) {

        // Verifica se o título foi informado
        if (titulo == null || titulo.isBlank()) {

            return "Informe o título do livro.";
        }
        // Verifica se o autor foi informado
        if (autor == null || autor.isBlank()) {

            return "Informe o autor do livro.";
        }
        // Verifica se o ano foi informado
        if (anoTexto == null || anoTexto.isBlank()) {

            return "Informe o ano de publicação.";
        }
        int ano;
        // Tenta transformar o texto em número
        try {
            ano = Integer.parseInt(anoTexto);
        } catch (NumberFormatException e) {
            return "O ano deve conter apenas números.";
        }
        // Descobre o ano atual
        int anoAtual = Year.now().getValue();

        // Verifica se o ano está no intervalo permitido
        if (ano < 1000 || ano > anoAtual) {

            return "Informe um ano entre 1000 e "
                    + anoAtual
                    + ".";
        }
        // Não encontrou nenhum erro
        return null;
    }
}