package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriaTest {

    // Testa se a categoria inicia com os valores padrão.
    @Test
    void deveCriarCategoriaComConstrutorPadrao() {
        Categoria categoria = new Categoria();

        assertEquals(0, categoria.getId());
        assertEquals("", categoria.getNome());
        assertEquals("", categoria.getEmbalagem());
        assertEquals("", categoria.getTamanho());
    }

    // Testa se a categoria recebe corretamente todos os dados no construtor.
    @Test
    void deveCriarCategoriaComConstrutorCompleto() {
        Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

        assertEquals(1, categoria.getId());
        assertEquals("Bebidas", categoria.getNome());
        assertEquals("Garrafa", categoria.getEmbalagem());
        assertEquals("2L", categoria.getTamanho());
    }

    // Testa se os dados da categoria podem ser alterados pelos setters.
    @Test
    void deveAlterarDadosDaCategoria() {
        Categoria categoria = new Categoria();

        categoria.setId(2);
        categoria.setNome("Alimentos");
        categoria.setEmbalagem("Pacote");
        categoria.setTamanho("1kg");

        assertEquals(2, categoria.getId());
        assertEquals("Alimentos", categoria.getNome());
        assertEquals("Pacote", categoria.getEmbalagem());
        assertEquals("1kg", categoria.getTamanho());
    }

    // Testa o texto exibido quando a categoria é convertida para String.
    @Test
    void deveRetornarTextoFormatadoDaCategoria() {
        Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

        assertEquals("Bebidas - Garrafa - 2L", categoria.toString());
    }
}
