package com.cy.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 不保证原子性
 * @author CY
 * @create 2019-09-19 9:17
 */

class MyData{
    volatile int number = 0;
    public void addPlusPlus(){
        number++;
    }

    AtomicInteger ai = new AtomicInteger();
    public int getSerialNumber(){
        return ai.getAndIncrement();
    }
}

public class VolatileDemo {

    public static void main(String[] args) {

        MyData myData = new MyData();
        
        for (int i = 0; i < 20; i++){
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 20; i++){
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.getSerialNumber();
                }
            },String.valueOf(i)).start();
        }

//        System.out.println(myData.ai);
        while(Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(myData.number);
        System.out.println(myData.ai);
    }
}
