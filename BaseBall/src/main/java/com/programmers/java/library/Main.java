package com.programmers.java.library;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        String title = faker.name().title();
        System.out.println("title = " + title);

        String name = faker.name().fullName();
        System.out.println("name = " + name);

        String character = faker.starTrek().character();
        System.out.println("character = " + character);

        long number = faker.number().randomNumber();
        System.out.println("number = " + number);

        Integer[] nums = Stream.generate(() -> faker.number().randomDigitNotZero())
                .distinct()
                .limit(3)
                .toArray(Integer[]::new);
        System.out.println(Arrays.toString(nums));
    }
}
