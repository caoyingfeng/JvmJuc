package com.cy.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket // 实例变量+实例方法Field +method
{
    private int number = 30;

    // 1.同步方法
	/*public synchronized void sale() {
		// 2.同步代码块
		synchronized (this) {
		}
		if (number > 0) {
			System.out.println(Thread.currentThread().getName() + "卖出第：\t" + (number--) + "\t 还剩下：" + number);
		}
	}*/

    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第：\t" + (number--) + "\t 还剩下：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}


/**
 * @Description:卖票程序复习线程知识 ,    三个售票员			卖出			30张票
 * <p>
 * 笔记：Java里面如何进行工程级别的多线程编写
 * 1	多线程编程模板（套路）-----上
 * 1.1	线程		操作(资源类里的实例方法)资源类
 * 1.2	高内聚	低耦合
 */
public class SaleTicket {
    public static void main(String[] args)//main所有程序的入口
    {
        Ticket ticket = new Ticket();

		/*new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					ticket.sale();
				}
			}
		},"A").start();*/

        new Thread(() -> {
            for (int i = 0; i < 30; i++) ticket.sale();
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) ticket.sale();
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) ticket.sale();
        }, "CC").start();
    }
}