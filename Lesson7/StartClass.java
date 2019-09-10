import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StartClass {
    public static void main(String[] args) throws Exception {

        start(MyClassWithTests.class);
    }

    // Проверяем на наличиеметодов и запускаем их в порядке расположения в классе
    public static void start(Class testClass) throws Exception {
        MyClassWithTests cat = new MyClassWithTests();
        Method[] m = testClass.getMethods();
        int indexBeforeSuite = -1;
        int indexAfterSuite = -1;
        Map<Method, Integer> metodsTest = new HashMap<>();
        int i = 0;

        // Проверяем на единственность анотаций BeforeSuite и AfterSuite перед началом тестирования
        // и запоминаем их индекс в массиве Методов
        for (Method met : m) {
            if (met.getAnnotation(BeforeSuite.class) != null) {
                if (indexBeforeSuite == -1) {
                    indexBeforeSuite=i;
                } else {
                    throw new Exception("Аннотаций BeforeSuite не может быть больше одной");
                }
            }
            if (met.getAnnotation(AfterSuite.class) != null) {
                if (indexAfterSuite == -1) {
                    indexAfterSuite=i;
                } else {
                    throw new Exception("Аннотаций AfterSuite не может быть больше одной");
                }
            }
            // Заполняем map методов с анатацией Test
            if (met.getAnnotation(Test.class) != null){
                metodsTest.put(met, met.getAnnotation(Test.class).prioritet());
            }

            i++;
        }
        // обрабатываем метод BeforeSuite
        if (indexBeforeSuite > -1) {m[indexBeforeSuite].invoke(cat);}


        // сортируем методы c анотацией Test по приоритету
        //если приоритет не указан то приоритет 0
        Map<Method, Integer> result = metodsTest.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for(Map.Entry met: result.entrySet()){
            Method met1 = (Method) met.getKey();
            met1.invoke(cat);
        }
        // обрабатываем метод AfterSuite
        if (indexAfterSuite > -1) { m[indexAfterSuite].invoke(cat);}
    }
}