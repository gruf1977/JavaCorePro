import java.util.ArrayList;

public class Zadanie1 {

    //метод печати массива
public static void printArray(Element[] myarray){
        for (Element o: myarray ) {
            System.out.println(o.getObj());
        }
        System.out.println();

    }

    public static Element[] changeArray(Element[] myarray, Integer firstIndex, Integer twoIndex){
        System.out.println("Задание 1 поменять элементы массива местами");

        if (firstIndex<myarray.length && twoIndex<myarray.length) {
            Element temp = myarray[firstIndex];
            myarray[firstIndex] = myarray[twoIndex];
            myarray[twoIndex] = temp;

        } else {
            System.out.println("Не верно заданы индексы элементов массива. Массив не изменен");
        }

        return myarray;
    }

    public static ArrayList ArrayToArrayList(Element[] myarray){
        System.out.println("Задание 2 Преобразовать массив в ArrayList");
        ArrayList arrayElenment = new ArrayList();
        for (Element o: myarray ) {

            arrayElenment.add(o.getObj());

        }
        return arrayElenment;

    }

    public static void main(String[] args) {
        System.out.println("Массив с сылочными типами данных:");
        Element[] myarray = new Element[3];
        myarray[0] = new Element<String>("own");
        myarray[1] = new Element<Integer>(6);
        myarray[2] = new Element<Double>(5.24);
        printArray(myarray); // Первоначальный массив

        printArray(changeArray(myarray, 2, 0)); // массив с поменяными значениями
        System.out.println(ArrayToArrayList(myarray).toString()); // массив преобразован в коллекцию



    }

}
