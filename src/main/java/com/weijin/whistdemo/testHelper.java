package com.weijin.whistdemo;

import java.util.ArrayList;
import java.util.List;

public class testHelper {
    public void getBiggestInList(List<Integer> list) {
        int max = 0;
        for (Integer i : list) {
            if (i > max) {
                max = i;
            }
        }
        System.out.println(max);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        testHelper test = new testHelper();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        test.getBiggestInList(list);
    }

}
