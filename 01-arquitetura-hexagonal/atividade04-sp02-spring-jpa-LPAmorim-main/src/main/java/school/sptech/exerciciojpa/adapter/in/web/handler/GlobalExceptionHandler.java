package school.sptech.exerciciojpa.adapter.in.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import school.sptech.exerciciojpa.domain.exception.ConsoleNoContentException;
import school.sptech.exerciciojpa.domain.exception.ConsoleNotFoundException;
import school.sptech.exerciciojpa.domain.exception.DuplicateConsoleException;

/**
 * Tratamento global de exceções — centraliza TODOS os erros em um único lugar.
 *
 * Antes (3 camadas), cada endpoint do Controller tinha try/catch repetido.
 * Agora o Controller fica limpo e o handler cuida da tradução:
 *   Exceção de domínio → HTTP Status Code.
 *
 * DICA: O Spring encontra esta classe automaticamente porque ela tem @RestControllerAdvice.
 * Qualquer exceção lançada em qualquer camada do request é capturada aqui.
 *
 * MELHORIA FUTURA: Retornar ProblemDetail (RFC 7807) em vez de String.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceção de negócio: console não encontrado → 404
    @ExceptionHandler(ConsoleNotFoundException.class)
    public ResponseEntity<String> handleConsoleNotFound(ConsoleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConsoleNoContentException.class)
    public ResponseEntity<String> handleConsoleNoContent(ConsoleNoContentException ex) {
        return ResponseEntity.noContent().build();
    }

    // Exceção de negócio: nome+fabricante duplicado → 409
    @ExceptionHandler(DuplicateConsoleException.class)
    public ResponseEntity<String> handleDuplicateConsole(DuplicateConsoleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Validação manual lança IllegalArgumentException → 400
    // Se tivesse Jakarta + @Valid, o MethodArgumentNotValidException faria esse papel.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /*
     * DICA: Este handler só seria ativado se usasse Jakarta Bean Validation
     * com @Valid no Controller. Mantido comentado para referência de estudo.
     *
     * @ExceptionHandler(MethodArgumentNotValidException.class)
     * public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
     *     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
     * }
     */

}
