package com.cy.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题的解决
 *
 * @author CY
 * @create 2019-09-19 15:15
 */
public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);

    public static void main(String[] args) {
        System.out.println("ABA问题的产生");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        }, "t1").start();
        
        new Thread(() -> {
            //暂停1秒,等t1完成
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 102) + "\t" + atomicReference.get());
        }, "t2").start();

    }
}
