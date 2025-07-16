# Projeto Final - API de Gestão de Concessionária

Este projeto consiste no desenvolvimento de uma API RESTful completa para a disciplina de Programação Web, da Universidade Federal do Rio Grande do Norte (UFRN). A aplicação simula um sistema de gerenciamento para uma concessionária/oficina de carros, permitindo o controle de clientes, veículos, peças e revisões.

O principal objetivo foi aplicar os conceitos de arquitetura REST, segurança, persistência de dados e boas práticas de desenvolvimento de software aprendidos em aula.

---

## ✨ Funcionalidades Principais

* **API RESTful Nível 3 (HATEOAS):** Endpoints que seguem os princípios REST, incluindo o uso de hipermídia para descoberta de recursos.
* **Segurança com JWT:** Autenticação e autorização stateless utilizando JSON Web Tokens com assinatura RSA.
* **Operações CRUD Completas:** Implementação de todos os endpoints para Criar, Ler, Atualizar e Deletar (CRUD) para todas as principais entidades.
* **Relacionamentos Complexos:** Modelo de dados com relacionamentos 1-para-1, 1-para-N e N-para-N.
* **Paginação e Ordenação:** Suporte para paginação e ordenação nos endpoints de listagem para otimização de performance.
* **Validação de Dados:** Validação dos dados de entrada para garantir a integridade.
* **Soft Delete:** Implementação de exclusão lógica para que os registros nunca sejam perdidos permanentemente.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Módulos Spring:**
    * Spring Web: Para a criação dos endpoints REST.
    * Spring Data JPA: Para a persistência de dados.
    * Spring Security: Para a camada de segurança.
    * Spring HATEOAS: Para a implementação de hipermídia.
    * Validation: Para a validação de dados.
* **Banco de Dados:** PostgreSQL (rodando em um container Docker).
* **Mapeamento Objeto-Relacional:** Hibernate
* **Mapeamento de DTOs:** MapStruct
* **Containerização:** Docker
* **Build Tool:** Maven

---

## 🚀 Como Executar o Projeto Localmente

Siga os passos abaixo para configurar e rodar a aplicação no seu ambiente de desenvolvimento.

### Pré-requisitos

* [Java (JDK) 17](https://www.oracle.com/java/technologies/downloads/#java17) ou superior.
* [Apache Maven](https://maven.apache.org/download.cgi) 3.8 ou superior.
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e em execução.

### Passos

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/AlfredoBsantos/ProjetoFinalPW.git](https://github.com/AlfredoBsantos/ProjetoFinalPW.git)
    cd ProjetoFinalPW
    ```

2.  **Inicie o Banco de Dados com Docker:**
    Abra um terminal e execute o comando abaixo para iniciar um container PostgreSQL com as configurações necessárias.
    ```bash
    docker run --name meu-postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=gestaovendas -p 5432:5432 -d postgres
    ```

3.  **Execute a Aplicação Spring Boot:**
    Você pode rodar a aplicação pela sua IDE (IntelliJ) ou pelo terminal usando o Maven.
    ```bash
    mvn spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`.

---

## 🔑 Endpoints da API

Para interagir com os endpoints protegidos, primeiro obtenha um token de acesso.

### 1. Autenticação

* **`POST /token`**: Realiza o login e retorna um token JWT.

    **Corpo da Requisição:**
    ```json
    {
      "username": "admin",
      "password": "12345"
    }
    ```
    **Resposta:** Um token JWT que deve ser usado no cabeçalho `Authorization` das requisições seguintes (`Authorization: Bearer SEU_TOKEN`).

### 2. Exemplos de Endpoints CRUD

* **Clientes (Pessoas):**
    * `GET /pessoas`: Lista todos os clientes (paginado).
    * `POST /pessoas`: Cria um novo cliente.
    * `GET /pessoas/{id}`: Busca um cliente pelo ID.
    * `PUT /pessoas/{id}`: Atualiza um cliente.
    * `DELETE /pessoas/{id}`: Deleta um cliente (soft delete).

* **Carros:**
    * `GET /carros`: Lista todos os carros (paginado).
    * `POST /carros`: Cria um novo carro para um cliente.
    * `GET /carros/{id}`: Busca um carro pelo ID.

* **Revisões:**
    * `GET /revisoes`: Lista todas as revisões (paginado).
    * `POST /revisoes`: Cria uma nova revisão para um carro.
    * `POST /revisoes/{revisaoId}/pecas/{pecaId}`: Associa uma peça a uma revisão.

* **Peças:**
    * `GET /pecas`: Lista todas as peças (paginado).
    * `POST /pecas`: Cria uma nova peça.

---

## Video de Apresentação:
[Clique aqui](https://youtu.be/R4QLOwW9YxQ)

## 👨‍💻 Autor

* **Alfredo Bezerra dos Santos**
* **GitHub:** [@AlfredoBsantos](https://github.com/AlfredoBsantos)

