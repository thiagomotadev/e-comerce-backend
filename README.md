# 🏦 Documentação Completa - Backend E-Commerce

## 📚 Sumário

- [Visão Geral](#-visão-geral)
- [Tecnologias Principais](#-tecnologias-principais)
- [Endpoints da API](#-endpoints-da-api-resumo)
- [Autenticação com JWT](#-autenticação-com-jwt)
- [Fluxo de Cadastro/Login](#-fluxo-de-cadastrologin-frontend)
- [Validações e Erros](#-validações-e-erros)
- [Swagger UI](#-swagger-ui-interface-visual-da-api)
- [Estrutura do Banco de Dados](#-estrutura-do-banco-de-dados)
- [Regras de Negócio](#-regras-de-negócio)
- [Exemplo Completo de Pedido](#-exemplo-completo-de-pedido)
- [Como Rodar Localmente](#-como-rodar-localmente)
- [Dicas Finais](#-dicas-finais)
- [Diagrama ER (Mermaid)](#-diagrama-er-mermaid)

## ✨ Visão Geral

Este projeto é o backend de uma aplicação de e-commerce desenvolvida com **Spring Boot 3.4.5**, **Java 17**, **JWT** para autenticação, banco de dados **PostgreSQL** e conteinerização com **Docker**.

Ele expõe endpoints RESTful para que o frontend possa:
- Autenticar usuários
- Criar contas
- Listar, cadastrar e consultar produtos
- Criar e acompanhar pedidos

A documentação abaixo explica de forma **clara e didática** todos os pontos importantes para desenvolvedores frontend e para entendimento do cliente final.

---

## 🚀 Tecnologias Principais

| Tecnologia       | Descrição                                 |
|------------------|--------------------------------------------|
| Java 17          | Linguagem principal do backend              |
| Spring Boot 3.4.5| Framework para criação de APIs REST        |
| PostgreSQL       | Banco de dados relacional                  |
| Spring Security  | Proteção de endpoints e controle de acesso |
| JWT              | Autenticação baseada em token             |
| Docker           | Containerização da aplicação             |
| Swagger / OpenAPI| Documentação automática da API            |

---

## 📁 Endpoints da API (Resumo)

### 🔑 Autenticação
| Método | Rota            | Descrição                      |
|--------|------------------|----------------------------------|
| POST   | `/auth/register` | Registra novo usuário            |
| POST   | `/auth/login`    | Faz login e retorna JWT          |

### 📅 Produtos
| Método | Rota          | Descrição                       | Requer Auth |
|--------|----------------|----------------------------------|-------------|
| POST   | `/products`    | Cria produto (ADMIN)              | Sim         |
| GET    | `/products`    | Lista todos os produtos           | Não         |

### 🌿 Categorias
| Método | Rota          | Descrição                       | Requer Auth |
|--------|----------------|----------------------------------|-------------|
| POST   | `/categories`  | Cria nova categoria (ADMIN)       | Sim         |
| GET    | `/categories`  | Lista todas as categorias         | Não         |

### 🏦 Pedidos
| Método | Rota                          | Descrição                              | Auth      |
|--------|-------------------------------|------------------------------------------|-----------|
| POST   | `/orders`                     | Cria pedido                              | Sim       |
| GET    | `/orders/user/summary`        | Lista pedidos do usuário autenticado     | Sim       |
| GET    | `/orders/admin/summary`       | Lista todos pedidos (ADMIN)              | Sim       |
| GET    | `/orders/{id}`                | Detalhes de um pedido (user/admin)       | Sim       |
| PATCH  | `/orders/admin/{id}/status`   | Atualiza status do pedido (ADMIN)        | Sim       |

---

## 🔐 Autenticação com JWT

O sistema usa **JWT (JSON Web Tokens)** para autenticar e proteger endpoints. 

- Após o login, o backend retorna um token JWT.
- Esse token deve ser enviado no cabeçalho das requisições:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

Sem esse token, os endpoints protegidos retornarão erro 401.

---

## 📊 Fluxo de Cadastro/Login (Frontend)

### Registro de usuário
```json
POST /auth/register
{
  "name": "João",
  "email": "joao@gmail.com",
  "password": "123456",
  "confirmPassword": "123456"
}
```

### Login
```json
POST /auth/login
{
  "email": "joao@gmail.com",
  "password": "123456"
}
```

Resposta:
```json
"eyJhbGciOiJIUzI1NiJ9..."
```

Esse token é utilizado no cabeçalho das requisições autenticadas.

---

## 🚨 Validações e Erros

Todas as requisições são validadas com mensagens claras. Exemplo de erro:
```json
{
  "password": "A senha deve ter no mínimo 6 caracteres"
}
```

Outros erros comuns:
- `401 Unauthorized`: token inválido ou ausente
- `403 Forbidden`: usuário sem permissão
- `404 Not Found`: recurso não encontrado

---

## 📖 Swagger UI (Interface Visual da API)

Acesse:
```
http://localhost:8080/swagger-ui.html
```

Permite:
- Testar endpoints
- Visualizar JSONs (DTOS)
- Explorar regras de negócio da API

---

## 🗃️ Estrutura do Banco de Dados

| Entidade | Relacionamentos         |
|----------|--------------------------|
| User     | 1:N Pedidos              |
| Order    | N:1 User, 1:N Itens      |
| Product  | N:1 Categoria            |
| Category | 1:N Produtos             |

---
## ⚖️ Regras de Negócio

- **Usuários comuns**:
  - Visualizam produtos
  - Criam pedidos
  - Visualizam seus pedidos

- **Administradores**:
  - Cadastram produtos e categorias
  - Visualizam todos os pedidos
  - Atualizam status dos pedidos

---
## ⚡ Exemplo Completo de Pedido

```json
POST /orders
{
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 5, "quantity": 1 }
  ],
  "paymentMethod": "pix"
}
```

Resposta:
```json
{
  "orderId": 42,
  "createdAt": "2025-06-04T20:12:00",
  "total": 150.00,
  "items": [
    { "productName": "Camisa X", "price": 50.00, "quantity": 2 },
    { "productName": "Tenis Y", "price": 50.00, "quantity": 1 }
  ]
}
```

---

## 🧪 Como Rodar Localmente

### 🐳 Docker
```bash
docker-compose up --build
```
Acesso:
- Backend: http://localhost:8080
- PostgreSQL: `localhost:5432`

### ☕ Sem Docker
```bash
./mvnw spring-boot:run
```

---
## 💡 Considerações Finais

- Código modularizado e seguro
- Swagger integrado para facilitar testes
- Ideal para integração com qualquer frontend moderno
---