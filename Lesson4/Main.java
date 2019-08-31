/*1. Создать три потока, каждый из которых выводит определенную букву
(A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.*/

public class Main {
    private final Object mon = new Object(); //объект синхронизации
    private volatile char currentLetter = 'A';
    private final int countprint = 5; // количество циклов печати

    public static void main(String[] args) {

        Main w = new Main();

        Thread t1 = new Thread(() -> {
            try {
                w.printA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {

            w.printB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
            w.printC();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        });
        t3.start();
        t1.start();
        t2.start();



        }

    public void printA() throws InterruptedException {
        synchronized (mon) {


            for (int i = 0; i < countprint; i++) {

                while (currentLetter != 'A') {
                    mon.wait();
                }
                System.out.print("A");
                currentLetter = 'B';
                mon.notifyAll();

            }
        }
    }

    public void printB() throws InterruptedException {
        synchronized (mon) {
            for (int i = 0; i < countprint; i++) {

                while (currentLetter != 'B') {
                    mon.wait();
                }
                System.out.print("B");
                currentLetter = 'C';
                mon.notifyAll();
            }

        }
    }

    public void printC() throws InterruptedException {
        synchronized (mon) {
            for (int i = 0; i < countprint; i++) {

                while (currentLetter != 'C') {
                    mon.wait();
                }
                System.out.print("C");
                currentLetter = 'A';
                mon.notifyAll();
            }

        }
    }
}

