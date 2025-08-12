<h1 align="center" style="font-weight: bold;">Controle de Despesas Pessoais - Backend 💻</h1>

<p align="center">
    <a href="#technologies">Tecnologias</a> • 
    <a href="#started">Começando</a> • 
    <a href="#routes">API Endpoints</a> •
    <a href="#colab">Collaborators</a> •
</p>

<p align="center">
    <b>É uma aplicação web full-stack que permite aos usuários gerenciar suas finanças pessoais e controlar seu dinheiro. Os usuários poderão se cadastrar, fazer login e, em seguida, registrar, categorizar e visualizar suas receitas e despesas, ajudando-os a ter uma visão clara de sua saúde financeira.</b>
</p>

<h2 id="technologies">💻 Tecnologias</h2>

Lista de todas as Tecnologias usadas no backend:
- Java
- Spring
- Módulos do spring:
    - Spring Web
    - Spring Dev Tools
    - Spring Data JPA
    - Spring Security
    - Java JWT
    - Validation
    - Loombok
- Maven
- Banco de dados:
    - MySQL
    - H2 Database


<h2 id="started">🚀 Começando</h2>

Aqui está a descrição detalhada para rodar o projeto localmente.

<h3>Pré-requisitos</h3>

Aqui está a lista de todos os pré-requisitos necessários para rodar o projeto.

- [Java JDK 17+](https://adoptium.net/pt-BR/temurin/releases?version=17)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- IDE (opcional):
    - Intellij
    - VSCode
    - Eclipse

<h3>Clonando projeto</h3>

Como clonar o projeto

```bash
git clone https://github.com/rafaeldjcarvalho/ControleDeDespesasPessoais
```


<h3>Rodar o projeto</h3>

- Via IDE(IntelliJ, VSCode, Eclipse):
    - Abra o projeto na IDE.
    - Vá até a pasta do serviço(ControleDeDespesasPessoaisApplication).
    - Execute a classes Application.
- Via terminal:
    - Dentro da pasta do serviço, execute:

    ```bash
    cd ControleDeDespesasPessoais
    mvn spring-boot:run
    ``` 


<h2 id="routes">📍 API Endpoints</h2>

Aqui está a lista das principais rotas da API e o que elas fazem.

- Módulo de Autenticação


| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /api/auth/registrar</kbd>      | Cria um novo usuário, ver [request details](#post-user-register)
| <kbd>POST /api/auth/login</kbd>     | Autentica um usuário, ver [request details](#post-user-login)


<h3 id="post-user-register">POST /registrar</h3>

**REQUEST**
```json
{
    "nome": "Matheus",
    "email": "matheus34@gmail.com",
    "senha": "matheus123"
}
```

**RESPONSE**
```json
{
    "nome": "Matheus"
}
```


<h3 id="post-user-login">POST /login</h3>

**REQUEST**
```json
{
    "email": "matheus34@gmail.com",
    "senha": "matheus123"
}
```

**RESPONSE**
```json
{
    "nome": "Matheus",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6InJhZmFlbGRlamVzdXNAZ21haWwuY29tIiwiZXhwIjoxNzU0NzU2Mzc4fQ.TzM7LGUj-VkbXRPq2g8uMR_IgkS9JwuK0iyTMNdeiX0"
}
```


- Módulo de Categorias


| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /api/categorias</kbd>     | Criar nova categoria, ver [request details](#post-category-detail)
| <kbd>GET /api/categorias/{id_usuario}</kbd>     | Recupera as categorias, ver [response details](#get-category-detail)
| <kbd>PUT /api/categorias/{id}</kbd>     | Atualiza uma categoria, ver [request details](#put-category-detail)
| <kbd>DELETE /api/categorias</kbd>     | Deleta uma categoria, ver [request details](#delete-category-detail)

<h3 id="post-category-detail">POST /categorias</h3>

**REQUEST**
```json
{
    "nome": "Comida",
    "descricao": "fast food",
    "id_usuario": 1
}
```

<h3 id="get-category-detail">GET /categorias/{id_usuario}</h3>

**RESPONSE**
```json
[
    {
        "id": 1,
        "nome": "Comida",
        "descricao": "fast food",
        "id_usuario": 1
    },
    {
        "id": 2,
        "nome": "Transporte",
        "descricao": "Carro",
        "id_usuario": 1
    }
]
```

<h3 id="put-category-detail">PUT /categorias/{id}</h3>

**REQUEST**
```json
{
    "nome": "Cinema",
    "descricao": "Filme e Pipoca",
    "id_usuario": 1
}
```

**RESPONSE**
```json
{
    "id": 2,
    "nome": "Cinema",
    "descricao": "Filme e Pipoca",
    "id_usuario": 1
}
```

<h3 id="delete-category-detail">DELETE /categorias</h3>

**REQUEST**
```json
{
    "id": 2,
    "id_usuario": 1
}
```

- Módulo de Transações


| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /api/transacoes</kbd>     | Criar nova transação, ver [request details](#post-transaction-detail)
| <kbd>GET /api/transacoes?Id_usuario=1&mes=8&ano=2025&id_categoria=3</kbd>     | Recupera transações, ver [response details](#get-transaction-detail)
| <kbd>GET /api/transacoes/{id_usuario}/{id_transaction}</kbd>     | Obtém os detalhes de uma transação específica, ver [response details](#get-transaction-specifies-detail)
| <kbd>PUT /api/transacoes</kbd>     | Atualiza uma transação, ver [request details](#put-transaction-detail)
| <kbd>DELETE /api/transacoes</kbd>     | Deleta uma transacao, ver [request details](#delete-transaction-detail)

<h3 id="post-transaction-detail">POST /transacoes</h3>

**REQUEST**
```json
{
    "descricao": "Aifood",
    "valor": 50,
    "data": "2025-08-11",
    "hora": "15:33",
    "tipo": "DESPESA",
    "id_categoria": 1,
    "id_usuario": 1
}
```

**RESPONSE**
```json
{
    "id": 1,
    "descricao": "Aifood",
    "valor": 50,
    "data": "2025-08-11",
    "hora": "15:33:00",
    "tipo": "DESPESA",
    "id_categoria": 1,
    "id_usuario": 1
}
```

<h3 id="get-transaction-detail">GET /transacoes?Id_usuario=1&mes=8&ano=2025&id_categoria=1</h3>

**RESPONSE**
```json
[
    {
        "id": 1,
        "descricao": "Aifood",
        "valor": 50.00,
        "data": "2025-08-11",
        "hora": "15:33:00",
        "tipo": "DESPESA",
        "id_categoria": 1,
        "id_usuario": 1
    }
]
```

<h3 id="get-transaction-specifies-detail">GET /transacoes/{id_usuario}/{id_transaction}</h3>

**RESPONSE**
```json
{
    "id": 1,
    "descricao": "Aifood",
    "valor": 50.00,
    "data": "2025-08-11",
    "hora": "15:33:00",
    "tipo": "DESPESA",
    "id_categoria": 1,
    "id_usuario": 1
}
```

<h3 id="put-transaction-detail">PUT /transacoes</h3>

**REQUEST**
```json
{
    "descricao": "Restaurante",
    "valor": 150.00,
    "data": "2025-08-11",
    "hora": "15:33:00",
    "tipo": "DESPESA",
    "id_categoria": 1,
}
```

**RESPONSE**
```json
{
    "id": 1,
    "descricao": "Restaurante",
    "valor": 150.00,
    "data": "2025-08-11",
    "hora": "15:33:00",
    "tipo": "DESPESA",
    "id_categoria": 1,
    "id_usuario": 1
}
```

<h3 id="delete-transaction-detail">DELETE /transacoes</h3>

**REQUEST**
```json
{
    "id": 1,
    "id_usuario": 1
}
```

- Módulo de Dashboard


| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /api/dashboard/summary?usuario_id=1&mes=8&ano=2025</kbd>      | Recebe um resumo financeiro, ver [response details](#get-dashboard-summary)



<h3 id="get-dashboard-summary">GET /summary?usuario_id=1&mes=8&ano=2025</h3>

**RESPONSE**
```json
{
    "totalReceitas": 2000.00,
    "totalDespesas": 1100.00,
    "saldo": 900.00,
    "despesasPorCategoria": [
        {
            "categoria": "Alimentacao",
            "total": 100.00
        },
        {
            "categoria": "Transporte",
            "total": 1000.00
        }
    ]
}
```

Fluxo Resumido

```plaintext
Usuário → TransactionController
              |
              |→ AuthorizedTransaction
              |     ↳ AVAILABLE
              |
              |→ NotificationService
              |     ↳ Remetente
              |     ↳ Destinatário
              |
       ↳ Gera Transação, salva no banco
```

<h2 id="colab">🤝 Collaborators</h2>

Special thank you for all people that contributed for this project.

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/141766102?v=4" width="100px;" alt="Rafael Profile Picture"/><br>
        <sub>
          <b>Rafael de Jesus Carvalho</b>
        </sub>
      </a>
    </td>
  </tr>
</table>