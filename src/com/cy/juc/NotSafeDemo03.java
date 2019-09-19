package com.cy.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author CY
 * @create 2019-09-17 19:50
 */
public class NotSafeDemo03 {
    public static void main(String[] args) {
        //ArraySetNotSafe();
        //arrayListNotSafe();
        Map<String,String> map = new ConcurrentHashMap<>();//HashMap
        for (int i = 0; i < 30; i++)
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
    }

    private static void ArraySetNotSafe() {
        Set<String> list = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 3; i++)
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
    }

    private static void arrayListNotSafe() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
