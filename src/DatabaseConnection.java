import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Строка подключения именно для PostgreSQL!
    private static final String URL = "jdbc:postgresql://localhost:5432/animal_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // Ваш пароль от PostgreSQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}