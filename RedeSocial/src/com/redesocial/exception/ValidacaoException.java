package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros de validação de dados.
 * Extende a classe RuntimeException, permitindo o lançamento de exceções em tempo de execução.
 */
public class ValidacaoException extends RuntimeException {

    /**
     * Construtor da exceção que recebe uma mensagem de erro.
     * @param mensagem A mensagem de erro a ser associada à exceção.
     */
    public ValidacaoException(String mensagem) {
        super(mensagem); // Passa a mensagem para a classe pai (RuntimeException)
    }
}