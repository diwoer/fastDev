package com.di.fast;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
    public void testClone() {
        Person person = new Person();
        person.age = 10;
        person.name = "1";
        Address address = new Address();
        address.city = "北京";
        address.town = "海淀";
        person.address = address;

        System.out.println("init : " + person.toString());

        try {
            Person clonePerson = person.clone();
            System.out.println("after clone : " + person.toString() + ", " + clonePerson.toString());

            clonePerson.name = "2";
            System.out.println("after put clone object: " + person.toString() + ", " + clonePerson.toString());

            clonePerson.address.city = "石家庄";
            System.out.println("after put clone object address: " + person.toString() + ", " + clonePerson.toString());


        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testString() {
        StringBuilder before = new StringBuilder("dichangshun");
        changeString(before);
        System.out.println(before);

//        int beforeInt = 0;
//        changeInt(beforeInt);
//        System.out.println(beforeInt);
//
//        Name beforeName = new Name();
//        beforeName.name = "dichangshun";
//        changeObject(beforeName);
//        System.out.print(beforeName.toString());
    }

    private static void changeString(StringBuilder before) {
//        before = new StringBuilder("dcs");
//        before.delete(0, before.length());
//        before.setLength(0);
        before.append("dcs");
    }

    private static void changeInt(int before) {
        before = 1;
    }

    private static void changeObject(Name before) {
        Name name = new Name();
        name.name = "dcs";
        before = name;
    }

    private static class Name {

        public String name;

        @Override
        public String toString() {
            return "Name{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void test() {
        int[] arr = new int[]{
                3, 3, 3, 3, 5, 5, 5, 2, 2, 7
        };
        int[] arr1 = new int[]{
                7, 7, 7, 7, 7, 7
        };
        int[] arr2 = new int[]{
                1, 9
        };
        int[] arr3 = new int[]{
                1000, 1000, 3, 7
        };
        int[] arr4 = new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        };
        System.out.println("arr:" + new Solution().minSetSize(arr));
        System.out.println("===============================");
        System.out.println("arr1:" + new Solution().minSetSize(arr1));
        System.out.println("===============================");
        System.out.println("arr2:" + new Solution().minSetSize(arr2));
        System.out.println("===============================");
        System.out.println("arr3:" + new Solution().minSetSize(arr3));
        System.out.println("===============================");
        System.out.println("arr4:" + new Solution().minSetSize(arr4));
        System.out.println("===============================");


        char[][] matrix = new char[][]{
                new char[]{'1', '0', '1', '0', '0'},
                new char[]{'1', '0', '1', '1', '1'},
                new char[]{'1', '1', '1', '1', '1'},
                new char[]{'1', '0', '0', '1', '0'},
        };
        System.out.println("matrix:" + new Solution().maximalRectangle(matrix));
        System.out.println("===============================");

    }

    class Solution {


        /**
         * 给你一个整数数组 arr。你可以从中选出一个整数集合，并删除这些整数在数组中的每次出现。
         * <p>
         * 返回 至少 能删除数组中的一半整数的整数集合的最小大小。
         */
        public int minSetSize(int[] arr) {

            int length = arr.length;

            Map<Integer, Integer> numberCountMap = new HashMap<>();

            for (int key : arr) {
                if (numberCountMap.containsKey(key)) {
                    int value = numberCountMap.get(key);
                    numberCountMap.put(key, ++value);
                    if (numberCountMap.get(key) >= length / 2) {
                        return 1;
                    }
                } else {
                    numberCountMap.put(key, 1);
                }
            }

            System.out.println("numberCountMap.size=" + numberCountMap.size());

            List<Integer> valueCountList = new ArrayList<>(numberCountMap.values());
            Collections.sort(valueCountList, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            System.out.println("valueCountList.size=" + valueCountList.size() + ", " + valueCountList.get(0));

            int valueSize = 2;

            while (valueSize <= valueCountList.size()) {

                int sunCount = 0;
                for (int i = 0; i < valueSize; i++) {
                    sunCount += valueCountList.get(i);
                }

                System.out.println("sunCount=" + sunCount);

                if (sunCount >= length / 2) {
                    return valueSize;
                }
                valueSize++;
            }

            System.out.println("valueSize=" + valueSize);

            return length;
        }

        //给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
        public int maximalRectangle(char[][] matrix) {

            //最大面积
            int maxRect = 0;

            for (int i = 0; i < matrix.length; i++) {

                char[] rowArr = matrix[i];
                int col = 0;
                int row = 0;
                int max = 0;

                for (int j = 0; j < rowArr.length; j++) {

                    if (rowArr[j] == '1') {
                        row += 1;
                        col += 1;

                        for (int k = i+1; k < matrix.length; k++) {
                            char[] nextRowArr = matrix[k];
                            if (j < nextRowArr.length && nextRowArr[j] == '1') {
                                col += 1;
                            } else {
                                break;
                            }
                        }
                    } else {
                        max = Math.max(col * row, max);
                        row = 0;
                        col = 0;
                    }
                }
                maxRect = Math.max(maxRect, max);
            }


            return maxRect;
        }
    }
}