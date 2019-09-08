import java.io.*;

/*Последовательно сшить 5 файлов в один (файлы примерно 100 байт). */


import static java.lang.System.currentTimeMillis;
public class Dop2 {
    public static void main(String[] args) throws IOException {
        long before = currentTimeMillis();
        String[] namefile={"test21.bin", "test22.bin", "test23.bin", "test24.bin", "test25.bin"};
        String resultfile = "result2.bin";
        File fileres = new File(resultfile);
        if (!isFile(resultfile)) {
            fileres.createNewFile(); // создаем результирующий файл
        }

        //Открываем результирующий файл для записи
        BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream(fileres, true)); // true - добавление в конец файла



        for (String nf:namefile) {

            File file = new File(nf);
            BufferedInputStream bufRead = new BufferedInputStream(new FileInputStream(file));
            int n;

            while((n = bufRead.read()) != -1) {
                bufOut.write(n);
            }
            bufRead.close();
            System.out.println();
        }
        bufOut.close();     // Закрываем соединения
        long after = currentTimeMillis();
        System.out.println("Время ms " + (currentTimeMillis()-before));


        //Читаем новый объединенный файл
       BufferedInputStream bufRead = new BufferedInputStream(new FileInputStream(fileres));
        System.out.println("Файл : " + fileres);
        int n = 0; int count = 0;
        while ((n = bufRead.read()) != -1) {
            System.out.print(n + ", ");
            count++;
        }
        bufRead.close();
        System.out.println();
        System.out.println("Количество элементов : " + count);
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