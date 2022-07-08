package com.programmers.java.library;

public class Main2 {
    public static void main(String[] args) {
        User user = new User();
        User user1 = new User(1,"a");
        User user2 = new User(1,"a");

        System.out.println(user);
        System.out.println(user1);
        System.out.println(user1.equals(user2));
        System.out.println(user1.getAge());

        user1.setName("v");
        System.out.println(user1.getName());
    }
}
