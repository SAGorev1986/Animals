package db;

import settings.DbSettingsReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlConnectionManager implements IDBConnectionManager {

    private static volatile SqlConnectionManager instance;

    // Храним не одно Connection
    private final HikariDataSource dataSource;

    // Приватный конструктор, чтобы запретить создание объектов через new извне
    private SqlConnectionManager(DbSettingsReader settings) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(settings.getUrl());
        config.setUsername(settings.getUser());
        config.setPassword(settings.getPassword());

        // Настройки пула (подбираются под нагрузку)
        config.setMaximumPoolSize(10); // Максимум 10 одновременных соединений
        config.setMinimumIdle(2);      // Минимум 2 соединения в простое
        config.setConnectionTimeout(30000);

        this.dataSource = new HikariDataSource(config);
    }

    // Метод для получения единственного экземпляра (Thread-safe Singleton)
    public static SqlConnectionManager getInstance(DbSettingsReader settings) {
        if (instance == null) {
            synchronized (SqlConnectionManager.class) {
                if (instance == null) {
                    instance = new SqlConnectionManager(settings);
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // Пул выдает свободное соединение из пула или ставит поток в очередь ожидания
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close(); // Корректно закрывает ВСЕ физические соединения в пуле
        }
    }
}