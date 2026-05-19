package exception;

/**
 * Exceção personalizada para erros de validação de dados.
 */
public class ValidacaoException extends Exception {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
