package com.cy.juc;

import java.util.concurrent.TimeUnit;

/**
 * volatile可以保证可见性
 * @author CY
 * @create 2019-09-19 9:54
 */

class MyData1{
    volatile int number = 0;
    public void addTo60(){
        number = 60;
    }
}

public class VolatileDemo2 {
    public static void main(String[] args) {
        MyData1 myData = new MyData1();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            //线程暂停
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + " over, number = " + myData.number);
        }, "A").start();

        while(myData.number ==0 ){

        }
        System.out.println(Thread.currentThread().getName() + " over, number = " + myData.number);
    }
}
