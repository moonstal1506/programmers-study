package com.programmers.java.def3;

class Duck implements Swimable, Walkable {}

class Swan implements Flyable, Walkable {}

public class Main {
    public static void main(String[] args) {
        new Duck().swim();
        new Duck().walk();
        new Swan().fly();
        Ability.sayHello();
    }
}
