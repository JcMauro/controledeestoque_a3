# Controle de estoque - A3

Este sistema foi desenvolvido como parte da disciplina de Programacao de Solucoes Computacionais (PSC) da Universidade do Sul de Santa Catarina (UNISUL). O objetivo do projeto e criar uma aplicacao para gerenciamento de controle de estoque, com interfaces graficas, integracao com banco de dados e uso do padrao DAO.

Nesta etapa, o projeto foi revisado e complementado para a disciplina de Gestao e Qualidade de Software.

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
| Nycolle Vieira | [NycolleVieira](https://github.com/NycolleVieira) | 10723114148 |
| Layse Gabrielly Silva Lima | [LAYSEGABI](https://github.com/LAYSEGABI) | 10724111931 |

## Responsabilidades

| Nome | Funcao | Responsabilidade |
| --- | --- | --- |
| Julio Cesar de Souza Mauro | Lider de equipe / DevOps / Desenvolvedor | Organizacao geral, acompanhamento das atividades, GitHub Actions, SonarCloud, ajustes de seguranca e apoio nos testes unitarios dos modelos. |
| Gustavo de Espindola Martins | Desenvolvedor / Tester | Testes relacionados a produtos, estoque e relatorios. |
| Layse Gabrielly Silva Lima | Desenvolvedora / Tester | Testes dos validadores de categoria, produto, estoque e usuario. |
| Nycolle Vieira | Desenvolvedora / Tester | Testes relacionados a login, usuarios e validacoes de usuario. |

### Distribuicao das Tarefas

| Nome | Funcionalidade | Link do Teste |
| --- | --- | --- |
| Julio Cesar de Souza Mauro | Ajustes gerais do projeto, criacao da base de testes, testes unitarios dos modelos e apoio na estrutura de qualidade do projeto. | [CategoriaTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/modelo/CategoriaTest.java), [ProdutoTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/modelo/ProdutoTest.java), [UsuarioTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/modelo/UsuarioTest.java), [LoginTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/modelo/LoginTest.java) |
| Nycolle Vieira | Testes relacionados a login, usuarios e integracao com o banco de dados, validando cenarios de sucesso e falha nas classes DAO. | [LoginDAOTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/dao/LoginDAOTest.java), [UsuarioDAOTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/dao/UsuarioDAOTest.java), [UsuarioValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/UsuarioValidadorTest.java) |
| Layse Gabrielly Silva Lima | Testes unitarios dos validadores, cobrindo regras de categoria, estoque, produto e usuario com casos validos e invalidos. | [CategoriaValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/CategoriaValidadorTest.java), [EstoqueValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/EstoqueValidadorTest.java), [ProdutoValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/ProdutoValidadorTest.java), [UsuarioValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/UsuarioValidadorTest.java) |
| Gustavo de Espindola Martins | Testes complementares de produto, estoque e relatorios, reforcando cenarios de busca, limites de estoque e relacionamento entre produto e categoria. | [ProdutoDAOTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/dao/ProdutoDAOTest.java), [ProdutoValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/ProdutoValidadorTest.java), [EstoqueValidadorTest.java](https://github.com/JcMauro/controledeestoque_a3/blob/main/src/test/java/validacao/EstoqueValidadorTest.java) |

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

```text
DB_URL=jdbc:mysql://localhost:3306/db_controledeestoque
DB_USER=root
DB_PASSWORD=sua_senha_do_mysql
```

Por seguranca, a senha do banco nao fica salva no codigo-fonte. Configure os dados de conexao por variaveis de ambiente antes de executar o sistema ou os testes:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/db_controledeestoque"
$env:DB_USER="root"
$env:DB_PASSWORD="sua_senha_do_mysql"
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
