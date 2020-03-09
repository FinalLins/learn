package com.lin.learn;

import com.lin.learn.java.p1.BitOperation;
import com.lin.learn.java.p1.MyArray;
import com.lin.learn.java.p1.Sort;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void bitOperationTest() {
        BitOperation.test();
    }

    @Test
    public void sortTest() {
        Sort.test();
    }

    @Test
    public void arrayTest() {
        MyArray.test();
    }
}