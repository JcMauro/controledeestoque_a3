package validacao;

import modelo.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class ProdutoValidadorTest {

    private final ProdutoValidador validador = new ProdutoValidador();
    private final Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

    // Cenário básico de produto válido.
    // Todos os campos principais estão preenchidos e dentro dos limites esperados.
    @Test
    void deveAceitarProdutoValido() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // O preço decimal representa melhor uma situação real de cadastro.
    // O teste mostra que o validador aceita valores quebrados, desde que sejam maiores que zero.
    @Test
    void deveAceitarProdutoComPrecoDecimal() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.99, 10, 2, 20, categoria));
    }

    // Produto recém-cadastrado pode começar sem estoque.
    // A regra permite zero, mas não permite quantidade negativa.
    @Test
    void deveAceitarProdutoComQuantidadeZero() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 0, 2, 20, categoria));
    }

    // Este teste usa mínimo e máximo iguais para validar o limite da regra.
    // Como o mínimo não passa do máximo, o cadastro continua sendo válido.
    @Test
    void deveAceitarProdutoComMinimoIgualAoMaximo() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 5, 5, 5, categoria));
    }

    // O id zero é usado como exemplo de identificador inválido.
    // O cadastro de produto deve exigir um id positivo.
    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // Nome muito curto deixa o produto sem uma identificação adequada.
    // Por isso o teste espera que o validador lance uma mensagem de erro.
    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "A", 3.50, 10, 2, 20, categoria));
    }

    // Aqui o nome nem é enviado para a validação.
    // O teste garante que o produto não siga sem uma descrição.
    @Test
    void deveFalharComNomeNulo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, null, 3.50, 10, 2, 20, categoria));
    }

    // Preço zero é considerado inválido para o cadastro.
    // Esse teste evita que um produto seja salvo sem valor de venda definido.
    @Test
    void deveFalharComPrecoZero() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 0.0, 10, 2, 20, categoria));
    }

    // Estoque negativo não deve existir no cadastro inicial.
    // O teste cobre esse erro antes que o dado chegue ao banco.
    @Test
    void deveFalharComQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, -1, 2, 20, categoria));
    }

    // O estoque mínimo é usado como referência para alertas e relatórios.
    // Se ele for zero, a regra de controle perde sentido e deve falhar.
    @Test
    void deveFalharComMinimoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 0, 20, categoria));
    }

    // O máximo também precisa ser maior que zero.
    // Sem esse limite, não dá para controlar excesso de estoque.
    @Test
    void deveFalharComMaximoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 0, categoria));
    }

    // Este cenário força uma regra incoerente: mínimo maior que máximo.
    // O validador precisa impedir essa configuração.
    @Test
    void deveFalharComMinimoMaiorQueMaximo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 30, 20, categoria));
    }

    // Produto sem categoria fica incompleto para o sistema.
    // A categoria também é usada nos relatórios, então ela precisa ser obrigatória.
    @Test
    void deveFalharComCategoriaNula() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, null));
    }
}
