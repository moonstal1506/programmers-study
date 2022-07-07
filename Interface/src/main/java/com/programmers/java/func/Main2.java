package com.programmers.java.func;

public class Main2 {
    public static void main(String[] args) {
        MyRunnable r1 = new MyRunnable() {

            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        //익명 메소드 사용해서 표현하는 방법: 람다 표현식
        MyRunnable r2 = ()-> System.out.println("hello");

        MySupply s1= ()->"hello";

        MyRunnable r3 = ()->{
            MySupply s2 = ()->"hello";
            System.out.println(s2.supply());
        };

        r1.run();
        r2.run();
        r3.run();
    }
}
