package com.programmers.java.func;

@FunctionalInterface
public interface MyRunnable {
    void run(); //추상메소드가 하나밖에 없는 메소드 == 함수형 인터페이스
}

@FunctionalInterface
interface MyMap{
    //추상메소드가 하나밖에 없음
    void map();
    default void sayHello(){
        System.out.println("hello");
    }
    static void sayBye(){
        System.out.println("Bye");
    }
}