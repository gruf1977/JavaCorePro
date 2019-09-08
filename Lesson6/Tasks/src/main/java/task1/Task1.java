package task1;

import java.util.Arrays;

public class Task1 {
    public static void main(String[] args) {
        int[] arrInt = {4, 2, 0, 3, 2, 3, 0, 3, 7};
        Task1 tasktest = new Task1();
        tasktest.info(arrInt);
    }



        public void info(int[] arrInt) {
            try {
                System.out.println(Arrays.toString(task1(arrInt)));
            } catch (MyError myError) {
                myError.printStackTrace();
            }
        }


    public int[] task1(int[] arrInt) throws MyError {

          for (int i=(arrInt.length-1); i >= 0 ; i--){
            if (arrInt[i] == 4){
                int[] resultArr = new int[arrInt.length - i - 1];
                for (int j=0; j < (resultArr.length) ; j++){
                    resultArr[j] = arrInt[i+j + 1];

                }
                return resultArr;
            }
        }

        throw new MyError();
    }

    public class MyError extends Exception {
        @Override
        public void printStackTrace() {
            System.err.println("Неверный входной массив");
        }
    }
}
