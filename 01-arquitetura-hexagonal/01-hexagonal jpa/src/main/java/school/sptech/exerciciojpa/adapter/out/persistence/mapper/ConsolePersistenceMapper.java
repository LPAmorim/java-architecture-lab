package school.sptech.exerciciojpa.adapter.out.persistence.mapper;

import school.sptech.exerciciojpa.adapter.out.persistence.entity.ConsoleJpaEntity;
import school.sptech.exerciciojpa.domain.model.Console;

public class ConsolePersistenceMapper {
    
    private ConsolePersistenceMapper() {
    }

    public static ConsoleJpaEntity toJpaEntity(Console console) {
        return new ConsoleJpaEntity(
                console.getId(),
                console.getNome(),
                console.getFabricante(),
                console.getGeracao(),
                console.getPreco()
        );
    }

    public static Console toDomain(ConsoleJpaEntity jpaEntity) {
        return new Console(
                jpaEntity.getId(),
                jpaEntity.getNome(),
                jpaEntity.getFabricante(),
                jpaEntity.getGeracao(),
                jpaEntity.getPreco()
        );
    }
}
