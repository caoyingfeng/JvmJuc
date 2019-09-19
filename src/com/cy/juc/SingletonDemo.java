package com.cy.juc;

/**
 * DCL double check lock双端检锁机制 单例模式
 * DCL(双端检锁) 机制不一定线程安全,原因是有指令重排的存在,加入volatile可以禁止指令重排
 * 原因在于某一个线程在执行到第一次检测,读取到的instance不为null时,instance的引用对象可能没有完成初始化.
 * DCL方法包含了层判断语句，第一层判断语句用于判断resource对象是否为空，也就是是否被实例化，
 * 如果为空时就进入同步代码块进一步判断，问题就出在了resource的实例化语句instance = new SingletonDemo()上，
 * 因为这个语句实际上不是原子性的。这句话可以大致分解为如下步骤：
 * 1.给instance 的实例分配内存
 * 2.初始化instance 构造器
 * 3.将instance 实例指向分配的内存空间，此时instance 实例就不再为空
 * @author CY
 * @create 2019-09-19 10:18
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + " 构造方法");
    }

    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (SingletonDemo.class){
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
