# Melhorias de Qualidade

Este documento registra pequenas melhorias aplicadas ao sistema legado antes da criacao dos testes automatizados.

## Alteracoes Realizadas

1. `Produto.getValorTotal()`
   - Antes retornava `Object` e convertia o resultado para `int`.
   - Agora retorna `double`, mantendo o valor financeiro com casas decimais.

2. `Login.toString()`
   - Antes exibia a senha do usuario.
   - Agora exibe `senha=<oculta>`, evitando exposicao de dado sensivel.

3. `ProdutoDAO.removerProduto()`
   - Antes montava o comando SQL concatenando o ID.
   - Agora usa `PreparedStatement`, mantendo o mesmo funcionamento com mais seguranca.

4. `CategoriaDAO.removerCategoria()`
   - Antes montava o comando SQL concatenando o ID.
   - Agora usa `PreparedStatement`, mantendo o mesmo funcionamento com mais seguranca.

5. `ConexaoDAO`
   - Mantem os dados padrao do banco local.
   - Permite alterar URL, usuario e senha por variaveis de ambiente:
     - `DB_URL`
     - `DB_USER`
     - `DB_PASSWORD`

## Objetivo

As alteracoes foram mantidas simples para preservar o padrao academico do projeto. O foco foi melhorar seguranca, manutenibilidade e testabilidade sem alterar a arquitetura principal do sistema.
