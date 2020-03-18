package com.lin.learn;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {


        int[] arr = {333, 1324, 123, 5, 242, 7982};
        int len = arr.length;

        //1.获取最大值
        int max = 0;
        for (int i = 0; i < len; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        //2.求得最大值的位数
        int maxCount = 0;
        while (max != 0) {
            max /= 10;
            maxCount++;
        }
        //构建桶
        int bucketSize = 10;
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        //3.循环位数的次数，从低位开始到高位
        for (int i = 0; i < maxCount; i++) {
            int base = (int) Math.pow(10, i);
            //4.取出对应位数的值放到对应桶里面
            for (int j = 0; j < len; j++) {
                int bucketNo = (arr[j] / base) % 10;
                buckets.get(bucketNo).add(arr[j]);
            }

            //5.从桶里面把数据放回数组中
            int index = 0;
            for (int k = 0; k < bucketSize; k++) {
                List<Integer> bucket = buckets.get(k);
                if (bucket == null || bucket.isEmpty()) {
                    continue;
                }
                for (int l = 0 ; l < bucket.size(); l++) {
                    arr[index++] = bucket.get(l);
                }
                bucket.clear();
            }
        }

        //6.当循环结束后，排序结束
        System.out.println(Arrays.toString(arr));
        //
    }

    public static int[] LSDSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }

        int length = arr.length;

        //1.找到最大值
        int max = arr[0];
        for (int i = 0; i < length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        //2.计算出最大的数据有几位
        int maxCount = 0;
        while (max != 0) {
            max /= 10;
            maxCount++;
        }

        //3.定义桶
//            int[] bucket = new int[10];
        int bucketSize = 10; //0~9
        List<List<Integer>> bucketList = new ArrayList<>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            bucketList.add(new ArrayList<Integer>());
        }


        for (int i = 1, base = maxCount; i <= maxCount; i++, base *= 10) {
            //基数排序是按照位先排序
            for (int j = 0; j < length; j++) {
                int index = arr[j] / base % 10;
                bucketList.get(index).add(arr[j]);
            }

            //然后收集
            for (int j = 0, index = 0; j < bucketSize; j++) {
                for (Integer integer : bucketList.get(j)) {
                    System.out.println(integer);
                    arr[index++] = integer;
                }
                bucketList.get(j).clear();
            }
        }
        return arr;

    }
}