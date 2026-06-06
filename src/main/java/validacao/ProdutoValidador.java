package validacao;

import modelo.Categoria;
import visao.Mensagem;

/**
 * Classe com validacoes simples para produtos.
 */
public class ProdutoValidador {

    public void validarCadastro(int id, String nome, double preco, int quantidade, int min, int max, Categoria categoria) throws Mensagem {
        if (id <= 0) {
            throw new Mensagem("ID deve ser maior que zero.");
        }
        if (nome == null || nome.trim().length() < 2) {
            throw new Mensagem("O nome deve conter ao menos 2 caracteres.");
        }
        if (preco <= 0) {
            throw new Mensagem("O preço deve ser maior que zero.");
        }
        if (quantidade < 0) {
            throw new Mensagem("Quantidade atual não pode ser negativa.");
        }
        if (min <= 0 || max <= 0) {
            throw new Mensagem("Quantidades mínima e máxima devem ser maiores que zero.");
        }
        if (min > max) {
            throw new Mensagem("Quantidade mínima não pode ser maior que a máxima.");
        }
        if (categoria == null) {
            throw new Mensagem("Selecione uma categoria.");
        }
    }
}
