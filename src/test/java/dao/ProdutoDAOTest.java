package dao;

import modelo.Categoria;
import modelo.Produto;
import java.sql.ResultSet;
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

    @Test
    void deveRecusarProdutoDuplicado() {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));
        assertFalse(TesteBancoUtil.executarSemExibirErro(() -> produtoDAO.inserirProduto(produto)));
    }

    @Test
    void deveRetornarNullParaProdutoInexistente() {
        assertNull(produtoDAO.buscarProduto(999999));
    }

    @Test
    void deveRetornarNullParaNomeInexistente() {
        assertNull(produtoDAO.buscarProdutoPorNome("produto_inexistente"));
    }

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

    @Test
    void deveListarProdutosAgrupadosPorCategoriaNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ID, PRODUTO_NOME, 10.0, 5, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        try (ResultSet resultado = produtoDAO.contarProdutosPorCategoria()) {
            boolean encontrou = false;
            while (resultado.next()) {
                if ("Categoria Integracao".equals(resultado.getString("categoria"))
                        && resultado.getInt("quantidade") >= 1) {
                    encontrou = true;
                }
            }
            assertTrue(encontrou);
        }
    }

    @Test
    void deveListarProdutosAbaixoDoEstoqueMinimoNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_BAIXO_ID, PRODUTO_BAIXO_NOME, 10.0, 1, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        try (ResultSet resultado = produtoDAO.contarProdutosEstoqueMinimo()) {
            boolean encontrou = false;
            while (resultado.next()) {
                if (PRODUTO_BAIXO_ID == resultado.getInt("codigo")
                        && PRODUTO_BAIXO_NOME.equals(resultado.getString("produto"))) {
                    encontrou = true;
                }
            }
            assertTrue(encontrou);
        }
    }

    @Test
    void deveListarProdutosAcimaDoEstoqueMaximoNoRelatorio() throws Exception {
        Categoria categoria = categoriaDAO.buscarCategoria(CATEGORIA_ID);
        Produto produto = new Produto(PRODUTO_ALTO_ID, PRODUTO_ALTO_NOME, 10.0, 25, 2, 20, "un", categoria);

        assertTrue(produtoDAO.inserirProduto(produto));

        try (ResultSet resultado = produtoDAO.contarProdutosEstoqueMaximo()) {
            boolean encontrou = false;
            while (resultado.next()) {
                if (PRODUTO_ALTO_ID == resultado.getInt("codigo")
                        && PRODUTO_ALTO_NOME.equals(resultado.getString("produto"))) {
                    encontrou = true;
                }
            }
            assertTrue(encontrou);
        }
    }
}
