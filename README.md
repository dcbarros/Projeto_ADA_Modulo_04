# Sistema de Gerenciamento de Estoque:

## Descrição: 

>Nossa empresa tem feito o controle de estoque de forma manual a bastante tempo porém está na hora de uma modernização!
>As mercadorias em estoque estão todas em um arquivo do tipo CSV, as colunas são ID do produto, nome, quantidade, cateogia, preço. 
>Nossa aplicação deve ser capaz de ler este arquivo CSV e gravar em uma tabela de nossa base de dados estas informações.

## Descrição do meu projeto:

Em uma empresa de venda de rações para gato o controle do estoque era feito de forma manual, os registros eram feitos de forma manual e ineficiente.

O arquivo CSV era organizado dessa forma:

```
    Nome do produto, Categoria, Quantidade Consumida, custo 
```

Nossa aplicação deve ser capaz de ler este arquivo CSV e gravar em uma tabela de nossa base de dados estas informações.

### Desafios:

Após gravadas as informações alguns relatórios serão necessários (utilize streams para gera-los), crie métodos que respondam as seguintes perguntas:
- Quantas categorias de produtos temos.
- Quantos produtos de cada categoria tem em estoque.
- Valor médio dos produtos
- Produtos com menos de 3 unidades devem ser colocados em uma lista de "estoque baixo"

### Respostas:

- Quantas categorias de produtos temos.

R. Com o método salesService.getAllSaleByCategorie todas as categorias de produtos são retornadas;

- Quantos produtos de cada categoria tem em estoque.

R. Com o método salesService.getInfosByProductNameAndCategories o consumo por todas as categorias e produtos;

- Valor médio dos produtos

R. Não realizei esse procedimento, pois a base de dados é bem homogênio, mas posso gerar esses dados utilizando a query:
```
    select productName, categories, AVG(costs) AS costs from sale group by productName, categories;
```

- Produtos com menos de 3 unidades devem ser colocados em uma lista de "estoque baixo".

R. A resposta está dentro do relatório, no caso desse projeto, é quando o consumo for maior que 15000

## Como instalar manipular o programa:

No diretório:

```
|_src
| |_main
|   |_resources
|     |_META-INF
|       |_persistence_example.xml
```
criar um arquivo persistence.xml baseado no persistence_example.xml, modificando:

```
    <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/" />
    <property name="javax.persistence.jdbc.user" value="" /> ->
    <property name="javax.persistence.jdbc.password" value="" />
```

Adicione a url do seu banco, o usuário e a senha. Após essas modificações é possível ir no arquivo Main.java e iniciar a aplicação.

