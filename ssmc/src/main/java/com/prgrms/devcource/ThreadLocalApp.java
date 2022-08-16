package com.prgrms.devcource;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.*;

public class ThreadLocalApp {

    final static ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(getCurrentThreadName() + " ### main get value =  1");
        threadLocalValue.set(1);
        a();
        b();
        //다른스레드에서 람다식 실행하는 비동기 실행코드
        CompletableFuture<Void> task = runAsync(() -> {
            a();
            b();
        });
        //람다식 코드 끝날때까지 대기
        task.join();
    }

    public static void a() {
        Integer value = threadLocalValue.get();
        System.out.println(getCurrentThreadName() + " ### a() get value = " + value);
    }

    public static void b() {
        Integer value = threadLocalValue.get();
        System.out.println(getCurrentThreadName() + " ### b() get value = " + value);
    }

    /**
     * 현재 코드가 실행되고 있는 쓰레드 이름 반환메서드
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
