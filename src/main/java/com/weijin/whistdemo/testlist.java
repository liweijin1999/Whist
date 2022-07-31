package com.weijin.whistdemo;

import com.weijin.whistdemo.model.Card;
import com.weijin.whistdemo.utils.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class testlist {
    private static List<Integer> deepCopyCardList(List<Integer> list) {
        return new ArrayList<>(list);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Integer> testList = new ArrayList<>();

        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        System.out.println(testList);
//        List<Integer> testListCopy= helper.SerialCloneUtils.deepCopy(testList);
        List<Integer> testListCopy = deepCopyCardList(testList);
        System.out.println(testList.get(testList.size() - 1));
        testList.remove((Integer) 3);
        System.out.println(testList);
        System.out.println(testList.get(testList.size() - 1));
        System.out.println(testListCopy);
        testListCopy.set(0, 6);
//        testListCopy.removeAll(testList);
        System.out.println(testListCopy);
        System.out.println(testList);
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int fines = random.nextInt(2);
            System.out.print(fines + " ");
        }
    }
}
