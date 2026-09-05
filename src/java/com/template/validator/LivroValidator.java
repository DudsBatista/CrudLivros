package com.template.validator;
import java.util.ArrayList;
import java.util.List;
import static com.template.util.DialogUtil.showWarning;

/*
 * Classe responsável por validar os dados do livro.
 *
 * Ela reúne todos os validadores necessários e executa
 * cada validação em sequência.
 */
public class LivroValidator implements ILivroValidator {
    @Override
    public boolean validarLivros(
            String titulo,
            String autor,
            String anoTexto
    ) {
        // Lista que armazena os validadores dos campos.
        List<Validador<String>> validadores =
                new ArrayList<>();
        // Verifica se o título foi preenchido.
        validadores.add(
                new CampoObrigatorioValidador(
                        "Título",
                        titulo
                )
        );
        // Verifica se o autor foi preenchido.
        validadores.add(
                new CampoObrigatorioValidador(
                        "Autor",
                        autor
                )
        );
        // Verifica se o ano foi preenchido.
        validadores.add(
                new CampoObrigatorioValidador(
                        "Ano de publicação",
                        anoTexto
                )
        );
        // Verifica se o ano de publicação é válido.
        validadores.add(
                new LivroAnoPublicacaoValidator(
                        anoTexto
                )
        );
        /*
         * Percorre todos os validadores.
         * Caso algum deles não seja válido, mostra
         * a mensagem de aviso e interrompe a validação.
         */
        for (Validador<String> validador : validadores) {
            if (!validador.validar()) {
                showWarning(
                        validador.getMensagemErro()
                );
                return false;
            }
        }
        // Se todos os validadores passaram, os dados são válidos.
        return true;
    }
}