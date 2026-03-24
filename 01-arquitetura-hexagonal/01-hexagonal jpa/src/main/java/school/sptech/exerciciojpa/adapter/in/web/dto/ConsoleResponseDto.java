package school.sptech.exerciciojpa.adapter.in.web.dto;

/**
 * DTO de saída — o que o cliente recebe na resposta.
 * Só expõe os campos que fazem sentido para quem consome a API.
 */
public record ConsoleResponseDto(
        Integer id,
        String nome,
        String fabricante,
        Integer geracao,
        Double preco
) {
}
