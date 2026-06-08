package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioTest {

    // Testa a criação de usuário usado no login.
    @Test
    void deveCriarUsuarioParaLogin() {
        Usuario usuario = new Usuario("admin", "12345");

        assertEquals(0, usuario.getId_cadastro());
        assertEquals("admin", usuario.getNome());
        assertEquals("", usuario.getEmail());
        assertEquals("12345", usuario.getSenha());
    }

    // Testa a criação de usuário usado no cadastro.
    @Test
    void deveCriarUsuarioParaCadastro() {
        Usuario usuario = new Usuario("julio", "julio@email.com", "12345");

        assertEquals("julio", usuario.getNome());
        assertEquals("julio@email.com", usuario.getEmail());
        assertEquals("12345", usuario.getSenha());
    }

    // Testa a criação de usuário com todos os dados preenchidos.
    @Test
    void deveCriarUsuarioCompleto() {
        Usuario usuario = new Usuario(1, "julio", "julio@email.com", "12345");

        assertEquals(1, usuario.getId_cadastro());
        assertEquals("julio", usuario.getNome());
        assertEquals("julio@email.com", usuario.getEmail());
        assertEquals("12345", usuario.getSenha());
    }

    // Testa se os dados do usuário podem ser alterados pelos setters.
    @Test
    void deveAlterarDadosDoUsuario() {
        Usuario usuario = new Usuario("usuario", "senha");

        usuario.setId_cadastro(2);
        usuario.setNome("novo");
        usuario.setEmail("novo@email.com");
        usuario.setSenha("54321");

        assertEquals(2, usuario.getId_cadastro());
        assertEquals("novo", usuario.getNome());
        assertEquals("novo@email.com", usuario.getEmail());
        assertEquals("54321", usuario.getSenha());
    }

    // Testa se a senha não aparece no texto do objeto.
    @Test
    void naoDeveExibirSenhaNoToString() {
        Usuario usuario = new Usuario(1, "julio", "julio@email.com", "12345");

        assertTrue(usuario.toString().contains("senha=<oculto>"));
        assertFalse(usuario.toString().contains("12345"));
    }
}
