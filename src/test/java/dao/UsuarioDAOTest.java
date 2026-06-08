package dao;

import modelo.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioDAOTest {

    private static final String USUARIO = "teste_integracao_usuario";
    private static final String EMAIL = "teste_integracao_usuario@email.com";
    private static final String SENHA = "12345";

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @BeforeEach
    void preparar() throws Exception {
        limpar();
    }

    @AfterEach
    void limpar() throws Exception {
        TesteBancoUtil.executar("DELETE FROM tb_usuarios WHERE usuario = ? OR username = ? OR email = ?", USUARIO, USUARIO, EMAIL);
    }

    // Testa o cadastro, busca e listagem de usuário no banco.
    @Test
    void deveInserirBuscarEListarUsuario() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));
        assertNotNull(usuarioDAO.buscarUsuario(USUARIO));
        assertNotNull(usuarioDAO.buscarEmail(EMAIL));
        assertTrue(usuarioDAO.getAllusuarios().contains(USUARIO));
        assertTrue(usuarioDAO.getLista().stream().anyMatch(u -> USUARIO.equals(u.getNome())));
    }

    // Testa se a busca retorna nulo para usuário inexistente.
    @Test
    void deveRetornarNullParaUsuarioInexistente() {
        assertNull(usuarioDAO.buscarUsuario("usuario_inexistente"));
    }

    // Testa se a busca retorna nulo para e-mail inexistente.
    @Test
    void deveRetornarNullParaEmailInexistente() {
        assertNull(usuarioDAO.buscarEmail("email_inexistente@email.com"));
    }

    // Testa se o banco recusa o cadastro de usuário duplicado.
    @Test
    void deveRecusarUsuarioDuplicado() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));
        assertFalse(TesteBancoUtil.executarSemExibirErro(() -> usuarioDAO.inserirUsuario(usuario)));
    }
}
