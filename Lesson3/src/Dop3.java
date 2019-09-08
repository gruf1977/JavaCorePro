import java.io.*;
import java.util.Scanner;

import static java.lang.System.currentTimeMillis;

/*
*Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
*  Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
* Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
* */
public class Dop3 {
    public static void main(String[] args) throws IOException {
        long before = currentTimeMillis();
        FileReader reader = new FileReader("ghj.txt");
        long after = currentTimeMillis();
        System.out.println("Время ms " + (currentTimeMillis()-before));


        int countByte = 1000;

        while (true) {
            char[] buf = new char[countByte];
            int c;
            c = reader.read(buf, 0, countByte);
            System.out.println(buf);
            reader.skip(countByte);
            System.out.println("------------------------------------------");
            System.out.println("Нажмите любу клвишу для продолжения");
            System.out.println("1 выход из программы");
            System.out.println("------------------------------------------");
            Scanner sc = new Scanner(System.in);
            String value = sc.nextLine();
            if (c == -1) {
                break;
            }
            if (value.equals("1")) {
                break;
            }

        }


    }
}


