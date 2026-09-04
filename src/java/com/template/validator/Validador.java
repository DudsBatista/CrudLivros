package com.template.validator;

/*
 * Interface genérica para os validadores.
 *
 * Cada regra de validação deve implementar esta interface.
 * Isso permite adicionar novos validadores sem precisar
 * alterar os validadores que já existem.
 */
public interface Validador<T> {

    // Executa a validação.
    boolean validar();

    // Retorna a mensagem caso a validação falhe.
    String getMensagemErro();
}