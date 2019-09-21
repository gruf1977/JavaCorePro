package task1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class Task1test {

    private Task1 arrayNew;

    @Before
    public void init() {
        arrayNew = new Task1();

    }

    @Test
    public void test1() {
        int[] b1={1, 2, 4, 4, 2, 3, 4, 1, 7};
        int[] a = {1,7};
        try {
            Assert.assertEquals(Arrays.toString(a), Arrays.toString(arrayNew.task1(b1)));
        } catch (Task1.MyError myError) {
            myError.printStackTrace();
        }
    }
    @Test
    public void test2() {
        int[] b1={1, 2, 4, 4, 2, 3, 0, 3, 7};
        int[] a = {2, 3, 0, 3, 7};
        try {
            Assert.assertEquals(Arrays.toString(a), Arrays.toString(arrayNew.task1(b1)));
        } catch (Task1.MyError myError) {
            myError.printStackTrace();
        }
    }
    @Test
    public void test3() {
        int[] b1={4, 2, 0, 3, 2, 3, 0, 3, 7};
        int[] a = {2, 0, 3, 2, 3, 0, 3, 7};
        try {
            Assert.assertEquals(Arrays.toString(a), Arrays.toString(arrayNew.task1(b1)));
        } catch (Task1.MyError myError) {
            myError.printStackTrace();
        }
    }

    @Test
    public void test4() {
        int[] b1={1, 2, 0, 3, 2, 3, 0, 3, 7};
        int[] a = {2, 0, 3, 2, 3, 0, 3, 7};
        try {
            Assert.assertEquals(Arrays.toString(a), Arrays.toString(arrayNew.task1(b1)));
        } catch (Task1.MyError myError) {
            myError.printStackTrace();
        }
    }

    public static class MyError extends Exception {
        @Override
        public void printStackTrace() {
            System.err.println("Неверный входной массив");
        }
    }
}
