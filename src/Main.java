import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnimalDao dao = new AnimalDao(); // Работаем с БД через DAO
        boolean isRunning = true;

        System.out.println("Добро пожаловать в приложение управления животными (с БД)!");

        while (isRunning) {
            System.out.print("\nКоманды (add/list/filter/update/delete/exit): ");
            String input = scanner.nextLine();
            Command command = Command.fromString(input);

            if (command == null) {
                System.out.println("Неизвестная команда. Попробуйте еще раз.");
                continue;
            }

            switch (command) {
                case ADD:
                    addAnimal(scanner, dao);
                    break;
                case LIST:
                    listAnimals(dao.getAllAnimals());
                    break;
                case FILTER:
                    filterAnimals(scanner, dao);
                    break;
                case UPDATE:
                    updateAnimal(scanner, dao);
                    break;
                case DELETE:
                    deleteAnimal(scanner, dao);
                    break;
                case EXIT:
                    System.out.println("Выход из программы. До свидания!");
                    isRunning = false;
                    break;
            }
        }
        scanner.close();
    }

    private static void addAnimal(Scanner scanner, AnimalDao dao) {
        String type = getValidAnimalType(scanner);

        System.out.print("Имя: ");
        String name = scanner.nextLine().trim();
        System.out.print("Возраст: ");
        int age = parseIntSafe(scanner);
        System.out.print("Вес (кг): ");
        double weight = parseDoubleSafe(scanner);
        System.out.print("Цвет: ");
        String color = scanner.nextLine().trim();

        Animal newAnimal;
        // ID = 0, потому что он сгенерируется в БД автоматически
        switch (type) {
            case "cat": newAnimal = new Cat(0, name, age, weight, color); break;
            case "dog": newAnimal = new Dog(0, name, age, weight, color); break;
            case "duck": newAnimal = new Duck(0, name, age, weight, color); break;
            default: return;
        }

        dao.addAnimal(newAnimal);
        System.out.print("Оно говорит: ");
        newAnimal.say();
    }

    private static void listAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("Список животных пуст.");
            return;
        }
        System.out.println("\n--- Список всех животных из БД ---");
        for (Animal animal : animals) {
            System.out.println(animal.toString());
            if (animal instanceof Flying) {
                ((Flying) animal).fly();
            }
        }
        System.out.println("----------------------------------");
    }

    private static void filterAnimals(Scanner scanner, AnimalDao dao) {
        System.out.print("Введите тип для фильтрации (cat/dog/duck): ");
        String type = scanner.nextLine().trim().toLowerCase();
        List<Animal> filtered = dao.getAnimalsByType(type);

        if (filtered.isEmpty()) {
            System.out.println("Животных этого типа не найдено.");
        } else {
            System.out.println("\n--- Отфильтрованный список ---");
            for (Animal animal : filtered) {
                System.out.println(animal.toString());
            }
            System.out.println("-----------------------------");
        }
    }

    private static void updateAnimal(Scanner scanner, AnimalDao dao) {
        System.out.print("Введите ID животного для редактирования: ");
        int id = parseIntSafe(scanner);

        System.out.print("Новое имя: ");
        String name = scanner.nextLine().trim();
        System.out.print("Новый возраст: ");
        int age = parseIntSafe(scanner);
        System.out.print("Новый вес: ");
        double weight = parseDoubleSafe(scanner);
        System.out.print("Новый цвет: ");
        String color = scanner.nextLine().trim();

        Animal updated = new Animal(id, name, age, weight, color);
        dao.updateAnimal(id, updated);
    }

    private static void deleteAnimal(Scanner scanner, AnimalDao dao) {
        System.out.print("Введите ID животного для удаления: ");
        int id = parseIntSafe(scanner);
        dao.deleteAnimal(id);
    }

    private static String getValidAnimalType(Scanner scanner) {
        while (true) {
            System.out.print("Какое животное добавить из (cat/dog/duck)? ");
            String type = scanner.nextLine().trim().toLowerCase();
            if (type.equals("cat") || type.equals("dog") || type.equals("duck")) {
                return type;
            }
            System.out.println("Неверное животное! Выберите из: cat, dog или duck.");
        }
    }

    private static int parseIntSafe(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число: ");
            }
        }
    }

    private static double parseDoubleSafe(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число: ");
            }
        }
    }
}