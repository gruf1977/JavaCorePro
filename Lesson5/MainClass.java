import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    static final int CARS_COUNT = 4;
    static volatile int finishcount;
    static String[] winner = new String[CARS_COUNT];
    static int numfinish;

    //подготовка к старту (CARS_COUNT*3+3) - количество действий до общего старта
    static final CountDownLatch START = new CountDownLatch(CARS_COUNT*3+3);

    //тоннель с емкостью половина участников
    static final Semaphore smp = new Semaphore(CARS_COUNT/2);


    public static void main(String[] args) throws InterruptedException {

        numfinish = 0;
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40),new Finish());
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();

        }
        while (START.getCount() > cars.length)
            Thread.sleep(100);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        Thread.sleep(1000);
        System.out.println("На старт!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1

        Thread.sleep(1000);
        System.out.println("Внимание!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1

        Thread.sleep(1000);
        System.out.println("Марш!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1

            }

            //пересечение финишной прямой
    public static synchronized void finish(String name)  {
        numfinish++;

        //запись финишного листа
        if (numfinish == 1) {
            System.out.println(name + " Финишировал первым Первое место!");
        } else {System.out.println(name + " Финишировал " + numfinish + " место" );}

            MainClass.winner[numfinish-1] = name;

//когда все финишировали - 1 секунда на размышление и публикуем важные сообщения и финишный лист
if (numfinish  == CARS_COUNT){
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победитель "+MainClass.winner[0]+" !!!");
    System.out.println("Финишный список: ");
    for (int i=0; i<CARS_COUNT; i++) {
        System.out.println((i+1) + " Место : " + winner[i]);
    }

    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Спасибо за внимание!");
}

    }







        //System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");


    public static class Car implements Runnable {
        private static int CARS_COUNT;
        static {
            CARS_COUNT = 0;
        }
        private Race race;
        private int speed;
        private String name;
        public String getName() {
            return name;
        }
        public int getSpeed() {
            return speed;
        }

        public Car(Race race, int speed) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
        }
        @Override
        public void run() {
            try {




                System.out.println(this.name + " готовится");
                START.countDown();
                Thread.sleep(500 + (int)(Math.random() * 800));
                System.out.println(this.name + " готов");
                START.countDown();
                System.out.println(this.name + " подьехал к старту");
                Thread.sleep(500 + (int)(Math.random() * 100));
                START.countDown();
                START.await();




            } catch (Exception e) {
                e.printStackTrace();
            }


            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        }
    }





}

