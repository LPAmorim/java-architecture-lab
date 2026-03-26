package school.sptech.se_vira.domain.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Integer id) {
        super("Já existe uma tarefa com o título: '" + id + "'");
    }
}
