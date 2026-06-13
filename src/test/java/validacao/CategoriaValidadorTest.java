package validacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import visao.Mensagem;

class CategoriaValidadorTest {

    private final CategoriaValidador validador = new CategoriaValidador();

    // Este é o cenário positivo da categoria.
    // Com id, nome, embalagem e tamanho preenchidos corretamente, o cadastro deve passar sem erro.
    @Test
    void deveAceitarCategoriaValida() {
        assertDoesNotThrow(() -> validador.validarCadastro(1, "Bebidas", "Garrafa", "2L"));
    }

    // Aqui é conferido o id da categoria.
    // Como o sistema trabalha com id maior que zero, o valor 0 precisa ser recusado.
    @Test
    void deveFalharComIdInvalido() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(0, "Bebidas", "Garrafa", "2L"));
    }

    // O nome com apenas uma letra é usado para representar um cadastro mal preenchido.
    // A validação deve impedir esse tipo de informação incompleta.
    @Test
    void deveFalharComNomeCurto() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "B", "Garrafa", "2L"));
    }

    // Verifica o cadastro com o nome vazio.
    // A categoria precisa de um nome para aparecer corretamente nas telas e relatórios.
    @Test
    void deveFalharComNomeVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "", "Garrafa", "2L"));
    }

    // Verifica o cadastro quando o nome não foi informado.
    // O validador deve barrar esse caso antes de tentar salvar no banco.
    @Test
    void deveFalharComNomeNulo() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, null, "Garrafa", "2L"));
    }

    // A embalagem vazia também precisa ser barrada.
    // Sem essa informação, a categoria fica pouco clara para o usuário do sistema.
    @Test
    void deveFalharComEmbalagemVazia() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "", "2L"));
    }

    // Verifica o cadastro quando a embalagem está nula.
    // Esse campo ajuda a descrever a categoria, então não pode seguir sem valor.
    @Test
    void deveFalharComEmbalagemNula() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", null, "2L"));
    }

    // Por fim, este teste verifica o tamanho vazio.
    // Ele completa a validação dos campos obrigatórios da categoria.
    @Test
    void deveFalharComTamanhoVazio() {
        assertThrows(Mensagem.class, () -> validador.validarCadastro(1, "Bebidas", "Garrafa", ""));
    }
}
