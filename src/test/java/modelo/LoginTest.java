package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    // Testa se o login vazio inicia sem usuário e senha.
    @Test
    void deveCriarLoginVazio() {
        Login login = new Login();

        assertEquals(null, login.getUsuario());
        assertEquals(null, login.getSenha());
    }

    // Testa se o login recebe usuário e senha pelo construtor.
    @Test
    void deveCriarLoginCompleto() {
        Login login = new Login("admin", "12345");

        assertEquals("admin", login.getUsuario());
        assertEquals("12345", login.getSenha());
    }

    // Testa se usuário e senha podem ser alterados pelos setters.
    @Test
    void deveAlterarDadosDoLogin() {
        Login login = new Login();

        login.setUsuario("usuario");
        login.setSenha("senha");

        assertEquals("usuario", login.getUsuario());
        assertEquals("senha", login.getSenha());
    }

    // Testa se a senha não aparece no texto do objeto.
    @Test
    void naoDeveExibirSenhaNoToString() {
        Login login = new Login("admin", "12345");

        assertTrue(login.toString().contains("senha=<oculta>"));
        assertFalse(login.toString().contains("12345"));
    }
}
