package com.template.validator;

import java.time.Year;

/*
 * Validador responsável por verificar o ano de publicação
 * do livro.
 */
public class LivroAnoPublicacaoValidator implements Validador<String> {
    private final String ano;
    public LivroAnoPublicacaoValidator(String ano) {
        this.ano = ano;
    }
    @Override
    public boolean validar() {
        // Verifica se o ano foi preenchido.
        if (ano == null || ano.isBlank()) {
            return false;}
        try {
            // Converte o ano de String para inteiro.
            int anoNumero =
                    Integer.parseInt(ano.trim());
            // Obtém o ano atual.
            int anoAtual =
                    Year.now().getValue();
            // O ano deve estar entre 1000 e o ano atual.
            return anoNumero >= 1000
                    && anoNumero <= anoAtual;
        } catch (NumberFormatException e) {
            // Retorna falso caso o valor não seja um número.
            return false;
        }
    }
    @Override
    public String getMensagemErro() {

        return "O ano de publicação deve ser válido.";
    }
}