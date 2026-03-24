package school.sptech.exerciciojpa.adapter.in.web.dto;

// import jakarta.validation.constraints.*;

/**
 * DTO de entrada — o que o cliente envia na requisição.
 *
 * SOBRE VALIDAÇÃO COM JAKARTA BEAN VALIDATION:
 * Se o projeto tivesse a dependência spring-boot-starter-validation,
 * poderíamos usar anotações para validar automaticamente cada campo.
 * O Spring faria a checagem ao receber o request (com @Valid no Controller)
 * e lançaria MethodArgumentNotValidException em caso de erro → retorna 400.
 *
 * Como este projeto NÃO usa Jakarta, a validação é feita manualmente
 * no Domain - ConsoleService.validateConsoleRequest().
 *
 * As anotações comentadas abaixo mostram o que cada campo teria com Jakarta:
 */
public record ConsoleRequestDto(

    // @NotBlank(message = "Nome é obrigatório")
    // → Rejeita null, "" e "   " (espaços em branco). Só funciona com String.
    String nome,

    // @NotBlank(message = "Fabricante é obrigatório")
    // → Mesmo comportamento: não aceita vazio nem só espaços.
    String fabricante,

    // @NotNull(message = "Geração é obrigatória")
    // → Rejeita apenas null. Usado para tipos que não são String (Integer, Double...).
    // @NotBlank não funciona com Integer — por isso usa @NotNull.
    Integer geracao,

    // @NotNull(message = "Preço é obrigatório")
    // @Positive(message = "Preço deve ser maior que zero")
    // → @Positive garante que o valor é > 0. Funciona com Double, BigDecimal, Integer etc.
    Double preco
) {
}
