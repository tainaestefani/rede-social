package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros relacionados aos Posts.
 * Extende a classe RuntimeException, permitindo o lançamento de exceções em tempo de execução.
 */
public class PostException extends RuntimeException {

    /**
     * Construtor da exceção que recebe uma mensagem de erro.
     * @param message Mensagem de erro a ser associada à exceção.
     */
    public PostException(String message) {
        super(message); // Passa a mensagem para a classe pai (RuntimeException)
    }
}