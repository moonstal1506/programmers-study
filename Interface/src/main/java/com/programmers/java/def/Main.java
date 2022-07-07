package com.programmers.java.def;

interface MyInterface{
    void method1(); //구현x: 추상메소드

    default void hello(){
        System.out.println("hello");
    }
}

public class Main implements MyInterface{
    public static void main(String[] args) {
        new Main().hello();
        new Main().method1();
    }

    @Override
    public void hello() {
        System.out.println("hi");
    }

    @Override
    public void method1() {
        throw new RuntimeException();
    }
}
