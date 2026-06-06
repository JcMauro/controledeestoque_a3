package validacao;

import visao.Mensagem;

/**
 * Classe com validacoes simples para cadastro de usuario.
 */
public class UsuarioValidador {

    public void validarCadastro(String usuario, String email, String senha) throws Mensagem {
        if (usuario == null || usuario.trim().length() < 2) {
            throw new Mensagem("O nome de usuário deve conter ao menos 2 caracteres.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new Mensagem("O e-mail não pode estar vazio.");
        }
        if (email.trim().length() < 2 || !email.contains("@")) {
            throw new Mensagem("Informe um e-mail válido.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new Mensagem("A senha não pode estar vazia.");
        }
        if (senha.trim().length() < 5) {
            throw new Mensagem("A senha deve conter ao menos 5 caracteres.");
        }
    }
}
