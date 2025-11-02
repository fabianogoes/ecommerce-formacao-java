# E-commerce Application

Sistema de e-commerce desenvolvido com Spring Boot 3.5.6 e Java 21.

## Tecnologias

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA, Hibernate
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: H2 (in-memory)
- **Build**: Maven

## Requisitos

- Java 21
- Maven 3.6+
- Docker Desktop

## Comandos Principais

### Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

### Executar com auto-login de administrador

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=admin
```

Acesse diretamente: http://localhost:8080/admin (já logado como admin)

### Build do projeto

```bash
./mvnw clean install
```

### Executar testes

```bash
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

## Funcionalidades Implementadas

- ✅ Catálogo de produtos com filtro por categoria
- ✅ Carrinho de compras (gerenciamento em sessão)
- ✅ Checkout e finalização de pedidos
- ✅ Cadastro e autenticação de usuários (session-based)
- ✅ Área administrativa com CRUD completo:
  - Gerenciamento de produtos
  - Gerenciamento de usuários
  - Gerenciamento de pedidos
- ✅ Controle de permissões (admin vs usuário comum)
- ✅ Filtros dinâmicos usando enums
- ✅ Integração Thymeleaf com Spring MVC
- ✅ Seed de dados inicial para desenvolvimento

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

## Mais Informações

Consulte o arquivo [CLAUDE.md](CLAUDE.md) para detalhes da arquitetura e padrões utilizados.
