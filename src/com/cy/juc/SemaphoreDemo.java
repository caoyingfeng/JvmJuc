package com.cy.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * <p>
 * 在信号量上我们定义两种操作：
 * acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 * 要么一直等下去，直到有线程释放信号量，或超时。
 * release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
 * <p>
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 * <p>
 * 情景：3个停车位，6部汽车争抢车位
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore sp = new Semaphore(3);
		for (int i = 0; i < 6; i++){
		    new Thread(() -> {
                try {
                    sp.acquire();
                    System.out.println(Thread.currentThread().getName() + "号停车");
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "号离开");
                    sp.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
		}
    }
}

class CompareAndSwap{
    private int value;
    //获取内存值
    public synchronized int get(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        return oldValue;
    }
    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue,newValue);
    }
}
