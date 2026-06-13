package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class EstoqueValidadorTest {

    private final EstoqueValidador validador = new EstoqueValidador();

    // Este teste representa uma entrada normal de estoque.
    // Ele confirma se a soma entre estoque atual e quantidade informada gera o novo valor esperado.
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

    // Quantidade zero não movimenta o estoque de verdade.
    // Por isso o validador deve recusar a operação antes de alterar qualquer valor.
    @Test
    void deveFalharAoAdicionarQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 0, 20));
    }

    // Entrada com quantidade negativa seria uma operação sem sentido para adicionar estoque.
    // O teste garante que esse tipo de dado inválido seja bloqueado.
    @Test
    void deveFalharAoAdicionarQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, -1, 20));
    }

    // Aqui a entrada ultrapassaria o estoque máximo permitido.
    // Esse limite evita que o produto fique registrado acima da capacidade definida.
    @Test
    void deveFalharAoUltrapassarEstoqueMaximo() {
        assertThrows(Mensagem.class, () -> validador.calcularAdicao(10, 15, 20));
    }

    // Este é o caminho comum de retirada de estoque.
    // A quantidade removida deve ser descontada e o resultado precisa bater com o valor esperado.
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

    // Remover zero unidades não representa uma movimentação real.
    // O validador deve tratar isso como entrada inválida.
    @Test
    void deveFalharAoRemoverQuantidadeZero() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 0, 2));
    }

    // Saída negativa também não faz sentido dentro da regra de estoque.
    // O teste confirma que a validação impede essa operação.
    @Test
    void deveFalharAoRemoverQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, -1, 2));
    }

    // Neste caso a retirada deixaria o estoque abaixo do mínimo.
    // A regra precisa bloquear para manter a quantidade dentro do limite definido.
    @Test
    void deveFalharAoFicarAbaixoDoEstoqueMinimo() {
        assertThrows(Mensagem.class, () -> validador.calcularRemocao(10, 9, 2));
    }
}
