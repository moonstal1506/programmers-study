package com.programmers.java;

interface YourRunnable{
    void YourRun();
}

interface MyRunnable{
    void myRun();
}

public class Main implements MyRunnable, YourRunnable{

    public static void main(String[] args) {
        Main m = new Main();
        m.myRun();
        m.YourRun();
    }

    @Override
    public void YourRun() {
        System.out.println("YourRun");
    }

    @Override
    public void myRun() {
        System.out.println("myRun");
    }
}
