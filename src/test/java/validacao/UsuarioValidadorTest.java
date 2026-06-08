package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class UsuarioValidadorTest {

    private final UsuarioValidador validador = new UsuarioValidador();

    // Testa se um usuário com dados válidos é aceito.
    @Test
    void deveAceitarUsuarioValido() {
        assertDoesNotThrow(() -> validador.validarCadastro("julio", "julio@email.com", "12345"));
    }

    // Testa se o cadastro falha quando o usuário é muito curto.
    @Test
    void deveFalharComUsuarioCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("j", "julio@email.com", "12345"));
    }

    // Testa se o cadastro falha quando o e-mail está vazio.
    @Test
    void deveFalharComEmailVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "", "12345"));
    }

    // Testa se o cadastro falha quando o e-mail é inválido.
    @Test
    void deveFalharComEmailInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "email", "12345"));
    }

    // Testa se o cadastro falha quando a senha está vazia.
    @Test
    void deveFalharComSenhaVazia() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "julio@email.com", ""));
    }

    // Testa se o cadastro falha quando a senha é muito curta.
    @Test
    void deveFalharComSenhaCurta() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro("julio", "julio@email.com", "1234"));
    }
}
