package com.redesocial.exception;

public class UsuarioException extends RuntimeException{
    public UsuarioException(String mensagem) {
        super(mensagem);
    }

    public UsuarioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}