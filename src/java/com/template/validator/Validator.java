package com.template.validator;

    interface Validador<T> {
    boolean validar(T valor);
    String getMensagemErro();
    T getValor();
}
