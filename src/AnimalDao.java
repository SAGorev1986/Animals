import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao {

    // CREATE
    public void addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (type, name, age, weight, color) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, animal.getType());
            pstmt.setString(2, animal.getName());
            pstmt.setInt(3, animal.getAge());
            pstmt.setDouble(4, animal.getWeight());
            pstmt.setString(5, animal.getColor());
            pstmt.executeUpdate();
            System.out.println("Животное успешно сохранено в БД!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении: " + e.getMessage());
        }
    }

    // READ (все)
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                animals.add(createAnimalFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении: " + e.getMessage());
        }
        return animals;
    }

    // READ (фильтр по типу)
    public List<Animal> getAnimalsByType(String type) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    animals.add(createAnimalFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при фильтрации: " + e.getMessage());
        }
        return animals;
    }

    // UPDATE
    public void updateAnimal(int id, Animal animal) {
        String sql = "UPDATE animals SET name=?, age=?, weight=?, color=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, animal.getName());
            pstmt.setInt(2, animal.getAge());
            pstmt.setDouble(3, animal.getWeight());
            pstmt.setString(4, animal.getColor());
            pstmt.setInt(5, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Животное успешно обновлено!");
            } else {
                System.out.println("Животное с таким ID не найдено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    // DELETE
    public void deleteAnimal(int id) {
        String sql = "DELETE FROM animals WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Животное успешно удалено!");
            } else {
                System.out.println("Животное с таким ID не найдено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }

    // Вспомогательный метод для создания объекта из строки БД
    private Animal createAnimalFromResultSet(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        double weight = rs.getDouble("weight");
        String color = rs.getString("color");

        // Создаем нужный класс в зависимости от типа
        switch (type.toLowerCase()) {
            case "cat": return new Cat(id, name, age, weight, color);
            case "dog": return new Dog(id, name, age, weight, color);
            case "duck": return new Duck(id, name, age, weight, color);
            default: return new Animal(id, name, age, weight, color);
        }
    }
}