package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class EstoqueValidadorTest {

    private final EstoqueValidador validador = new EstoqueValidador();

    // Testa se uma entrada válida aumenta o estoque corretamente.
    @Test
    void deveCalcularAdicaoValida() throws Mensagem {
        int novoEstoque = validador.calcularAdicao(10, 5, 20);

        assertEquals(15, novoEstoque);
    }

    // Verifica a entrada de estoque chegando exatamente no limite máximo.
    // Esse caso deve ser aceito, porque a regra só bloqueia quando passa do máximo.
    @Test
    void devePermitirAdicaoAteEstoqueMaximo() throws Mensagem {
        int novoEstoque = validador.calcularAdicao(10, 10, 20);

        assertEquals(20, novoEstoque);
    }

    // Testa se a entrada falha quando a quantidade é zero.
    @Test
    void deveFalharAoAdicionarQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 0, 20));
    }

    // Testa se a entrada falha quando a quantidade é negativa.
    @Test
    void deveFalharAoAdicionarQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, -1, 20));
    }

    // Testa se a entrada falha ao ultrapassar o estoque máximo.
    @Test
    void deveFalharAoUltrapassarEstoqueMaximo() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 15, 20));
    }

    // Testa se uma saída válida reduz o estoque corretamente.
    @Test
    void deveCalcularRemocaoValida() throws Mensagem {
        int novoEstoque = validador.calcularRemocao(10, 5, 2);

        assertEquals(5, novoEstoque);
    }

    // Verifica a saída de estoque chegando exatamente no limite mínimo.
    // Esse cenário ainda é válido, pois o estoque não ficou abaixo do permitido.
    @Test
    void devePermitirRemocaoAteEstoqueMinimo() throws Mensagem {
        int novoEstoque = validador.calcularRemocao(10, 8, 2);

        assertEquals(2, novoEstoque);
    }

    // Testa se a saída falha quando a quantidade é zero.
    @Test
    void deveFalharAoRemoverQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 0, 2));
    }

    // Testa se a saída falha quando a quantidade é negativa.
    @Test
    void deveFalharAoRemoverQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, -1, 2));
    }

    // Testa se a saída falha ao ficar abaixo do estoque mínimo.
    @Test
    void deveFalharAoFicarAbaixoDoEstoqueMinimo() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 9, 2));
    }
}
