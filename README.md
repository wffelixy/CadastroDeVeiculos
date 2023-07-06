# Cadastro de Veículos

Este projeto é uma API para o cadastro de veículos.

## Pré-requisitos

- Java 17
- MySQL 8

## Configuração do Banco de Dados

1. Crie um banco de dados chamado `tinnova`.
2. No arquivo `application.properties`, verifique e atualize as configurações do banco de dados conforme necessário:

## Executando o Projeto

1. Clone o repositório para a sua máquina.
2. Navegue até o diretório do projeto.
3. Execute o comando `mvn clean install` para compilar e empacotar o projeto.
4. Execute o comando `java -jar target/cadastra-veiculos-0.0.1-SNAPSHOT.jar` para iniciar a aplicação.

## Endpoints

- **GET /veiculos**: Retorna todos os veículos cadastrados.
- **GET /veiculos/{veiculoId}**: Retorna um veículo específico com base no ID.
- **POST /veiculos**: Cadastra um novo veículo.
- **PUT /veiculos/{veiculoId}**: Atualiza um veículo existente.
- **DELETE /veiculos/{veiculoId}**: Remove um veículo existente.
- **PATCH /veiculos/{veiculoId}**: Atualiza parcialmente um veículo existente.
- **GET /veiculos/filtro**: Filtra veículos por marca e/ou ano.
- **GET /veiculos/nao-vendidos/quantidade**: Retorna a quantidade de veículos não vendidos.
- **GET /veiculos/distribuicao-decada**: Retorna a distribuição de veículos por década.
- **GET /veiculos/distribuicao-fabricante**: Retorna a distribuição de veículos por fabricante.
- **GET /veiculos/registrados-ultima-semana**: Retorna os veículos registrados na última semana.


GET /veiculos
Retorna todos os veículos cadastrados.

Método: GET
URL: /veiculos
Resposta de Sucesso: 200 OK
Exemplo de Resposta:

[
  {
    "id": 1,
    "marca": "Ford",
    "ano": 2020,
    "descricao": "Fiesta Sedan",
    "vendido": false,
    "created": "2023-07-05T10:30:00",
    "updated": "2023-07-05T10:30:00"
  },
  {
    "id": 2,
    "marca": "Chevrolet",
    "ano": 2018,
    "descricao": "Onix",
    "vendido": true,
    "created": "2023-07-05T09:45:00",
    "updated": "2023-07-05T10:15:00"
  },
  ...
]

GET /veiculos/{veiculoId}
Retorna um veículo específico com base no ID.

Método: GET
URL: /veiculos/{veiculoId}
Parâmetros de URL:
veiculoId (obrigatório): ID do veículo a ser buscado.
Resposta de Sucesso: 200 OK
Resposta de Falha: 404 Not Found
Exemplo de Resposta (sucesso):

{
  "id": 1,
  "marca": "Ford",
  "ano": 2020,
  "descricao": "Fiesta Sedan",
  "vendido": false,
  "created": "2023-07-05T10:30:00",
  "updated": "2023-07-05T10:30:00"
}


POST /veiculos
Cadastra um novo veículo.

Método: POST
URL: /veiculos
Corpo da Requisição: JSON com os dados do veículo a ser cadastrado.
Resposta de Sucesso: 201 Created
Resposta de Falha: 401 Unauthorized
Exemplo de Corpo da Requisição:

{
  "marca": "Volkswagen",
  "ano": 2019,
  "descricao": "Golf",
  "vendido": false
}

Exemplo de Resposta (sucesso):
{
  "id": 3,
  "marca": "Volkswagen",
  "ano": 2019,
  "descricao": "Golf",
  "vendido": false,
  "created": "2023-07-05T11:00:00",
  "updated": "2023-07-05T11:00:00"
}

PUT /veiculos/{veiculoId}
Atualiza um veículo existente.

Método: PUT
URL: /veiculos/{veiculoId}
Parâmetros de URL:
veiculoId (obrigatório): ID do veículo a ser atualizado.
Corpo da Requisição: JSON com os dados atualizados do veículo.
Resposta de Sucesso: 200 OK
Resposta de Falha: 404 Not Found ou 401 Unauthorized
Exemplo de Corpo da Requisição:

{
  "marca": "Chevrolet",
  "ano": 2018,
  "descricao":"Onix Sedan",
  "vendido": true
}

Exemplo de Resposta (sucesso):
{
"id": 2,
"marca": "Chevrolet",
"ano": 2018,
"descricao": "Onix Sedan",
"vendido": true,
"created": "2023-07-05T09:45:00",
"updated": "2023-07-05T11:30:00"
}


### DELETE /veiculos/{veiculoId}

Remove um veículo existente.

- Método: DELETE
- URL: /veiculos/{veiculoId}
- Parâmetros de URL:
- veiculoId (obrigatório): ID do veículo a ser removido.
- Resposta de Sucesso: 204 No Content
- Resposta de Falha: 404 Not Found

### PATCH /veiculos/{veiculoId}

Atualiza parcialmente um veículo existente.

- Método: PATCH
- URL: /veiculos/{veiculoId}
- Parâmetros de URL:
- veiculoId (obrigatório): ID do veículo a ser atualizado.
- Corpo da Requisição: JSON com os campos a serem atualizados.
- Resposta de Sucesso: 200 OK
- Resposta de Falha: 404 Not Found ou 401 Unauthorized
- Exemplo de Corpo da Requisição:

{
"descricao": "Novo Onix Sedan",
"vendido": false
}

Exemplo de Resposta (sucesso):

{
"id": 2,
"marca": "Chevrolet",
"ano": 2018,
"descricao": "Novo Onix Sedan",
"vendido": false,
"created": "2023-07-05T09:45:00",
"updated": "2023-07-05T11:45:00"
}


### GET /veiculos/filtro

Filtra os veículos por marca e/ou ano.

- Método: GET
- URL: /veiculos/filtro
- Parâmetros de Query:
- marca (opcional): Marca do veículo.
- ano (opcional): Ano do veículo.
- Resposta de Sucesso: 200 OK
- Exemplo de URL de Requisição (filtrando por marca e ano):

/veiculos/filtro?marca=Ford&ano=2020

Exemplo de Resposta:


Onix Sedan",
"vendido": true
}

diff
Copy code
- Exemplo de Resposta (sucesso):
{
"id": 2,
"marca": "Chevrolet",
"ano": 2018,
"descricao": "Onix Sedan",
"vendido": true,
"created": "2023-07-05T09:45:00",
"updated": "2023-07-05T11:30:00"
}

markdown
Copy code

### DELETE /veiculos/{veiculoId}

Remove um veículo existente.

- Método: DELETE
- URL: /veiculos/{veiculoId}
- Parâmetros de URL:
- veiculoId (obrigatório): ID do veículo a ser removido.
- Resposta de Sucesso: 204 No Content
- Resposta de Falha: 404 Not Found

### PATCH /veiculos/{veiculoId}

Atualiza parcialmente um veículo existente.

- Método: PATCH
- URL: /veiculos/{veiculoId}
- Parâmetros de URL:
- veiculoId (obrigatório): ID do veículo a ser atualizado.
- Corpo da Requisição: JSON com os campos a serem atualizados.
- Resposta de Sucesso: 200 OK
- Resposta de Falha: 404 Not Found ou 401 Unauthorized
- Exemplo de Corpo da Requisição:
{
"descricao": "Novo Onix Sedan",
"vendido": false
}

 Exemplo de Resposta (sucesso):
{
"id": 2,
"marca": "Chevrolet",
"ano": 2018,
"descricao": "Novo Onix Sedan",
"vendido": false,
"created": "2023-07-05T09:45:00",
"updated": "2023-07-05T11:45:00"
}

### GET /veiculos/filtro

Filtra os veículos por marca e/ou ano.

- Método: GET
- URL: /veiculos/filtro
- Parâmetros de Query:
- marca (opcional): Marca do veículo.
- ano (opcional): Ano do veículo.
- Resposta de Sucesso: 200 OK
- Exemplo de URL de Requisição (filtrando por marca e ano):
/veiculos/filtro?marca=Ford&ano=2020






-Exemplo de Resposta:
[
{
"id": 1,
"marca": "Ford",
"ano": 2020,
"descricao": "Fiesta Sedan",
"vendido": false,
"created": "2023-07-05T10:30:00",
"updated": "2023-07-05T10:30:00"
},
...
]


### GET /veiculos/nao-vendidos/quantidade

Retorna a quantidade de veículos não vendidos.

- Método: GET
- URL: /veiculos/nao-vendidos/quantidade
- Resposta de Sucesso: 200 OK
- Exemplo de Resposta:

volta um inteiro = 3


### GET /veiculos/distribuicao-decada

Retorna a distribuição de veículos por década.

- Método: GET
- URL: /veiculos/distribuuicao-decada

Resposta de Sucesso: 200 OK
Exemplo de Resposta:

{
  "Década 2000": 5,
  "Década 2010": 10,
  "Década 2020": 8
}

GET /veiculos/distribuicao-fabricante
Retorna a distribuição de veículos por fabricante.

Método: GET
URL: /veiculos/distribuicao-fabricante
Resposta de Sucesso: 200 OK
Exemplo de Resposta:

{
  "Ford": 5,
  "Chevrolet": 8,
  "Volkswagen": 7,
  "Toyota": 3
}

GET /veiculos/registrados-ultima-semana
Retorna os veículos registrados na última semana.

Método: GET
URL: /veiculos/registrados-ultima-semana
Resposta de Sucesso: 200 OK
Exemplo de Resposta:

[
  {
    "id": 1,
    "marca": "Ford",
    "ano": 2020,
    "descricao": "Fiesta Sedan",
    "vendido": false,
    "created": "2023-06-28T09:30:00",
    "updated": "2023-06-28T09:30:00"
  },
  {
    "id": 4,
    "marca": "Volkswagen",
    "ano": 2022,
    "descricao": "Golf GTI",
    "vendido": false,
    "created": "2023-06-30T14:15:00",
    "updated": "2023-06-30T14:15:00"


