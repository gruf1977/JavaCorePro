public class Main {

    public static void main(String[] args) {
        //задаем количество яблок и апельсинов
        Integer kolApple = 5;
        Integer kolOrange = 4;

        //Создаем и наполняем ящик с яблоками
        Box<Apple> BoxWithApple = new Box<>();
        for (int i = 0; i < kolApple; i++) {
        BoxWithApple.add(new Apple());
        }

        //Создаем и наполняем ящик с апельсинами
        Box<Orange> BoxWithOrange = new Box<>();
        for (int i = 0; i < kolOrange; i++) {
        BoxWithOrange.add(new Orange());
        }

        System.out.println("Вес коробки 1 с яблоками : " + BoxWithApple.getWeight());
        System.out.println("Вес коробки 1 с апельсинами : " + BoxWithOrange.getWeight());

        //Сравнение коробок по весу

            System.out.println("Вес коробок одинаковый ? = " + BoxWithOrange.CompareBox(BoxWithApple) );


        //создаем новую коробку для пересыпки яблок
        Box<Apple> BoxWithApple2 = new Box<>();
        //пересыпаем яблоки в новую коробку из старой
        BoxWithApple2.fromBoxToBox(BoxWithApple);

        System.out.println();
        System.out.println("Вес коробки 1 после персыпки " + BoxWithApple.getWeight());
        System.out.println("Вес коробки 2 после персыпки " + BoxWithApple2.getWeight());

        //Добавление одного фрукта
        BoxWithApple.add(new Apple());
        System.out.println();
        System.out.println("Вес коробки 1 после добавления яблока " + BoxWithApple.getWeight());

    }


}
