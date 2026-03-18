# ☕ Java Architecture Lab

Laboratório pessoal de práticas em **Java** — arquiteturas de software, princípios SOLID, Clean Code, design patterns e lógica de programação.

> Cada pasta é um módulo de estudo independente. O workflow principal: **3 Camadas** → **Hexagonal** → **Event-Driven**.

---

## 📂 Estrutura do Repositório

| #  | Pasta                                        | Arquitetura           | Propósito                              | Status         |
|----|----------------------------------------------|-----------------------|----------------------------------------|----------------|
| 01 | `01-arquitetura-tres-camadas/`               | 3 Camadas             | Projetos iniciais em 3 camadas         | ✅ Em andamento |
| 02 | `02-arquitetura-hexagonal/`                  | Hexagonal             | Refatoração de 3 camadas → Hexagonal   | ✅ Em andamento |
| 03 | `03-arquitetura-event-driven/`               | Event-Driven          | Refatoração de Hexagonal → Event-Driven| 🔜 Planejado   |



---

## 🏛️ As 3 Arquiteturas

### 01 — Arquitetura 3 Camadas (Layered)

**Objetivo:** Ponto de partida. Estrutura simples e direta para projetos menores.

**Estrutura típica:**
```
project-name/
├── controller/          # Recebe requisições HTTP
├── service/             # Regras de negócio
├── repository/          # Acesso a dados
└── entity/              # Modelos com @Entity
```

**Boas práticas:**
- ✅ Separação clara de responsabilidades
- ✅ Fácil de entender e implementar
- ✅ DTOs opcionais para transferência de dados
- ✅ Validações no Service
- ⚠️ Dependências fluem para baixo (Controller → Service → Repository)
- ⚠️ Model acoplado às tecnologias (JPA, JSON)
- ⚠️ Difícil trocar implementações (banco, transporte)

**Quando usar:**
- MVPs e projetos pequenos
- Times iniciantes em arquitetura
- Protótipos rápidos

**Exemplo de fluxo:**
```
POST /api/consoles
    ↓
ConsoleController.create(DTO)
    ↓
ConsoleService.create(DTO)
    ↓
ConsoleRepository.save(Entity)
    ↓
Database
```

---

### 02 — Arquitetura Hexagonal (Ports & Adapters)

**Objetivo:** Isolamento do domínio. Fácil refatoração e teste.

**Estrutura típica:**
```
project-name/
├── domain/
│   ├── model/           # POJOs puros (sem anotações)
│   ├── exception/       # Exceções de negócio
│   ├── port/in/         # Interfaces de entrada (use cases)
│   ├── port/out/        # Interfaces de saída (abstrações)
│   ├── service/         # Lógica de negócio
│   └── validator/       # Validações do domínio
├── adapter/
│   ├── in/web/          # Controller, DTOs, WebMapper, Handler
│   └── out/persistence/ # JPA Entity, SpringData, PersistenceMapper
└── config/BeanConfig.java  # Wiring e injeção
```

**Boas práticas:**
- ✅ Domínio totalmente independente de frameworks
- ✅ Easy to test (sem Spring, sem banco)
- ✅ Portas (interfaces) bem definidas
- ✅ Mapeadores explícitos (DTOs ↔ Model Entity)
- ✅ Trocar implementações sem mexer no domínio
- ✅ Validações no Service (lógica pura)
- ⚠️ Mais código (mapeadores, interfaces)
- ⚠️ Aprendizado maior

**Quando usar:**
- Projetos médios/grandes
- Quando refatoração é frequente
- Times que querem domínio testável

**Exemplo de fluxo:**
```
POST /api/consoles
    ↓
ConsoleController.create(DTO)
    ↓
WebMapper.toDomain(DTO) → Domain Model
    ↓
ConsoleService.create(Model) [lógica pura]
    ↓
ConsoleRepositoryAdapter [implementa porta]
    ↓
PersistenceMapper.toEntity(Model) → JPA Entity
    ↓
SpringDataRepository.save(Entity)
    ↓
Database
```

---

### 03 — Arquitetura Event-Driven

**Objetivo:** Desacoplamento através de eventos. Sistemas escaláveis e assíncronos.

**Estrutura típica:**
```
project-name/
├── domain/
│   ├── model/           
│   ├── event/           # Eventos de domínio
│   ├── port/in/         # Comandos (use cases)
│   ├── port/out/        # Event publishers, repositórios
│   ├── service/         # Command handlers
│   └── event-handler/   # Listeners de eventos
├── adapter/
│   ├── in/web/          # REST Controllers (comandos)
│   ├── in/event/        # Listeners de eventos externos
│   └── out/             # Databases, message brokers, etc
└── config/
```

**Boas práticas:**
- ✅ Desacoplamento máximo
- ✅ Escalabilidade horizontal
- ✅ Reatividade (respostas a eventos)
- ✅ Auditoria natural (histórico de eventos)
- ⚠️ Eventual consistency (não imediato)
- ⚠️ Complexidade em debug
- ⚠️ Requer message broker (Kafka, RabbitMQ, etc)

**Quando usar:**
- Sistemas distribuídos
- Processamento assíncrono necessário
- Múltiplos serviços (microserviços)
- Alta concorrência

---

## 🔄 Fluxo de Refatoração: 3 Camadas → Hexagonal → Event-Driven

```
┌─────────────────────┐
│   3 Camadas         │  ← Projeto inicial (simples)
│  (Controller/       │
│   Service/Repo)     │
└──────────┬──────────┘
           │ Refatoração 1
           ↓
┌─────────────────────┐
│   Hexagonal         │  ← Domínio isolado
│  (Ports & Adapters) │     (fácil teste)
│  (sem eventos)      │
└──────────┬──────────┘
           │ Refatoração 2
           ↓
┌─────────────────────┐
│  Event-Driven       │  ← Desacoplado
│  (com eventos e     │     (assíncrono)
│   message broker)   │
└─────────────────────┘
```

**Como refatorar:**

1. **3 Camadas → Hexagonal:**
   - Extrair Domain Model (POJO puro)
   - Criar Ports (ConsoleUseCase, ConsoleRepositoryPort)
   - Mover Service para domínio
   - Criar Adapters (Web, Persistence)
   - Adicionar Mapeadores

2. **Hexagonal → Event-Driven:**
   - Criar classes de Evento (ConsoleCreatedEvent)
   - Refatorar Service para publicar eventos
   - Criar Event Handlers
   - Integrar message broker
   - Remover chamadas síncronas diretas



---

## 📚 Exemplo Prático — Hexagonal com Spring Boot

**Projeto:** Console API (CRUD de consoles de videogame)

### O que foi praticado

- **Domain Model** puro (POJO sem anotações de framework)
- **Ports IN** — interface `ConsoleUseCase` definindo os casos de uso
- **Ports OUT** — interface `ConsoleRepositoryPort` abstraindo persistência
- **Adapter IN (Web)** — Controller limpo + DTOs + WebMapper + GlobalExceptionHandler
- **Adapter OUT (Persistence)** — JPA Entity separada + PersistenceMapper + SpringData
- **BeanConfig** — wiring sem `@Service` no domínio
- **Validação manual** (sem Jakarta Bean Validation)
- **Exceções de domínio** separadas da camada HTTP

### Estrutura

```
school.sptech.exerciciojpa/
├── domain/
│   ├── model/Console.java                    # POJO puro
│   ├── exception/                            # Exceções de negócio
│   ├── port/in/ConsoleUseCase.java           # Porta de entrada
│   ├── port/out/ConsoleRepositoryPort.java   # Porta de saída
│   └── service/ConsoleService.java           # Regras de negócio
├── adapter/
│   ├── in/web/                               # Controller, DTOs, Mapper, Handler
│   └── out/persistence/                      # JPA Entity, SpringData, Mapper
└── config/BeanConfig.java                    # Wiring
```

---

## 04 — SOLID (planejado)

| Princípio                   | Sigla | Pasta                              |
|-----------------------------|-------|-------------------------------------|
| Single Responsibility       | S     | `04-solid/single-responsibility/`   |
| Open/Closed                 | O     | `04-solid/open-closed/`             |
| Liskov Substitution         | L     | `04-solid/liskov-substitution/`     |
| Interface Segregation       | I     | `04-solid/interface-segregation/`   |
| Dependency Inversion        | D     | `04-solid/dependency-inversion/`    |

---

## 05 — Design Patterns (planejado)

| Padrão     | Categoria    | Pasta                          |
|------------|-------------|--------------------------------|
| Strategy   | Behavioral  | `05-design-patterns/strategy/`  |
| Factory    | Creational  | `05-design-patterns/factory/`   |
| Observer   | Behavioral  | `05-design-patterns/observer/`  |
| Builder    | Creational  | `05-design-patterns/builder/`   |
| Singleton  | Creational  | `05-design-patterns/singleton/` |
| Adapter    | Structural  | `05-design-patterns/adapter/`   |

---

## 📝 Notas de Estudo

### Hexagonal vs 3 Camadas

| Aspecto            | 3 Camadas                         | Hexagonal                          |
|--------------------|-----------------------------------|-------------------------------------|
| Dependências       | Controller → Service → Repository | Adapters → Domain ← Adapters       |
| Model              | Tem `@Entity`                     | POJO puro                           |
| Trocar banco       | Mexe no Service                   | Cria novo Adapter OUT               |
| Trocar transporte  | Reescreve Controller              | Cria novo Adapter IN                |
| Testabilidade      | Precisa subir Spring              | Testa domínio sem framework         |

### Validação: Jakarta vs Manual

| Com Jakarta                              | Sem Jakarta (manual)                          |
|------------------------------------------|-----------------------------------------------|
| `@NotBlank` no DTO                       | `if (campo == null)` no Service               |
| `@Valid` no Controller                   | Método `validateRequest()` no Service         |
| Spring lança exceção automática          | Service lança `IllegalArgumentException`      |
| Handler trata `MethodArgumentNotValidException` | Handler trata `IllegalArgumentException` |

---

*Repositório em construção — novos módulos serão adicionados conforme o estudo avança.*
