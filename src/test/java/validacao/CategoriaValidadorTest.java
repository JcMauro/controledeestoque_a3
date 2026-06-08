package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class CategoriaValidadorTest {

    private final CategoriaValidador validador = new CategoriaValidador();

    // Testa se uma categoria com dados válidos é aceita.
    @Test
    void deveAceitarCategoriaValida() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Bebidas", "Garrafa", "2L"));
    }

    // Testa se o cadastro falha quando o id é inválido.
    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Bebidas", "Garrafa", "2L"));
    }

    // Testa se o cadastro falha quando o nome é muito curto.
    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "B", "Garrafa", "2L"));
    }

    // Testa se o cadastro falha quando a embalagem está vazia.
    @Test
    void deveFalharComEmbalagemVazia() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "", "2L"));
    }

    // Testa se o cadastro falha quando o tamanho está vazio.
    @Test
    void deveFalharComTamanhoVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "Garrafa", ""));
    }
}
