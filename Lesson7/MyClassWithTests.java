public class MyClassWithTests {
    @AfterSuite
    public void methodAfterSuite(){
        System.out.println("Принт из метода AfterSuite");
    }

    @Test(prioritet = 2)
    public void methodTest7(){
        System.out.println("Тест7 приоритет 2 Принт из метода два");
    }
    @Test
    public void methodTest3(){
        System.out.println("Тест 3 без приоритета Принт из метода три  (без приоритета)");
    }
    @Test(prioritet = 1)
    public void methodTest1(){
        System.out.println("Тест 1 приоритет 1 Принт из метода один");
    }
    @Test(prioritet = 2)
    public void methodTest2(){
        System.out.println("Тест 2 приоритет 2 Принт из метода два");
    }

    @Test(prioritet = 5)
    public void methodTest10(){
        System.out.println("Тест 10 приоритет 5 Принт из метода один");
    }
    @Test(prioritet = 5)
    public void methodTest11(){
        System.out.println("Тест 11 приоритет 5 Принт из метода один");
    }
    @Test(prioritet = 7)
    public void methodTest12(){
        System.out.println("Тест 12 приоритет 7 Принт из метода один");
    }

    @BeforeSuite
    public void methodBeforeSuite(){
        System.out.println("Принт из метода BeforeSuite");
    }
    @Test
    public void methodTest4(){
        System.out.println("Тест 4 без приоритета Принт из метода три  (без приоритета)");
    }
    @Test
    public void methodTest5(){
        System.out.println("Тест 5 без приоритета Принт из метода три  (без приоритета)");
    }



}
