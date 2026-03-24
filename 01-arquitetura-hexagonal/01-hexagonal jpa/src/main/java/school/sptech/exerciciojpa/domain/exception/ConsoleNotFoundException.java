package school.sptech.exerciciojpa.domain.exception;

/**
 * Exceção de negócio — console não encontrado.
 * Lançada pelo Service quando um id não existe no repositório.
 *
 * Quem traduz isso para HTTP 404 é o GlobalExceptionHandler (adapter web).
 * O domínio não sabe que HTTP existe — ele só sabe que "não achou".
 */
public class ConsoleNotFoundException extends RuntimeException {
    public ConsoleNotFoundException(Integer id) {
        super("Console com id " + id + " não encontrado.");
    }

    public ConsoleNotFoundException(String message) {
        super(message);
    }
}
