package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class UsuarioValidadorTest {

    private final UsuarioValidador validador = new UsuarioValidador();

    @Test
    void deveAceitarUsuarioValido() {
        assertDoesNotThrow(() -> validador.validarCadastro("julio", "julio@email.com", "12345"));
    }

    @Test
    void deveFalharComUsuarioCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("j", "julio@email.com", "12345"));
    }

    @Test
    void deveFalharComEmailVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "", "12345"));
    }

    @Test
    void deveFalharComEmailInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "email", "12345"));
    }

    @Test
    void deveFalharComSenhaVazia() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "julio@email.com", ""));
    }

    @Test
    void deveFalharComSenhaCurta() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "julio@email.com", "1234"));
    }
}
