package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConexaoDAOTest {

    // Testa se o sistema consegue abrir conexão com o banco.
    @Test
    void deveConectarNoBancoDeDados() throws SQLException {
        try (Connection conn = new ConexaoDAO().getConexao()) {
            assertNotNull(conn);
            assertTrue(conn.isValid(2));
        }
    }

    // Testa se as tabelas principais existem no banco.
    @Test
    void deveEncontrarTabelasPrincipais() throws SQLException {
        try (Connection conn = new ConexaoDAO().getConexao()) {
            assertTrue(existeTabela(conn, "tb_usuarios"));
            assertTrue(existeTabela(conn, "tb_categoria"));
            assertTrue(existeTabela(conn, "tb_produto"));
        }
    }

    // Testa se colunas importantes existem nas tabelas principais.
    @Test
    void deveEncontrarColunasPrincipais() throws SQLException {
        try (Connection conn = new ConexaoDAO().getConexao()) {
            assertTrue(existeColuna(conn, "tb_usuarios", "usuario"));
            assertTrue(existeColuna(conn, "tb_categoria", "embalagem"));
            assertTrue(existeColuna(conn, "tb_produto", "categoria_id"));
        }
    }

    private boolean existeTabela(Connection conn, String tabela) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tabela, new String[]{"TABLE"})) {
            return rs.next();
        }
    }

    private boolean existeColuna(Connection conn, String tabela, String coluna) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tabela, coluna)) {
            return rs.next();
        }
    }
}
