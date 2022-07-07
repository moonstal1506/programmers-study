package com.programmers.java.def2;

public class Main {
    public static void main(String[] args) {
       new Service().method1();
    }
}

class Service extends MyInterfaceAdapter {

    @Override
    public void method1() {
        System.out.println("hello");
    }

}