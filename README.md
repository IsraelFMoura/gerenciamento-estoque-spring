# 📦 API de Gerenciamento de Estoque

Uma API REST profissional desenvolvida em **Java 21** com **Spring Boot** para o controle e gerenciamento inteligente de estoque de produtos. O sistema conta com validações de regras de negócio (como impedimento de estoque negativo), tratamento de exceções global padronizado e documentação interativa automatizada via **Swagger UI (OpenAPI 3)**.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**: Versão estável de suporte a longo prazo (LTS).
* **Spring Boot 4.1.0 (Snapshot)**: Framework para o desenvolvimento ágil da aplicação.
* **Spring Data JPA**: Abstração de persistência e comunicação com o banco de dados.
* **Hibernate**: Mecanismo de mapeamento objeto-relacional (ORM).
* **H2 Database**: Banco de dados relacional em memória, ideal para desenvolvimento e testes rápidos.
* **Lombok**: Biblioteca para redução de código boilerplate (getters, setters, construtores).
* **Springdoc OpenAPI / Swagger UI**: Geração automática da documentação visual e interativa das rotas.

---

## 🏛️ Arquitetura do Projeto

O projeto segue o padrão de arquitetura em camadas do ecossistema Spring, garantindo a separação de responsabilidades e facilidade de manutenção:

1. **Model (`Produto.java`)**: Mapeia a entidade que representa a tabela `TB_PRODUTO` no banco de dados.
2. **Repository (`ProdutoRepository.java`)**: Interface que herda `JpaRepository` e fornece operações nativas de CRUD sem necessidade de SQL manual.
3. **Service (`ProdutoService.java`)**: Camada onde residem as **regras de negócio**, como o bloqueio automático se o saldo atual for menor do que a quantidade de saída solicitada.
4. **Controller (`ProdutoController.java`)**: Expõe os endpoints HTTP da API para consumo externo.
5. **Exception Handler (`ManipuladorExcecoesGlobal.java`)**: Captura falhas e exceções de negócio globalmente, convertendo erros genéricos (Status 500) em respostas JSON limpas e padronizadas com o status HTTP correto (**400 Bad Request**).

---

## 🔌 Endpoints da API (Rotas)

O Swagger mapeia automaticamente e organiza todas as operações disponíveis no sistema:

### 🔹 Operações do CRUD Principal (`/produtos`)
* `GET /produtos` - Lista todos os produtos cadastrados.
* `POST /produtos` - Cadastra um novo produto (recebe o corpo em JSON).
* `PUT /produtos/{id}` - Atualiza todos os dados de um produto existente baseado no ID.
* `DELETE /produtos/{id}` - Exclui um produto do sistema através do ID.

### 🔹 Regras de Negócio de Movimentação de Estoque
* `POST /produtos/{id}/entrada?quantidade=X` - Adiciona uma quantidade específica ao estoque de um produto existente.
* `POST /produtos/{id}/saida?quantidade=X` - Subtrai uma quantidade do estoque do produto. Caso a quantidade solicitada seja maior do que o saldo em estoque, a operação é barrada.

---

## 🛑 Tratamento de Erros Padronizado

Quando uma regra de negócio falha (ex: tentar retirar mais produtos do que o disponível), a rede de proteção global intercepta e devolve um formato limpo para o integrador frontend:

```json
{
  "timestamp": "2026-05-27T15:59:45.123456",
  "status": 400,
  "erro": "Erro na Operação",
  "mensagem": "Saldo insuficiente no estoque! Estoque atual: 15",
  "caminho": "/produtos/1/saida"
}
