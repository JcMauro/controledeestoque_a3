package validacao;

import visao.Mensagem;

/**
 * Classe com validacoes simples para movimentacao de estoque.
 */
public class EstoqueValidador {

    public int calcularAdicao(int estoqueAtual, int quantidade, int estoqueMaximo) throws Mensagem {
        validarQuantidade(quantidade);

        int novoEstoque = estoqueAtual + quantidade;
        if (novoEstoque > estoqueMaximo) {
            throw new Mensagem("Estoque nao pode ultrapassar o limite maximo.");
        }
        return novoEstoque;
    }

    public int calcularRemocao(int estoqueAtual, int quantidade, int estoqueMinimo) throws Mensagem {
        validarQuantidade(quantidade);

        int novoEstoque = estoqueAtual - quantidade;
        if (novoEstoque < estoqueMinimo) {
            throw new Mensagem("Estoque nao pode ser menor que o minimo permitido.");
        }
        return novoEstoque;
    }

    private void validarQuantidade(int quantidade) throws Mensagem {
        if (quantidade <= 0) {
            throw new Mensagem("Informe uma quantidade maior que zero.");
        }
    }
}
