package task2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test2 {

    private Task2 arrayNew;

    @Before
    public void init() {
        arrayNew = new Task2();
    }

    @Test
    public void test1() {
        int[] b1={1, 1, 1, 4, 4, 1, 4, 4};
        Assert.assertEquals(true, arrayNew.checkArray(b1));
    }
    @Test
    public void test2() {
        int[] b2={1, 1, 1, 1, 1, 1};
        Assert.assertEquals(false, arrayNew.checkArray(b2));
    }
    @Test
    public void test3() {
        int[] b3={4, 4, 4, 4 };
        Assert.assertEquals(false, arrayNew.checkArray(b3));
    }
    @Test
    public void test4() {
        int[] b4={1, 4, 4, 1, 1, 4, 3};
        Assert.assertEquals(false, arrayNew.checkArray(b4));
    }

    @Test
    public void test5() {
        int[] b4={3, 6, 7, 9, 3};
        Assert.assertEquals(false, arrayNew.checkArray(b4));
    }
}
