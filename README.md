# 🚀 E-commerce com Java e Spring Boot

Projeto de um sistema de e-commerce completo, desenvolvido como parte do curso **Formação Java** do Full Stack Club.    
A aplicação abrange desde a arquitetura MVC e APIs REST até a containerização com Docker.

--- 

## ✨ Funcionalidades Principais

- ✅ **Catálogo de Produtos:** Visualização de produtos com filtro por categoria.
- ✅ **Carrinho de Compras:** Gerenciamento de itens em sessão.
- ✅ **Fluxo de Pedidos:** Checkout completo com criação de pedidos no banco de dados.
- ✅ **Painel Administrativo:** CRUD completo para Produtos, Usuários e Pedidos.
- ✅ **API REST:** Endpoints para gerenciamento de recursos via HTTP.
- ✅ **Containerização:** Ambiente de produção completo com Docker e Docker Compose.

--- 

## 🛠️ Stack Tecnológica

- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, Hibernate
- **Frontend:** Thymeleaf, HTML, CSS
- **Banco de Dados:** PostgreSQL (produção), H2 (desenvolvimento/teste)
- **Build/Dependency:** Maven
- **Containerização:** Docker, Docker Compose
- **Testes:** JUnit 5, Mockito

## 🧠 Hard Skills Desenvolvidas

Para construir este projeto, foram aplicadas e aprofundadas as seguintes habilidades técnicas, que são altamente demandadas no mercado de desenvolvimento backend Java:

- **Linguagem Java (21+):**
    - Uso de features modernas como **Records** para DTOs, **Streams API** para manipulação de coleções, e **Optionals** para tratamento de valores nulos.

- **Ecossistema Spring:**
    - **Spring Boot:** Configuração de projetos, gerenciamento de dependências e uso de `profiles`.
    - **Spring MVC:** Arquitetura para aplicações web, com `Controllers` para gerenciar requisições HTTP.
    - **Spring Data JPA:** Persistência de dados com padrão **Repository** e mapeamento objeto-relacional (ORM) com **Hibernate**.

- **Template Engine:**
    - **Thymeleaf:** Renderização de páginas web no lado do servidor, integrando o backend Java com o frontend HTML.

- **APIs e Arquitetura:**
    - **Design de APIs RESTful:** Construção de endpoints seguindo os padrões REST.
    - **Padrão DTO (Data Transfer Object):** Separação entre o modelo de domínio e a camada de API.
    - **Arquitetura em 3 Camadas:** Organização do código em `Controller`, `Service` e `Repository`.

- **Banco de Dados:**
    - **PostgreSQL:** Utilização como banco de dados de produção.
    - **H2 Database:** Uso para testes automatizados e ambiente de desenvolvimento inicial.

- **Testes Automatizados:**
    - **JUnit 5 e Mockito:** Criação de testes unitários e de integração para garantir a qualidade e a confiabilidade do código.

- **Ferramentas de Build e Versionamento:**
    - **Maven:** Gerenciamento de dependências e do ciclo de vida do build do projeto.
    - **Git e GitHub:** Controle de versão e hospedagem do código-fonte.

- **Containerização (DevOps):**
    - **Docker e Docker Compose:** Criação de um ambiente de desenvolvimento e produção containerizado, garantindo consistência e facilitando o deploy.

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 21
- Maven 3.6+
- Docker e Docker Compose

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone git@github.com:seu-username/ecommerce-java-springboot.git
    cd ecommerce-java-springboot
    ```

2.  **Execute o ambiente Docker:**
    *Isso irá iniciar o banco de dados PostgreSQL e a interface Adminer.*
    ```bash
    docker-compose up -d
    ```

3.  **Execute a aplicação Spring Boot:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Acesse a aplicação:**
    - **Loja:** [http://localhost:8080](http://localhost:8080)
    - **Adminer (DB):** [http://localhost:8081](http://localhost:8081)

--- 

## 🔌 Endpoints da API

A API está disponível no prefixo `/api`.

- `GET /api/products`: Lista todos os produtos.
- `PUT /api/products/{id}/price`: Atualiza o preço de um produto.
- `POST /api/orders`: Cria um novo pedido.
- ... e outros.

## Comandos Principais

```bash
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=admin
./mvnw clean clean package
./mvnw test
```

## H2 Console

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:ecommerce-db`
- **Username**: `sa`
- **Password**: (vazio)

## Usuários de Teste

O sistema inicia com os seguintes usuários cadastrados:

- **Admin**: admin@admin.com / admin123
- **João Silva**: joao@email.com / senha123
- **Maria Santos**: maria@email.com / senha123
- **Pedro Oliveira**: pedro@email.com / senha123 (conta inativa)
- **Ana Costa**: ana@email.com / senha123 (conta suspensa)

## Solução de Problemas

### Porta 8080 já em uso (Windows)

**Opção 1**: Encerrar o processo que está usando a porta

```bash
# Encontrar o PID do processo
netstat -ano | findstr :8080

# Encerrar o processo (substituir <PID> pelo ID retornado)
taskkill /PID <PID> /F
```

**Opção 2**: Alterar a porta da aplicação

Adicione em `src/main/resources/application.properties`:

```properties
server.port=8081
```

**Opção 3**: Executar temporariamente em outra porta

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

## Estrutura do Projeto

```
src/main/java/com/ecommerce/
├── controllers/          # Controladores MVC
├── entities/            # Entidades JPA (Product, User, Order)
├── repositories/        # Repositórios Spring Data
├── config/             # Configurações (WebConfig, AutoLogin)
└── EcommerceApplication.java

src/main/resources/
├── templates/          # Páginas Thymeleaf
├── static/
│   ├── css/           # Estilos
│   └── js/            # Scripts
└── application.properties
```

## IntelliJ IDEA Community

Para usar auto-login de admin no IntelliJ IDEA Community:

1. Abra Run/Debug Configurations (Run > Edit Configurations)
2. Selecione ou crie uma configuração para `EcommerceApplication`
3. No campo "Program arguments", adicione: `--spring.profiles.active=admin`
4. Clique em Apply e execute normalmente

