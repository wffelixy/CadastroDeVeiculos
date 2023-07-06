Cadastro de Veículos
Esta é uma API JSON RESTful para gerenciar o cadastro de veículos. A API permite realizar operações como listar veículos, buscar um veículo por ID, cadastrar um novo veículo, atualizar informações de um veículo e remover um veículo do sistema.

Endpoints
Listar veículos
Retorna uma lista de todos os veículos cadastrados.

Endpoint: GET /veiculos

Buscar veículo por ID
Retorna as informações de um veículo específico com base no seu ID.

Endpoint: GET /veiculos/{veiculoId}

Cadastrar um veículo
Cadastra um novo veículo no sistema.

Endpoint: POST /veiculos

Body da requisição:

json
Copy code
{
  "veiculo": "Nome do veículo",
  "marca": "Marca do veículo",
  "ano": 2022,
  "descricao": "Descrição do veículo",
  "vendido": false
}
Atualizar um veículo
Atualiza as informações de um veículo existente com base no seu ID.

Endpoint: PUT /veiculos/{veiculoId}

Body da requisição:

json
Copy code
{
  "veiculo": "Novo nome do veículo",
  "marca": "Nova marca do veículo",
  "ano": 2023,
  "descricao": "Nova descrição do veículo",
  "vendido": true
}
Remover um veículo
Remove um veículo do sistema com base no seu ID.

Endpoint: DELETE /veiculos/{veiculoId}

Filtrar veículos por marca e ano
Filtra os veículos com base na marca e/ou ano informados. Retorna uma lista de veículos que correspondem aos critérios de filtro.

Endpoint: GET /veiculos/filtro?marca={marca}&ano={ano}

Se fornecida apenas a marca, retorna os veículos que possuem a marca especificada.
Se fornecido apenas o ano, retorna os veículos que possuem o ano especificado.
Se fornecidos tanto a marca quanto o ano, retorna os veículos que possuem a marca e o ano especificados.
Como testar a API
Você pode utilizar uma ferramenta como o Postman ou qualquer cliente HTTP para enviar requisições à API e testar os diferentes endpoints e funcionalidades.

Certifique-se de que o servidor esteja em execução e acesse os endpoints conforme especificado acima, fornecendo os parâmetros necessários e verificando as respostas retornadas pela API.

Isso é apenas um exemplo de README e você pode adaptá-lo para incluir informações adicionais, como pré-requisitos de ambiente, configuração do banco de dados, autenticação, entre outros.


Endpoint: GET /veiculos/nao-vendidos/quantidade

Ao acessar a URL /veiculos/nao-vendidos/quantidade no navegador ou no Postman, você receberá a quantidade de veículos que não estão vendidos como resposta.

Obs:

Necessário criar o banco tinnova e a tabela a seguir:

create table veiculo(
  id BIGINT NOT NULL AUTO_INCREMENT,
  veiculo VARCHAR(255) NOT NULL,
  marca VARCHAR(255) NOT NULL,
  ano INT NOT NULL,
  descricao VARCHAR(4000),
  vendido BOOLEAN NOT NULL,
  created DATETIME NOT NULL,
  updated DATETIME NOT NULL,
  
  PRIMARY KEY(id)  
);
