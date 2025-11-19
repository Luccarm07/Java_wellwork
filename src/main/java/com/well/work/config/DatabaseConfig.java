package com.well.work.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuração de conexão com banco de dados Oracle
 * Padrão: JDBC puro (sem JPA/Hibernate)
 */
public class DatabaseConfig {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm561792";
    private static final String PASSWORD = "290806";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("✅ Driver Oracle carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Driver Oracle não encontrado", e);
        }
    }

    /**
     * Obtém uma nova conexão com o banco de dados Oracle
     * @return Connection ativa
     * @throws RuntimeException se houver erro na conexão
     */
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexão com Oracle estabelecida!");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro na conexão com Oracle: " + e.getMessage(), e);
        }
    }

    /**
     * Fecha a conexão com o banco de dados
     * @param conn Conexão a ser fechada
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Conexão com Oracle fechada!");
            } catch (SQLException e) {
                System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
