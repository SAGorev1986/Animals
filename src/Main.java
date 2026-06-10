import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Animal> animals = new ArrayList<>();
        boolean isRun = true;
        String command_str = "(add/list/exit)";

        System.out.println("Приложение для управления животными");

        while (isRun){
            System.out.println("Введите команду "+ command_str + ":");
            String input = scanner.nextLine();
            Command command = Command.fromString(input);

            if(command == null){
                System.out.println("Неверная команда, введите " + command_str + "!");
                continue;
            }
            switch (command){
                case ADD:
                    addAnimal(scanner, animals);
                    break;
                case LIST:
                    addAnimal(scanner, animals);
                    break;
                case EXIT:
                    System.out.println("Выход из программы! Всего Хоро-шего)");
                    isRun = false;
                    break;
            }
        }
        scanner.close();


        /*Animal test = new Animal("ТестовыйЗверь", 6, 10.5, "рыжий");

        System.out.println(test.toString());
        test.say();
        test.go();
        test.drink();
        test.eat();*/


    }
    private static void addAnimal(Scanner scanner, List<Animal>animals){
        System.out.println("Какое животное добавить из (cat/dog/duck)?");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.println("Имя: ");
        String name = scanner.nextLine().trim();
        System.out.println("Возраст: ");
        int age  = parseIntSafe(scanner);
        System.out.print("Вес (кг): ");
        double weight = parseDoubleSafe(scanner);
        System.out.print("Цвет: ");
        String color = scanner.nextLine().trim();
        Animal newAnimal = null;
        switch (type) {
            case "cat":
                newAnimal = new Cat(name, age, weight, color);
                break;
            case "dog":
                newAnimal = new Dog(name, age, weight, color);
                break;
            case "duck":
                newAnimal = new Duck(name, age, weight, color);
                break;
            default:
                System.out.println("Ошибка: Неизвестный тип животного. Возврат в главное меню.");
                return;
        }

        animals.add(newAnimal);
        System.out.print("Животное добавлено! Оно говорит: ");
        newAnimal.say();
    }

    private static void listAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("Список животных пуст.");
            return;
        }
        System.out.println("\n--- Список животных ---");
        for (Animal animal : animals) {
            System.out.println(animal.toString());

            // Демонстрация полиморфизма: если это утка, она может летать
            if (animal instanceof Flying) {
                ((Flying) animal).fly();
            }
        }
        System.out.println("-----------------------");
    }

    // Вспомогательные методы для безопасного ввода чисел (чтобы программа не падала при ошибке)
    private static int parseIntSafe(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число заново: ");
            }
        }
    }

    private static double parseDoubleSafe(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число заново: ");
            }
        }
    }
}