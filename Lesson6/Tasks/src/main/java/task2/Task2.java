package task2;

public class Task2 {

    int[] arrInt;
    boolean checkArray;




    public void info(int[] arrInt) {


        System.out.println(checkArray(arrInt));

    }


    public boolean checkArray(int[] arrInt){
boolean result = false;
        boolean own = false;
        boolean four = false;
        for (int el: arrInt) {
            if (el == 1 || el == 4) {
                if (el == 1)
                    own = true;
                if (el == 4)
                    four = true;
            } else {
                return false;
            }

        }

if (own && four )
    result = true;

    return result;



    }
}
