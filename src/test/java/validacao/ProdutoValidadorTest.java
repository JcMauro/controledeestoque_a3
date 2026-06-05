package validacao;

import modelo.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class ProdutoValidadorTest {

    private final ProdutoValidador validador = new ProdutoValidador();
    private final Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

    @Test
    void deveAceitarProdutoValido() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, categoria));
    }

    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Agua", 3.50, 10, 2, 20, categoria));
    }

    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "A", 3.50, 10, 2, 20, categoria));
    }

    @Test
    void deveFalharComPrecoZero() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 0.0, 10, 2, 20, categoria));
    }

    @Test
    void deveFalharComQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, -1, 2, 20, categoria));
    }

    @Test
    void deveFalharComMinimoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 0, 20, categoria));
    }

    @Test
    void deveFalharComMaximoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 0, categoria));
    }

    @Test
    void deveFalharComMinimoMaiorQueMaximo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 30, 20, categoria));
    }

    @Test
    void deveFalharComCategoriaNula() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, null));
    }
}
