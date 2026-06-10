import java.lang.String.*;

public class Animal {
    private String name;
    private int age;
    private double weight;
    private String color;

    public Animal(String name,int age,double weight, String color){
        this.age = age;
        this.name = name;
        this.color = color;
        this.weight = weight;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) { this.name = name; }

    public int getAge(){
        return age;
    }
    public void setAge(int age) { this.age = age; }

    public double getWeight(){
        return weight;
    }
    public void setWeight(double weight) { this.weight = weight; }

    public String getColor(){
        return name;
    }
    public void setColor(String color) { this.color = color; }

    public void say() {
        System.out.println("Я говорю");
    }
    public void drink() {
        System.out.println("Я пью");
    }
    public void eat() {
        System.out.println("Я ем");
    }
    public void go() {
        System.out.println("Я иду");
    }
    /*Возврат строки: «Привет! Меня зовут name, мне age лет (/год/года),
     я вешу - weight кг, мой цвет - color») лет или год,
      или года должно быть выбрано в зависимости от числа.
     */
    private String getAgeWord(int age) {
        int absAge = Math.abs(age) % 100;
        int n1 = absAge % 10;
        if (absAge > 10 && absAge < 20) {
            return "лет";
        }
        if (n1 > 1 && n1 < 5) {
            return "года";
        }
        if (n1 == 1) {
            return "год";
        }
        return "лет";
    }
    @Override
    public String toString() {
        return String.format("Привет! Меня зовут %s, мне %d %s, я вешу - %s кг, мой цвет - %s.",
                name,
                age,
                getAgeWord(age),
                weight,
                color);
    }

}
