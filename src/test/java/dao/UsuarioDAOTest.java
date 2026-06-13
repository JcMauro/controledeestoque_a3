package dao;

import modelo.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    // Este teste verifica o fluxo básico de usuário no banco.
    // Depois de cadastrar, ele confirma se o usuário aparece nas buscas e nas listagens.
    @Test
    void deveInserirBuscarEListarUsuario() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));
        assertNotNull(usuarioDAO.buscarUsuario(USUARIO));
        assertNotNull(usuarioDAO.buscarEmail(EMAIL));
        assertTrue(usuarioDAO.getAllusuarios().contains(USUARIO));
        assertTrue(usuarioDAO.getLista().stream().anyMatch(u -> USUARIO.equals(u.getNome())));
    }

    // Além de buscar o usuário, este teste confere os dados retornados.
    // Assim dá para saber se nome, e-mail e senha vieram do banco corretamente.
    @Test
    void deveBuscarUsuarioComDadosCorretos() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));

        Usuario encontrado = usuarioDAO.buscarUsuario(USUARIO);
        assertNotNull(encontrado);
        assertEquals(USUARIO, encontrado.getNome());
        assertEquals(EMAIL, encontrado.getEmail());
        assertEquals(SENHA, encontrado.getSenha());
    }

    // Este cenário valida a consulta pelo e-mail.
    // O registro encontrado precisa ser o mesmo usuário cadastrado no começo do teste.
    @Test
    void deveBuscarEmailComDadosCorretos() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));

        Usuario encontrado = usuarioDAO.buscarEmail(EMAIL);
        assertNotNull(encontrado);
        assertEquals(USUARIO, encontrado.getNome());
        assertEquals(EMAIL, encontrado.getEmail());
        assertEquals(SENHA, encontrado.getSenha());
    }

    // Pesquisa um usuário que não existe na base de teste.
    // O retorno nulo mostra que o DAO não criou nenhum objeto indevido.
    @Test
    void deveRetornarNullParaUsuarioInexistente() {
        assertNull(usuarioDAO.buscarUsuario("usuario_inexistente"));
    }

    // Faz a consulta usando um e-mail que não foi cadastrado.
    // O teste confirma que a busca por e-mail também trata corretamente a ausência de dados.
    @Test
    void deveRetornarNullParaEmailInexistente() {
        assertNull(usuarioDAO.buscarEmail("email_inexistente@email.com"));
    }

    // Tenta gravar o mesmo usuário mais de uma vez.
    // A primeira inserção deve funcionar, mas a repetição precisa ser recusada pelo banco.
    @Test
    void deveRecusarUsuarioDuplicado() {
        Usuario usuario = new Usuario(USUARIO, EMAIL, SENHA);

        assertTrue(usuarioDAO.inserirUsuario(usuario));
        assertFalse(TesteBancoUtil.executarSemExibirErro(() -> usuarioDAO.inserirUsuario(usuario)));
    }
}
