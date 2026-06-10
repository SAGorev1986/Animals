public class Main {
    public static void main(String[] args) {
        Animal test = new Animal("ТестовыйЗверь", 6, 10.5, "рыжий");

        System.out.println(test.toString());
        test.say();
        test.go();
        test.drink();
        test.eat();


    }
}