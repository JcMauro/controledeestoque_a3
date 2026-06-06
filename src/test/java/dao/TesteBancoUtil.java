package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.BooleanSupplier;

class TesteBancoUtil {

    private TesteBancoUtil() {
    }

    static Connection getConexao() throws SQLException {
        return new ConexaoDAO().getConexao();
    }

    static void executar(String sql, Object... valores) throws SQLException {
        try (Connection conn = getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) {
                stmt.setObject(i + 1, valores[i]);
            }
            stmt.executeUpdate();
        }
    }

    static boolean executarSemExibirErro(BooleanSupplier acao) {
        PrintStream erroOriginal = System.err;
        PrintStream saidaOriginal = System.out;
        try {
            System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            System.setOut(new PrintStream(OutputStream.nullOutputStream()));
            return acao.getAsBoolean();
        } finally {
            System.setErr(erroOriginal);
            System.setOut(saidaOriginal);
        }
    }
}
