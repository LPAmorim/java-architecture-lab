[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/9hTWhfG5)
# 🎮 Atividade – CRUD de Consoles (Spring Boot + JPA)

## 🎯 Objetivo

Desenvolver uma **API REST** para gerenciamento de consoles de videogame, implementando um **CRUD completo**, utilizando **Spring Boot**, **Spring Data JPA (Repositories)** e **banco H2**.

---

## ⚠️ Atenção

Antes de iniciar o desenvolvimento:

* Leia todo o enunciado com atenção antes de começar;
* Utilize a interface **JpaRepository** para persistência;
* **Não utilize SQL manual ou JdbcTemplate**, o foco aqui é a abstração do JPA;
* O foco da atividade é a **correta implementação dos endpoints**, mapeamento de entidades, validações e códigos HTTP.

---

## 📌 Requisitos Gerais

* A aplicação deve rodar em `http://localhost:8080`
* O recurso principal da API é **/consoles**
* As respostas devem utilizar **códigos HTTP adequados** (200, 201, 204, 404, 400, etc.)
* Em casos de erro, apenas o status correto é necessário.

---

## 📂 Estrutura do Modelo (Entity)

A classe/tabela **Console** deve possuir os seguintes atributos mapeados com anotações JPA:

* `id` : Integer (Chave primária com geração automática)
* `nome` : String
* `fabricante` : String
* `geracao` : Integer
* `preco` : Double

---

## 🔧 Endpoints da API

### 1️⃣   Listagem de consoles
**Objetivo:** Retornar todos os consoles cadastrados.
* **Método HTTP:** GET
* **URL:** `http://localhost:8080/consoles`

* Caso não existam consoles, retorne o **status 204 (No Content)**.

---

### 2️⃣   Busca de console por ID
**Objetivo:** Buscar um console específico pelo seu identificador único.
* **Método HTTP:** GET
* **URL:** `http://localhost:8080/consoles/{id}`
* Caso o ID não exista, retorne **404 (Not Found)**.

---

### 3️⃣   Cadastro de console
**Objetivo:** Salvar um novo console no banco de dados.
* **Método HTTP:** POST
* **URL:** `http://localhost:8080/consoles`

#### Validações:
* Nome e fabricante não podem ser vazios ou nulos;
* Preço deve ser um número positivo;
* Não deve permitir o cadastro de um console com o **mesmo nome e fabricante** já existentes.

#### Exemplo de requisição:
```json
{
  "nome": "PlayStation 5",
  "fabricante": "Sony",
  "geracao": 9,
  "preco": 4500.0
}
```

#### Observações:
* Em caso de sucesso, retorne o objeto criado com **ID gerado** e status **201 (Created)**;
* Caso a regra de unicidade (nome + fabricante) seja violada, retorne o **status 409 (Conflict)** ou **400 (Bad Request)**.

---

### 4️⃣ Atualização de console por ID
**Objetivo:** Atualizar os dados de um console existente.
* **Método HTTP:** PUT
* **URL:** `http://localhost:8080/consoles/{id}`

#### Regras:
* O ID informado na URL deve existir no banco;
* Todas as validações de cadastro devem ser reaplicadas;
* O ID do console não deve ser alterado no processo.

---

### 5️⃣ Exclusão de console por ID
**Objetivo:** Remover um console do sistema.
* **Método HTTP:** DELETE
* **URL:** `http://localhost:8080/consoles/{id}`

* Caso o ID não exista, retorne o **status 404 (Not Found)**.
* Em caso de sucesso, retorne **204 (No Content)**.

---

### 6️⃣ Busca dinâmica de consoles 🔥
**Objetivo:** Filtrar consoles por critérios específicos via Query Parameters.
* **Método HTTP:** GET
* **URL:** `http://localhost:8080/consoles/search`

#### Parâmetros opcionais:
* `nome`
* `fabricante`

#### Regras de negócio:
* Os filtros informados devem ser combinados (**AND**);
* A busca por `nome` e `fabricante` deve ser **parcial** (Containing) e **insensível a maiúsculas/minúsculas** (IgnoreCase);
* Se nenhum resultado for encontrado, retorne **204 (No Content)**.

---

## 💡 Dicas Importantes

* Configure o `application.properties` para o banco H2 e use `spring.jpa.show-sql=true` para debugar;
* Utilize as anotações `@Entity`, `@Id` e `@GeneratedValue` no seu modelo;
* No Repository, utilize os **Dynamic Finders** do Spring Data JPA para os filtros;
* Teste todos os cenários de sucesso e erro utilizando o **Cliente HTTP**.

---

Boa atividade e bons códigos! 🕹️🚀
