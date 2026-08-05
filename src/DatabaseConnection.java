import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // ВАЖНО: Укажите здесь свой пароль от MySQL вместо "ваш_пароль"
    private static final String URL = "jdbc:mysql://localhost:3306/animal_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}