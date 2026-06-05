package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class EstoqueValidadorTest {

    private final EstoqueValidador validador = new EstoqueValidador();

    @Test
    void deveCalcularAdicaoValida() throws Mensagem {
        int novoEstoque = validador.calcularAdicao(10, 5, 20);

        assertEquals(15, novoEstoque);
    }

    @Test
    void deveFalharAoAdicionarQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 0, 20));
    }

    @Test
    void deveFalharAoAdicionarQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, -1, 20));
    }

    @Test
    void deveFalharAoUltrapassarEstoqueMaximo() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 15, 20));
    }

    @Test
    void deveCalcularRemocaoValida() throws Mensagem {
        int novoEstoque = validador.calcularRemocao(10, 5, 2);

        assertEquals(5, novoEstoque);
    }

    @Test
    void deveFalharAoRemoverQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 0, 2));
    }

    @Test
    void deveFalharAoRemoverQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, -1, 2));
    }

    @Test
    void deveFalharAoFicarAbaixoDoEstoqueMinimo() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 9, 2));
    }
}
