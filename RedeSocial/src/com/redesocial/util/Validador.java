package com.redesocial.util;

import com.redesocial.exception.ValidacaoException;

/**
 * Interface funcional utilizada para definir validações genéricas de dados.
 * O validador recebe um valor e lança uma exceção {@link ValidacaoException} caso o valor não seja válido.
 *
 * @param <T> Tipo do valor a ser validado.
 */
@FunctionalInterface
public interface Validador<T> {

    /**
     * Método responsável por validar o valor fornecido.
     * Caso o valor não seja válido, deve lançar uma {@link ValidacaoException}.
     *
     * @param valor O valor a ser validado.
     * @throws ValidacaoException Se o valor não for válido.
     */
    void validar(T valor) throws ValidacaoException;
}