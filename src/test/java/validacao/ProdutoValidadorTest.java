package validacao;

import modelo.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class ProdutoValidadorTest {

    private final ProdutoValidador validador = new ProdutoValidador();
    private final Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

    // Testa se um produto com dados válidos é aceito.
    @Test
    void deveAceitarProdutoValido() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // Testa se o cadastro falha quando o id é inválido.
    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // Testa se o cadastro falha quando o nome é muito curto.
    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "A", 3.50, 10, 2, 20, categoria));
    }

    // Testa se o cadastro falha quando o preço é zero.
    @Test
    void deveFalharComPrecoZero() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 0.0, 10, 2, 20, categoria));
    }

    // Testa se o cadastro falha quando a quantidade é negativa.
    @Test
    void deveFalharComQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, -1, 2, 20, categoria));
    }

    // Testa se o cadastro falha quando o estoque mínimo é inválido.
    @Test
    void deveFalharComMinimoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 0, 20, categoria));
    }

    // Testa se o cadastro falha quando o estoque máximo é inválido.
    @Test
    void deveFalharComMaximoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 0, categoria));
    }

    // Testa se o cadastro falha quando o mínimo é maior que o máximo.
    @Test
    void deveFalharComMinimoMaiorQueMaximo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 30, 20, categoria));
    }

    // Testa se o cadastro falha quando a categoria não é informada.
    @Test
    void deveFalharComCategoriaNula() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, null));
    }
}
