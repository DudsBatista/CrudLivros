package com.template.validator;

/*
 * Interface responsável pelo contrato da validação dos livros.
 *
 * O Controller depende desta interface e não da implementação
 * LivroValidator.
 */
public interface ILivroValidator {

    boolean validarLivros(
            String titulo,
            String autor,
            String anoTexto


    );
}