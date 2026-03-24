package school.sptech.exerciciojpa.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class InvalidConsoleProvider implements ArgumentsProvider {

    @Override
    public java.util.stream.Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return java.util.stream.Stream.of(
              Arguments.of("Console sem nome", "/request/invalid-console-no-nome.json"),
              Arguments.of("Console nome vazio", "/request/invalid-console-nome-vazio.json"),
              Arguments.of("Console sem fabricante", "/request/invalid-console-no-fabricante.json"),
              Arguments.of("Console com fabricante vazio",
                    "/request/invalid-console-fabricante-vazio.json"),
              Arguments.of("Console sem preço", "/request/invalid-console-no-preco.json"),
              Arguments.of("Console com preço inválido",
                    "/request/invalid-console-preco-invalido.json")
        );
    }
}

