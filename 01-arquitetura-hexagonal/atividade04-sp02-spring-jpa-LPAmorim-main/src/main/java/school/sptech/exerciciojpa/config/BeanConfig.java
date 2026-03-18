package school.sptech.exerciciojpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import school.sptech.exerciciojpa.domain.port.in.ConsoleUseCase;
import school.sptech.exerciciojpa.domain.port.out.ConsoleRepositoryPort;
import school.sptech.exerciciojpa.domain.service.ConsoleService;

/**
 * Configuração de beans — conecta os Ports com as implementações.
 *
 * O ConsoleService NÃO tem @Service do Spring (ele é classe pura de domínio).
 * Quem faz a "ligação" (wiring) é este BeanConfig.
 *
 * O Spring injeta automaticamente o JpaConsoleRepository (que é @Component)
 * como ConsoleRepositoryPort, e cria o ConsoleService retornando como ConsoleUseCase.
 */
@Configuration
public class BeanConfig {
    
    @Bean
    public ConsoleUseCase consoleUseCase(ConsoleRepositoryPort repositoryPort) {
        return new ConsoleService(repositoryPort);
    }
}
