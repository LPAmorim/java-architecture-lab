package school.sptech.exerciciojpa.domain.exception;

/**
 * Exceção de negócio — console com nome+fabricante já existe.
 * Lançada pelo Service quando tentam cadastrar/atualizar duplicado.
 *
 * Quem traduz isso para HTTP 409 (Conflict) é o GlobalExceptionHandler.
 */
public class DuplicateConsoleException extends RuntimeException {
    public DuplicateConsoleException(String nome, String fabricante) {
        super("Console com nome '" + nome + "' e fabricante '" + fabricante + "' já existe.");
    }
}
