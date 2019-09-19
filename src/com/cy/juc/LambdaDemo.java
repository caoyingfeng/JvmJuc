package com.cy.juc;

@FunctionalInterface
interface Foo {

	public int add(int x, int y);

}

/**
 * 
 * @Description: Lambda Express-----> 函数式编程 
 * 拷贝小括号(形参列表)，写死右箭头 ->，落地大括号 {方法实现}
 *
 */
public class LambdaDemo {
	public static void main(String[] args) {
		
	}
}

/*
 * 1 拷贝小括号(形参列表)，写死右箭头 ->，落地大括号 {方法实现} 
 * 2 有且只有一个public方法@FunctionalInterface注解增强定义 
 * 3 default方法默认实现 
 * 4 静态方法实现
 * 
 **/
