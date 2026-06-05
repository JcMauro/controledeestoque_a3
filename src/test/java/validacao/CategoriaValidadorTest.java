package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class CategoriaValidadorTest {

    private final CategoriaValidador validador = new CategoriaValidador();

    @Test
    void deveAceitarCategoriaValida() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Bebidas", "Garrafa", "2L"));
    }

    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Bebidas", "Garrafa", "2L"));
    }

    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "B", "Garrafa", "2L"));
    }

    @Test
    void deveFalharComEmbalagemVazia() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "", "2L"));
    }

    @Test
    void deveFalharComTamanhoVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "Garrafa", ""));
    }
}
