package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    @Test
    void deveCriarLoginVazio() {
        Login login = new Login();

        assertEquals(null, login.getUsuario());
        assertEquals(null, login.getSenha());
    }

    @Test
    void deveCriarLoginCompleto() {
        Login login = new Login("admin", "12345");

        assertEquals("admin", login.getUsuario());
        assertEquals("12345", login.getSenha());
    }

    @Test
    void deveAlterarDadosDoLogin() {
        Login login = new Login();

        login.setUsuario("usuario");
        login.setSenha("senha");

        assertEquals("usuario", login.getUsuario());
        assertEquals("senha", login.getSenha());
    }

    @Test
    void naoDeveExibirSenhaNoToString() {
        Login login = new Login("admin", "12345");

        assertTrue(login.toString().contains("senha=<oculta>"));
        assertFalse(login.toString().contains("12345"));
    }
}
