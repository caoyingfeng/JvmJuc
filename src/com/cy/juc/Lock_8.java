package com.cy.juc;

class Phone
{
	
	public  synchronized void sendSMS() throws Exception
	{
//		TimeUnit.SECONDS.sleep(4);
		System.out.println("------sendSMS");
	}
	public  synchronized void sendEmail() throws Exception
	{
		System.out.println("------sendEmail");
	}
	
	public void getHello() 
	{
		System.out.println("------getHello");
	}
	
}

/**
 * 
 * @Description: 8锁
 * 
 1 标准访问，先打印短信还是邮件
 2 停4秒在短信方法内，先打印短信还是邮件
 3 新增普通的hello方法，是先打短信还是hello
 4 现在有两部手机，先打印短信还是邮件
 5 两个静态同步方法，1部手机，先打印短信还是邮件
 6 两个静态同步方法，2部手机，先打印短信还是邮件
 7 1个静态同步方法,1个普通同步方法，1部手机，先打印短信还是邮件
 8 1个静态同步方法,1个普通同步方法，2部手机，先打印短信还是邮件
 

 */
public class Lock_8
{
	public static void main(String[] args) throws Exception
	{

		Phone phone = new Phone();
//		Phone phone2 = new Phone();
		
		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "AA").start();
		
		Thread.sleep(100);
		
		new Thread(() -> {
			try {
				phone.sendEmail();
//				phone.getHello();
//				phone2.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "BB").start();
	}
}







/**
 * 笔记
一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法




都换成静态同步方法后，情况又变化
所有的非静态同步方法用的都是同一把锁——实例对象本身，

也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。

所有的静态同步方法用的也是同一把锁——类对象本身，
这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。
但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，
而不管是同一个实例对象的静态同步方法之间，
还是不同的实例对象的静态同步方法之间，只要它们同一个类的实例对象！
 */
