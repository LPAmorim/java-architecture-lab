package school.sptech.se_vira.exception;

public class DuplicateTitleException extends RuntimeException {

    public DuplicateTitleException(String title) {
        super("Tarefa com título duplicado: " + title);
    }
}
