package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new LinkedList<>();
        List<Integer> list2 = new ArrayList<>();

        list1.add(1);
        list1.add(2);
        list1.add(3);

        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }

        new MyCollection<>(Arrays.asList(1, 2, 3, 4, 5))
                .foreach(System.out::println);

        MyCollection<String> c1 = new MyCollection<>(Arrays.asList("Aa", "Baa", "Caaa"));
        MyCollection<Integer> c2 = c1.map(String::length);
        c2.foreach(System.out::println);

        new MyCollection<>(Arrays.asList("1", "22", "333"))
                .map(String::length)
                .filter(i -> i % 2 == 1)
                .foreach(System.out::println);

        int size = new MyCollection<>(Arrays.asList("1", "22", "333"))
                .map(String::length)
                .filter(i -> i % 2 == 1)
                .size();
        System.out.println("size = " + size);
    }
}
