package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoTest {

    @Test
    void deveCriarProdutoComConstrutorPadrao() {
        Produto produto = new Produto();

        assertEquals(0, produto.getId());
        assertEquals("", produto.getNome());
        assertEquals(0.0, produto.getPreco());
        assertEquals(0, produto.getQuantidade());
        assertEquals(0, produto.getMin());
        assertEquals(0, produto.getMax());
        assertEquals("", produto.getUnidade());
        assertEquals(0, produto.getCategoriaId());
    }

    @Test
    void deveCriarProdutoComConstrutorCompleto() {
        Categoria categoria = new Categoria(3, "Limpeza", "Unidade", "500ml");
        Produto produto = new Produto(10, "Detergente", 2.50, 8, 2, 20, "un", categoria);

        assertEquals(10, produto.getId());
        assertEquals("Detergente", produto.getNome());
        assertEquals(2.50, produto.getPreco());
        assertEquals(8, produto.getQuantidade());
        assertEquals(2, produto.getMin());
        assertEquals(20, produto.getMax());
        assertEquals("un", produto.getUnidade());
        assertEquals(3, produto.getCategoriaId());
    }

    @Test
    void deveAlterarDadosDoProduto() {
        Categoria categoria = new Categoria(4, "Higiene", "Caixa", "12un");
        Produto produto = new Produto();

        produto.setId(11);
        produto.setNome("Sabonete");
        produto.setPreco(3.75);
        produto.setQuantidade(15);
        produto.setMin(5);
        produto.setMax(30);
        produto.setUnidade("un");
        produto.setCategoria(categoria);

        assertEquals(11, produto.getId());
        assertEquals("Sabonete", produto.getNome());
        assertEquals(3.75, produto.getPreco());
        assertEquals(15, produto.getQuantidade());
        assertEquals(5, produto.getMin());
        assertEquals(30, produto.getMax());
        assertEquals("un", produto.getUnidade());
        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    void deveCalcularValorTotalDoProduto() {
        Produto produto = new Produto();
        produto.setPreco(10.50);
        produto.setQuantidade(4);

        assertEquals(42.0, produto.getValorTotal());
    }

    @Test
    void deveRetornarTextoDoProduto() {
        Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");
        Produto produto = new Produto(5, "Agua", 4.0, 10, 2, 30, "un", categoria);

        assertTrue(produto.toString().contains("id=5"));
        assertTrue(produto.toString().contains("nome='Agua'"));
        assertTrue(produto.toString().contains("categoria=Bebidas"));
    }
}
