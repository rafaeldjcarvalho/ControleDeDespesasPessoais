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


<h3 id="get-user-register">POST /registrar</h3>

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


- TransactionController


| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /transaction</kbd>     | Criar transação, ver [request details](#post-transaction-detail)

<h3 id="post-flight-detail">POST /transaction</h3>

**REQUEST**
```json
{
  "valor": 50,
  "remetente": 1,
  "destinatario": 2
}
```

**RESPONSE**
```json
{
  "id": 1,
  "valor": 50,
  "remetente": 1,
  "destinatario": 2,
  "dataHora": "07/31/2025T13:48:10"
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