package com.cy.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData//资源类
{
    private int number = 0;//初始值为零的一个变量

    Lock lock = new ReentrantLock();
    Condition cd = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (number != 0) {
                cd.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + " number=" + number);
            cd.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                cd.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + " number=" + number);
            cd.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * @Description: 现在有两个线程，
 * 可以操作初始值为零的一个变量，
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 交替，来10轮。
 * <p>
 * * 笔记：Java里面如何进行工程级别的多线程编写
 * 1	多线程编程模板（套路）-----上
 * 1.1	线程操作资源类
 * 1.2	高内聚	低耦合
 * 2	多线程编程模板（套路）-----下
 * 2.1	判断
 * 2.2	干活
 * 2.3	通知
 */
public class NotifyWaitDemo {
    public static void main(String[] args) {
        ShareData sd = new ShareData();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sd.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}





/*
 * 3	防止虚假唤醒用while
 *
 * */





