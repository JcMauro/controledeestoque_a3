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
   - Removeu a senha real do banco dos valores padrao do codigo.
   - Mantem URL e usuario padrao para facilitar a execucao local.
   - Permite configurar a senha por variavel de ambiente:
     - `DB_URL`
     - `DB_USER`
     - `DB_PASSWORD`

6. `GitHub Actions`
   - A senha real do banco local foi removida do workflow.
   - O banco usado no CI passou a utilizar uma senha simples e descartavel, criada apenas para a execucao dos testes automatizados.

## Objetivo

As alteracoes foram mantidas simples para preservar o padrao academico do projeto. O foco foi melhorar seguranca, manutenibilidade e testabilidade sem alterar a arquitetura principal do sistema.
