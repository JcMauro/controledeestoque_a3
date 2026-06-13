package validacao;

import modelo.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class ProdutoValidadorTest {

    private final ProdutoValidador validador = new ProdutoValidador();
    private final Categoria categoria = new Categoria(1, "Bebidas", "Garrafa", "2L");

    // Usa um produto preenchido corretamente para confirmar o caminho de sucesso da validação.
    // Quando todos os campos estão dentro da regra, nenhuma mensagem de erro deve ser lançada.
    @Test
    void deveAceitarProdutoValido() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // Confere uma situação de limite em que mínimo e máximo são iguais.
    // Como a regra só impede mínimo maior que máximo, esse caso deve ser aceito.
    @Test
    void deveAceitarProdutoComMinimoIgualAoMaximo() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Agua", 3.50, 5, 5, 5, categoria));
    }

    // Valida o id do produto.
    // Um id igual a zero não representa um cadastro válido, então o validador precisa barrar.
    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Agua", 3.50, 10, 2, 20, categoria));
    }

    // Testa o nome do produto com apenas uma letra.
    // Esse caso ajuda a evitar cadastro com descrição incompleta ou digitada sem cuidado.
    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "A", 3.50, 10, 2, 20, categoria));
    }

    // Confere a regra de preço.
    // Produto com valor zero não deve ser aceito no cadastro, porque não representa um preço real.
    @Test
    void deveFalharComPrecoZero() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 0.0, 10, 2, 20, categoria));
    }

    // Valida a quantidade atual do estoque.
    // Como não faz sentido iniciar um produto com estoque negativo, o teste espera erro.
    @Test
    void deveFalharComQuantidadeNegativa() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, -1, 2, 20, categoria));
    }

    // Confere o estoque mínimo informado no cadastro.
    // O mínimo precisa ser maior que zero para que os relatórios de estoque tenham uma referência válida.
    @Test
    void deveFalharComMinimoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 0, 20, categoria));
    }

    // Confere o estoque máximo informado no cadastro.
    // Um máximo igual a zero deixa a regra de controle de estoque sem utilidade, então deve falhar.
    @Test
    void deveFalharComMaximoInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 0, categoria));
    }

    // Testa uma regra importante entre mínimo e máximo.
    // O mínimo não pode ser maior que o máximo, pois isso deixaria o controle de estoque incoerente.
    @Test
    void deveFalharComMinimoMaiorQueMaximo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 30, 20, categoria));
    }

    // Garante que o produto seja sempre vinculado a uma categoria.
    // Sem categoria, o produto ficaria incompleto e também prejudicaria o relatório por categoria.
    @Test
    void deveFalharComCategoriaNula() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Agua", 3.50, 10, 2, 20, null));
    }
}
