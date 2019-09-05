public class Finish extends Stage {


    @Override
    public void go(MainClass.Car c) {


        MainClass.finish(c.getName());

//        MainClass.finishcount--;
//        if (MainClass.finishcount == 0){
//            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
//            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победитель "+MainClass.winner+" !!!");
//            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Спасибо за внимание!");
//        }

    }
}
