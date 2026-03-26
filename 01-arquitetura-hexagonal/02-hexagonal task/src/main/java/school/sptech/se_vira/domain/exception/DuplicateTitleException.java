package school.sptech.se_vira.domain.exception;

public class DuplicateTitleException extends RuntimeException {

    public DuplicateTitleException(String title) {
        super("Tarefa não encontrada com o id: " + title);
    }
}
