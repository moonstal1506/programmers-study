package com.programmers.java.lambda;

import com.programmers.java.func.MySupply;

public class Main {
    public static void main(String[] args) {
        MySupplier<String> s = () -> "hello";

        MyMapper<String,Integer> m = String::length;
        MyMapper<Integer,Integer> m2 = i->i*i;
        MyMapper<Integer,String> m3 = Integer::toHexString;

        MyConsumer<String> c = System.out::println;//입력값 변경하지마
        MyConsumer<Integer> v = x -> System.out.println(x*10);

        MyRunnable r = () -> v.consume(m.map(s.supply()));
        MyRunnable r2= ()->c.consume(m3.map(m2.map(m.map(s.supply()))));

//        r.run();
        r2.run();
    }
}
