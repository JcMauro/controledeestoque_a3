# Controle de estoque - A3

Este sistema foi desenvolvido como parte da disciplina de Programacao de Solucoes Computacionais (PSC) da Universidade do Sul de Santa Catarina (UNISUL). O objetivo do projeto e criar uma aplicacao para gerenciamento de controle de estoque, com interfaces graficas, integracao com banco de dados e uso do padrao DAO.

## Descricao do Projeto

Este projeto tem como objetivo o desenvolvimento de um sistema de controle de estoque, permitindo ao usuario realizar uma gestao mais eficiente dos produtos armazenados. O sistema oferece funcionalidades como cadastro e atualizacao de produtos, cadastro de categorias, movimentacao de estoque e geracao de relatorios.

A aplicacao foi desenvolvida utilizando Java com Swing para a interface grafica, MySQL como sistema de gerenciamento de banco de dados e JDBC para comunicacao entre a aplicacao e o banco.

## Documentacao

- [Documentacao para a criacao do controle de estoque](https://mediacdns3.ulife.com.br/PAT/Upload/5688953/A3_roteiro_20250529201959.pdf)
- [Melhorias de qualidade aplicadas](docs/melhorias-qualidade.md)

## Alunos

| Nome Completo | Github | RA |
| --- | --- | --- |
| Julio Cesar de Souza Mauro | [JcMauro](https://github.com/JcMauro) | 10724269838 |
| Gustavo de Espindola Martins | [gustavodees](https://github.com/gustavodees) | 10724238393 |
| Nycolle Vieira | A confirmar | A confirmar |
| Layse Gabrielly Silva Lima | A confirmar | A confirmar |

## Requisitos Funcionais (RF)

RF01. O sistema deve permitir que o usuario cadastre categorias de produtos, informando: id, nome da categoria, embalagem e tamanho.

RF02. O sistema deve permitir que o usuario edite e exclua categorias previamente cadastradas.

RF03. O sistema deve permitir que o usuario cadastre novos produtos, informando: id, nome do produto, preco, quantidade minima e maxima permitida em estoque, quantidade atual em estoque, unidade e categoria.

RF04. O sistema deve permitir que o usuario edite e exclua produtos previamente cadastrados.

RF05. O sistema deve permitir que o usuario adicione e remova produtos ao estoque atual.

RF06. O sistema deve permitir que o usuario gere relatorio de lista de precos dos produtos.

RF07. O sistema deve permitir que o usuario gere relatorio de balanco fisico/financeiro.

RF08. O sistema deve permitir que o usuario gere relatorio de produtos abaixo da quantidade minima de estoque.

RF09. O sistema deve permitir que o usuario gere relatorio de produtos acima da quantidade maxima de estoque.

RF10. O sistema deve permitir que o usuario gere relatorio de quantidade de produtos agrupados por categoria.

RF11. O sistema deve disponibilizar uma janela especifica que exiba todos os produtos cuja quantidade em estoque esteja abaixo da quantidade minima permitida.

RF12. O sistema deve disponibilizar uma janela especifica que exiba todos os produtos cuja quantidade em estoque esteja acima da quantidade maxima permitida.

## Requisitos Nao Funcionais (RNF)

RNF01. O sistema deve estar disponivel para uso quando o ambiente local estiver configurado corretamente.

RNF02. O sistema deve possuir uma interface intuitiva e de facil utilizacao.

RNF03. O sistema deve responder aos comandos de forma rapida e eficaz.

RNF04. O sistema deve garantir a integridade dos dados no banco durante operacoes de leitura, escrita ou atualizacao.

RNF05. O sistema deve validar os dados inseridos pelo usuario e exibir uma mensagem de erro clara quando algum valor invalido for informado.

## Ferramentas

| Ferramenta | Finalidade |
| --- | --- |
| JDK 17 | Ambiente de desenvolvimento Java |
| MySQL Server 8.0 | Banco de dados relacional |
| JDBC (MySQL Connector/J 8.3.0) | Comunicacao entre Java e MySQL |
| Apache NetBeans | Ambiente de desenvolvimento |
| Maven | Gerenciamento e build do projeto |

## Banco de Dados

A pasta `database` contem o script de criacao das tabelas utilizadas neste projeto. Execute o script em um servidor MySQL local para criar a estrutura necessaria ao funcionamento da aplicacao.

Nome da base:

```text
db_controledeestoque
```

Dados padrao de conexao:

```java
String user = "root";
String password = "Unisul@1520";
```

Tambem e possivel configurar os dados de conexao por variaveis de ambiente:

```text
DB_URL
DB_USER
DB_PASSWORD
```

[Arquivo do Banco de Dados](database/db_controledeestoque.sql)

## Como Executar

Com Maven instalado, execute:

```bash
mvn clean test
```

Para executar a aplicacao pela IDE, utilize a classe principal:

```text
principal.Principal
```

## Referencias

- [How to Create a Perfect README for Your GitHub Project](https://medium.com/@sumudithalanz/the-art-of-crafting-an-effective-readme-for-your-github-project-cf425a8b1580)
- [readme.so](https://readme.so/pt/editor)
- [Implementando documentacao atraves do NetBeans](https://www.devmedia.com.br/javadoc-implementando-documentacao-atraves-do-netbeans/2495)
- [Requisitos funcionais e nao funcionais](https://www.mestresdaweb.com.br/tecnologias/requisitos-funcionais-e-nao-funcionais-o-que-sao)
- [How to link github to Netbeans projects](https://www.youtube.com/watch?v=UOIPS-ewFHg)
