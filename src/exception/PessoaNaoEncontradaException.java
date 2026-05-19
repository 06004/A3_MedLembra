package exception;

/**
 * Exceção personalizada para indicar que uma pessoa não foi encontrada.
 */
public class PessoaNaoEncontradaException extends Exception {
    public PessoaNaoEncontradaException(String nome) {
        super("Pessoa '" + nome + "' não encontrada no sistema.");
    }
}
