package com.redesocial.util;

import com.redesocial.exception.ValidacaoException;

@FunctionalInterface
public interface Validador<T> {

    void validar(T valor) throws ValidacaoException;
}