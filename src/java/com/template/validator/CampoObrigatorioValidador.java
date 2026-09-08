package com.template.validator;

/** Valida se um campo obrigatório foi preenchido. */
public class CampoObrigatorioValidador implements Validador<String> {

    // Nome do campo que será validado.
    private final String nomeCampo;

    // Valor que será verificado.
    private final String valor;

    // Construtor que recebe o nome do campo e o valor a ser validado.
    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    // Realiza a validação do campo.
    @Override
    public boolean validar() {
        // Retorna true se o valor não for nulo e não estiver vazio.
        return valor != null && !valor.trim().isEmpty();
    }

    // Retorna a mensagem de erro caso o campo não seja preenchido.
    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}