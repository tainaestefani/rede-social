package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros relacionados ao Usuário.
 * Extende a classe RuntimeException, permitindo o lançamento de exceções em tempo de execução.
 */
public class UsuarioException extends RuntimeException {

    /**
     * Construtor da exceção que recebe uma mensagem de erro.
     * @param mensagem A mensagem de erro a ser associada à exceção.
     */
    public UsuarioException(String mensagem) {
        super(mensagem); // Passa a mensagem para a classe pai (RuntimeException)
    }
}