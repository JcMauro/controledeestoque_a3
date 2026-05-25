# Controle de Estoque - A3

Sistema simples de controle de estoque desenvolvido em Java como atividade da disciplina de Programacao de Solucoes Computacionais (PSC), da Universidade do Sul de Santa Catarina (UNISUL).

Autor: Julio Cesar de Souza Mauro

## Descricao

O projeto possui uma interface grafica em Java Swing para cadastro e controle de usuarios, categorias e produtos. A persistencia dos dados e feita em banco MySQL, utilizando JDBC e classes DAO para separar o acesso ao banco das telas do sistema.

## Funcionalidades

- Tela de login.
- Cadastro de usuario.
- Cadastro, listagem, edicao e exclusao de categorias.
- Cadastro, listagem, edicao e exclusao de produtos.
- Relacionamento entre produtos e categorias.
- Validacao simples dos campos da tela de login.
- Teste unitario simples com JUnit 5.

## Tecnologias

- Java
- Swing
- Maven
- MySQL Server 8.0
- MySQL Connector/J 8.3.0
- JUnit 5
- NetBeans

## Estrutura do Projeto

```text
src/main/java
  dao       -> classes de acesso ao banco de dados
  modelo    -> classes de modelo do sistema
  principal -> classe principal
  visao     -> telas Swing

src/test/java
  visao     -> testes unitarios

database.sql -> script de criacao do banco
pom.xml      -> configuracao Maven
```

## Banco de Dados

O banco utilizado pelo projeto se chama:

```text
db_controledeestoque
```

Para criar a estrutura do banco, execute o arquivo:

```text
database.sql
```

O script cria as tabelas:

- `tb_usuarios`
- `tb_categoria`
- `tb_produto`

As configuracoes de conexao ficam nas classes DAO do projeto.

## Como Executar

Abra o projeto no NetBeans ou em outra IDE Java com suporte a Maven.

Classe principal:

```text
principal.Principal
```

Tambem e possivel executar pelo Maven/NetBeans usando a configuracao do arquivo `pom.xml`.

## Teste Unitario

O projeto possui um teste unitario simples com JUnit 5 para a tela de login.

Classe testada:

```text
visao.FrmLogin
```

Metodo testado:

```text
validarCamposLogin(String username, String senha)
```

Classe de teste:

```text
src/test/java/visao/FrmLoginTest.java
```

O teste ativo verifica se usuario e senha preenchidos corretamente passam pela validacao inicial da tela de login.

Comando para executar o teste:

```powershell
mvn -Dtest=FrmLoginTest test
```

No ambiente usado para o projeto, o Maven do NetBeans pode ser executado assim:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk1.8.0_121'; $env:Path="$env:JAVA_HOME\bin;$env:Path"; & 'C:\Program Files\NetBeans-17\netbeans\java\maven\bin\mvn.cmd' -Dtest=FrmLoginTest test
```

Resultado esperado:

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
