package dao;

import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoDAOTest {

    private static final int CATEGORIA_ID = 901101;
    private static final int PRODUTO_ID = 902101;
    private static final String PRODUTO_NOME = "Produto Integracao Teste";

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @BeforeEach
    void preparar() throws Exception {
        limpar();
        categoriaDAO.inserirCategoria(new Categoria(CATEGORIA_ID, "Categoria Integracao", "Unidade", "1un"));
    }

    @AfterEach
    void limpar() throws Exception {
        TesteBancoUtil.executar("DELETE FROM tb_produto WHERE id = ? OR nome = ?", PRODUTO_ID, PRODUTO_NOME);
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
}
