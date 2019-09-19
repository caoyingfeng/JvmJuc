package com.cy.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS compare and swap
 * @author CY
 * @create 2019-09-19 11:02
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5,2014));
        System.out.println(atomicInteger.compareAndSet(5,2010));
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.getAndIncrement());
    }
}
