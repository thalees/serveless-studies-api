package br.com.studiesMaterials.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection connection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            String dbName = "";
            String userName = "postgres";
            String password = System.getenv("PASSWORD");
            String hostname = System.getenv("HOSTNAME");
            String port = "5432";
            String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName;

            return DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
