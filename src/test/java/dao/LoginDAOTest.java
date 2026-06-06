package dao;

import modelo.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginDAOTest {

    private static final String USUARIO = "teste_integracao_login";
    private static final String EMAIL = "teste_integracao_login@email.com";
    private static final String SENHA = "12345";

    private final LoginDAO loginDAO = new LoginDAO();

    @BeforeEach
    void preparar() throws Exception {
        limpar();
        TesteBancoUtil.executar(
                "INSERT INTO tb_usuarios (usuario, username, email, senha) VALUES (?, ?, ?, ?)",
                USUARIO, USUARIO, EMAIL, SENHA
        );
    }

    @AfterEach
    void limpar() throws Exception {
        TesteBancoUtil.executar("DELETE FROM tb_usuarios WHERE usuario = ? OR username = ? OR email = ?", USUARIO, USUARIO, EMAIL);
    }

    @Test
    void deveValidarLoginCorreto() {
        assertTrue(loginDAO.validarLogin(USUARIO, SENHA));
    }

    @Test
    void deveRecusarSenhaIncorreta() {
        assertFalse(loginDAO.validarLogin(USUARIO, "senha_errada"));
    }

    @Test
    void deveRecusarUsuarioInexistente() {
        assertFalse(loginDAO.validarLogin("usuario_inexistente", SENHA));
    }

    @Test
    void deveBuscarUsuarioExistente() {
        Usuario usuario = loginDAO.buscarUsuario(USUARIO);

        assertNotNull(usuario);
    }

    @Test
    void deveRetornarNullAoBuscarUsuarioInexistente() {
        assertNull(loginDAO.buscarUsuario("usuario_inexistente"));
    }
}
