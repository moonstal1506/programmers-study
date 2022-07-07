package com.programmers.java.func;

class Greeting implements MySupply {

    @Override
    public String supply() {
        return "hello";
    }
}

class SayHello implements MyRunnable {
    @Override
    public void run() {
        System.out.println(new Greeting().supply());
    }
}

public class Main {
    public static void main(String[] args) {
//        new SayHello().run();

        // 이름없는 클래스 생성 -> 익명 클래스
        new MySupply() {

            @Override
            public String supply() {
                return "hello";
            }
        }.supply();

        MyRunnable r = new MyRunnable() {
            @Override
            public void run() {
                MySupply s = new MySupply() {
                    @Override
                    public String supply() {
                        return "hello";
                    }
                };
                System.out.println(s.supply());
            }
        };
        r.run();
    }
}
