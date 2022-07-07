package stream;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.asList("a","aa","asd","asdf")
                .stream()
                .map(String::length)
                .filter(i->i%2==1)
                .forEach(System.out::println);
    }
}
