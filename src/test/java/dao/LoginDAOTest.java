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

    // Usa um usuário preparado no banco antes do teste.
    // Com usuário e senha corretos, o login deve ser considerado válido.
    @Test
    void deveValidarLoginCorreto() {
        assertTrue(loginDAO.validarLogin(USUARIO, SENHA));
    }

    // Mantém o usuário correto, mas troca a senha.
    // O objetivo é confirmar que o sistema não libera acesso com senha incorreta.
    @Test
    void deveRecusarSenhaIncorreta() {
        assertFalse(loginDAO.validarLogin(USUARIO, "senha_errada"));
    }

    // Tenta fazer login com um usuário que não foi cadastrado no banco de teste.
    // Mesmo com uma senha preenchida, o retorno correto deve ser falso.
    @Test
    void deveRecusarUsuarioInexistente() {
        assertFalse(loginDAO.validarLogin("usuario_inexistente", SENHA));
    }

    // Simula o campo de usuário vazio na tentativa de login.
    // Esse cenário precisa ser recusado, pois não existe usuário em branco no cadastro.
    @Test
    void deveRecusarUsuarioVazio() {
        assertFalse(loginDAO.validarLogin("", SENHA));
    }

    // Simula o campo de senha vazio para um usuário existente.
    // O teste garante que o DAO não aceite login sem senha.
    @Test
    void deveRecusarSenhaVazia() {
        assertFalse(loginDAO.validarLogin(USUARIO, ""));
    }

    // Busca no banco o mesmo usuário criado no preparo do teste.
    // Se retornar um objeto, significa que a consulta de login encontrou o registro.
    @Test
    void deveBuscarUsuarioExistente() {
        Usuario usuario = loginDAO.buscarUsuario(USUARIO);

        assertNotNull(usuario);
    }

    // Faz uma busca por um nome de usuário que não existe.
    // O esperado é retornar nulo, e não um usuário preenchido incorretamente.
    @Test
    void deveRetornarNullAoBuscarUsuarioInexistente() {
        assertNull(loginDAO.buscarUsuario("usuario_inexistente"));
    }
}
