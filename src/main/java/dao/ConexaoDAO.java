/**
 * Classe responsavel por estabelecer a conexao com o banco de dados MySQL.
 *
 * Banco de dados: db_controledeestoque
 * Usuario padrao: root
 * Senha padrao: Unisul@1520
 *
 * Os dados tambem podem ser alterados pelas variaveis de ambiente:
 * DB_URL, DB_USER e DB_PASSWORD.
 *
 * @author luiz
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private final String URL = getValorAmbiente("DB_URL", "jdbc:mysql://localhost:3306/db_controledeestoque");
    private final String USER = getValorAmbiente("DB_USER", "root");
    private final String PASSWORD = getValorAmbiente("DB_PASSWORD", "Unisul@1520");

    /**
     * Estabelece e retorna a conexao com o banco de dados.
     *
     * @return Objeto {@link Connection} se a conexao for bem-sucedida, ou
     * {@code null} em caso de falha
     */
    public Connection getConexao() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexao estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC nao encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Busca uma variavel de ambiente. Caso ela nao exista, usa o valor padrao.
     *
     * @param nome Nome da variavel de ambiente
     * @param valorPadrao Valor utilizado quando a variavel nao foi configurada
     * @return Valor configurado ou valor padrao
     */
    private String getValorAmbiente(String nome, String valorPadrao) {
        String valor = System.getenv(nome);
        if (valor == null || valor.trim().isEmpty()) {
            return valorPadrao;
        }
        return valor;
    }
}
