package dao;

import modelo.Categoria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoriaDAOTest {

    private static final int ID = 901001;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @BeforeEach
    void preparar() throws Exception {
        limpar();
    }

    @AfterEach
    void limpar() throws Exception {
        TesteBancoUtil.executar("DELETE FROM tb_categoria WHERE id = ?", ID);
    }

    // Aqui é testado o caminho principal da categoria no banco:
    // cadastra, consulta, lista, atualiza e depois remove o registro criado.
    @Test
    void deveInserirBuscarAtualizarListarERemoverCategoria() {
        Categoria categoria = new Categoria(ID, "Teste Integracao", "Caixa", "1kg");

        assertTrue(categoriaDAO.inserirCategoria(categoria));
        assertNotNull(categoriaDAO.buscarCategoria(ID));
        assertTrue(categoriaDAO.getLista().stream().anyMatch(c -> c.getId() == ID));
        assertTrue(categoriaDAO.getNomesCategorias().stream().anyMatch(nome -> nome.contains("Teste Integracao")));

        Categoria atualizada = new Categoria(ID, "Teste Atualizado", "Pacote", "2kg");
        assertTrue(categoriaDAO.atualizarCategoria(atualizada));
        assertEquals("Teste Atualizado", categoriaDAO.buscarCategoria(ID).getNome());

        assertTrue(categoriaDAO.removerCategoria(ID));
        assertNull(categoriaDAO.buscarCategoria(ID));
    }

    // Neste teste a mesma categoria é inserida duas vezes.
    // A segunda tentativa precisa falhar, porque o banco não deve aceitar o mesmo id duplicado.
    @Test
    void deveRecusarCategoriaDuplicada() {
        Categoria categoria = new Categoria(ID, "Teste Integracao", "Caixa", "1kg");

        assertTrue(categoriaDAO.inserirCategoria(categoria));
        assertFalse(TesteBancoUtil.executarSemExibirErro(() -> categoriaDAO.inserirCategoria(categoria)));
    }

    // Verifica uma consulta sem resultado, simulando a busca por uma categoria que não foi cadastrada.
    // O retorno esperado é nulo, indicando que o DAO tratou a ausência do registro.
    @Test
    void deveRetornarNullParaCategoriaInexistente() {
        assertNull(categoriaDAO.buscarCategoria(999999));
    }
}
