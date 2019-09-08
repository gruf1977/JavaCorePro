import java.io.*;

import static java.lang.System.currentTimeMillis;

public class Dop1 {
    public static void main(String[] args) throws IOException {
        //Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        long before = currentTimeMillis();
        InputStream input = new BufferedInputStream(new FileInputStream("test.bin"),  50);

        int data = 0;
        while ((data = input.read()) != -1){ //-1 означает конец потока
            System.out.print(data + ", ");

        }

        input.close();
        long after = currentTimeMillis();
        System.out.println();
        System.out.println("Время ms " + (after-before));
    }

    private static boolean isFile(String namefile) {
        boolean res = false;
        File file = new File(namefile);
        if (file.exists()) {
            res = true;
        }
        return res;
    }
}
