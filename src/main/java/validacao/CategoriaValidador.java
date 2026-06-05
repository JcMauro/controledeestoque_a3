package validacao;

import visao.Mensagem;

/**
 * Classe com validacoes simples para categorias.
 */
public class CategoriaValidador {

    public void validarCadastro(int id, String nome, String embalagem, String tamanho) throws Mensagem {
        if (id <= 0) {
            throw new Mensagem("ID deve ser maior que zero.");
        }
        if (nome == null || nome.trim().length() < 2) {
            throw new Mensagem("O nome deve conter ao menos 2 caracteres.");
        }
        if (embalagem == null || embalagem.trim().isEmpty()) {
            throw new Mensagem("A embalagem deve ser informada.");
        }
        if (tamanho == null || tamanho.trim().isEmpty()) {
            throw new Mensagem("O tamanho deve ser informado.");
        }
    }
}
