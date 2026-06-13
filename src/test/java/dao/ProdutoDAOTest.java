package dao;

import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validacao.EstoqueValidador;
import visao.Mensagem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoDAOTest {

    private static final int CATEGORIA_ID = 901101;
    private static final int PRODUTO_ID = 902101;
    private static final String PRODUTO_NOME = "Produto Integracao Teste";
    private static final int PRODUTO_BAIXO_ID = 902102;
    private static final int PRODUTO_ALTO_ID = 902103;
    private static final String PRODUTO_BAIXO_NOME = "Produto Estoque Baixo Teste";
    private static final String PRODUTO_ALTO_NOME = "Produto Estoque Alto Teste";

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final EstoqueValidador estoqueValidador = new EstoqueValidador();

    @BeforeEach
    void preparar() throws Exception {
        limpar();
        categoriaDAO.inserirCategoria(new Categoria(CATEGORIA_ID, "Categoria Integracao", "Unidade", "1un"));
    }

    @AfterEach
    void limpar() throws Exception {
        TesteBancoUtil.executar(
                "DELETE FROM tb_produto WHERE id IN (?, ?, ?) OR nome IN (?, ?, ?)",
                PRODUTO_ID, PRODUTO_BAIXO_ID, PRODUTO_ALTO_ID,
                PRODUTO_NOME, PRODUTO_BAIXO_NOME, PRODUTO_ALTO_NOME
        );
        TesteBancoUtil.executar("DELETE FROM tb_categoria WHERE id = ?", CATEGORIA_ID);
    }

    // Este teste cobre o fluxo principal do produto no banco.
    // Ele cadastra, busca, lista, atualiza e remove o produto criado para o cenário de teste.
    @Test
    void deveInserirBuscarAtualizarListarERemoverProduto() {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));
        assertNotNull(produtoDAO.buscarProduto(PRODUTO_ID));
        assertNotNull(produtoDAO.buscarProdutoPorNome(PRODUTO_NOME));
        assertTrue(produtoDAO.getListaProdutos().stream().anyMatch(p -> p.getId() == PRODUTO_ID));
        assertTrue(produtoDAO.getNomesProdutos().contains(PRODUTO_NOME));

        Produto atualizado = new Produto(PRODUTO_ID, PRODUTO_NOME, 12.0, 8, 2, 20, "un", categoria);
        assertTrue(produtoDAO.atualizarProduto(atualizado));
        assertEquals(8, produtoDAO.buscarProduto(PRODUTO_ID).getQuantidade());

        assertTrue(produtoDAO.removerProduto(PRODUTO_ID));
        assertNull(produtoDAO.buscarProduto(PRODUTO_ID));
    }

    // Aqui o mesmo produto é inserido duas vezes.
    // A segunda inserção deve ser recusada para evitar duplicidade no cadastro.
    @Test
    void deveRecusarProdutoDuplicado() {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));
        assertFalse(TesteBancoUtil.executarSemExibirErro(() -> produtoDAO.inserirProduto(produto)));
    }

    // Verifica a busca por um id que não pertence a nenhum produto cadastrado.
    // O DAO deve devolver nulo quando não encontrar registro.
    @Test
    void deveRetornarNullParaProdutoInexistente() {
        assertNull(produtoDAO.buscarProduto(999999));
    }

    // Faz a mesma validação anterior, mas pesquisando pelo nome do produto.
    // Como o nome não existe no banco, o retorno esperado também é nulo.
    @Test
    void deveRetornarNullParaNomeInexistente() {
        assertNull(produtoDAO.buscarProdutoPorNome("produto_inexistente"));
    }

    // Testa se um produto cadastrado pode ser localizado pelo nome.
    @Test
    void deveBuscarProdutoExistentePorNome() {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        Produto encontrado = produtoDAO.buscarProdutoPorNome(PRODUTO_NOME);
        assertNotNull(encontrado);
        assertEquals(PRODUTO_ID, encontrado.getId());
    }

    // Testa a entrada de estoque e a atualização do produto no banco.
    @Test
    void deveAdicionarEstoqueEAtualizarBanco() throws Mensagem {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        int novoEstoque = estoqueValidador.calcularAdicao(produto.getQuantidade(), 4, produto.getMax());
        produto.setQuantidade(novoEstoque);

        assertTrue(produtoDAO.atualizarProduto(produto));
        assertEquals(9, produtoDAO.buscarProduto(PRODUTO_ID).getQuantidade());
    }

    // Testa a saída de estoque e a atualização do produto no banco.
    @Test
    void deveRemoverEstoqueEAtualizarBanco() throws Mensagem {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 10, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        int novoEstoque = estoqueValidador.calcularRemocao(produto.getQuantidade(), 3, produto.getMin());
        produto.setQuantidade(novoEstoque);

        assertTrue(produtoDAO.atualizarProduto(produto));
        assertEquals(7, produtoDAO.buscarProduto(PRODUTO_ID).getQuantidade());
    }

    // Testa o relatório que agrupa produtos por categoria.
    @Test
    void deveListarProdutosAgrupadosPorCategoriaNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        boolean encontrou = false;
        for (Object[] linha : produtoDAO.contarProdutosPorCategoria()) {
            if ("Categoria Integracao".equals(linha[0])
                    && (Integer) linha[1] >= 1) {
                encontrou = true;
            }
        }
        assertTrue(encontrou);
    }

    // Testa o relatório de produtos abaixo do estoque mínimo.
    @Test
    void deveListarProdutosAbaixoDoEstoqueMinimoNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_BAIXO_ID, PRODUTO_BAIXO_NOME, 10.0, 1, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        boolean encontrou = false;
        for (Object[] linha : produtoDAO.contarProdutosEstoqueMinimo()) {
            if (PRODUTO_BAIXO_ID == (Integer) linha[0]
                    && PRODUTO_BAIXO_NOME.equals(linha[1])) {
                encontrou = true;
            }
        }
        assertTrue(encontrou);
    }

    // Testa se produto exatamente no mínimo não aparece como estoque baixo.
    @Test
    void naoDeveListarProdutoNoMinimoComoEstoqueBaixo() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 2, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        boolean encontrou = false;
        for (Object[] linha : produtoDAO.contarProdutosEstoqueMinimo()) {
            if (PRODUTO_ID == (Integer) linha[0]) {
                encontrou = true;
            }
        }
        assertFalse(encontrou);
    }

    // Testa o relatório de produtos acima do estoque máximo.
    @Test
    void deveListarProdutosAcimaDoEstoqueMaximoNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ALTO_ID, PRODUTO_ALTO_NOME, 10.0, 25, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        boolean encontrou = false;
        for (Object[] linha : produtoDAO.contarProdutosEstoqueMaximo()) {
            if (PRODUTO_ALTO_ID == (Integer) linha[0]
                    && PRODUTO_ALTO_NOME.equals(linha[1])) {
                encontrou = true;
            }
        }
        assertTrue(encontrou);
    }

    // Testa se produto exatamente no máximo não aparece como estoque alto.
    @Test
    void naoDeveListarProdutoNoMaximoComoEstoqueAlto() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 20, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        boolean encontrou = false;
        for (Object[] linha : produtoDAO.contarProdutosEstoqueMaximo()) {
            if (PRODUTO_ID == (Integer) linha[0]) {
                encontrou = true;
            }
        }
        assertFalse(encontrou);
    }
}
