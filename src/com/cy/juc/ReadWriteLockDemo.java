package com.cy.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyQueue
{
	
	
	private Object obj;

	ReadWriteLock rwlLock = new ReentrantReadWriteLock();


	public void readObj()
	{
		rwlLock.readLock().lock();
		System.out.println(Thread.currentThread().getName() + "读出的内容是：" + obj);
		rwlLock.readLock().unlock();
	}
	
	public void writeObj(Object obj)
	{
		rwlLock.writeLock().lock();
		this.obj = obj;
		System.out.println(Thread.currentThread().getName() + "写的内容是：" + obj);
		rwlLock.writeLock().unlock();
	}
	
}

/**
 * 
 * @Description: 一个线程写入,100个线程读取
 * 
 */
public class ReadWriteLockDemo
{
	public static void main(String[] args) throws InterruptedException
	{
		MyQueue mq = new MyQueue();
		new Thread(() -> {
		    mq.writeObj("你是谁");
		}, "AA").start();

		for (int i = 0; i < 100; i++){
		    new Thread(() -> {
		    	mq.readObj();
		    },String.valueOf(i)).start();
		}
	}
}
