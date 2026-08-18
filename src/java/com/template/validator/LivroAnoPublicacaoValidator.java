package com.template.validator;

import java.util.Calendar;

class AnoPublicacaoValidador implements Validador<Integer> {
    private final String nomeCampo;
    private final Integer ano;

    public AnoPublicacaoValidador(String nomeCampo, Integer ano) {
        this.nomeCampo = nomeCampo;
        this.ano = ano;
    }

    @Override
    public boolean validar(Integer valorAtual) {
        if (this.ano == null) return false;
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        return this.ano >= 1450 && this.ano <= anoAtual;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ser um ano válido entre 1450 e o ano atual.";
    }

    @Override
    public Integer getValor() {
        return 0;
    }
}

