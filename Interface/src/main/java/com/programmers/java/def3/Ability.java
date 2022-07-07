package com.programmers.java.def3;

public interface Ability{
    static void sayHello(){
        System.out.println("hello");
    }
}

interface Flyable {
    default void fly() {
        System.out.println("fly");
    }
}
interface Swimable {
    default void swim() {
        System.out.println("swim");
    }
}
interface Walkable {
    default void walk(){
        System.out.println("walk");
    }
}
