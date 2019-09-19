package com.cy.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决
 *
 * @author CY
 * @create 2019-09-19 15:15
 */
public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);

    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

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

        System.out.println("ABA问题的解决");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 第一次版本号：" + stamp + ",值是"+atomicStampedReference.getReference());
            //暂停1s,让t4拿到stamp
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100,101,1,2);
            System.out.println(Thread.currentThread().getName() + "第二次版本号是:" + atomicStampedReference.getStamp()+ ",值是"+atomicStampedReference.getReference());
            atomicStampedReference.compareAndSet(101,100,2,3);
            System.out.println(Thread.currentThread().getName() + "第三次版本号是:" + atomicStampedReference.getStamp()+ ",值是"+atomicStampedReference.getReference());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 第一次版本号：" + stamp + ",值是"+atomicStampedReference.getReference());
            //暂停3s,让t3拿完成
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100,1024,1,2);
            System.out.println(Thread.currentThread().getName() + " 第二次版本号：" + atomicStampedReference.getStamp() + ",值是"+atomicStampedReference.getReference());
        }, "t4").start();
    }
}
