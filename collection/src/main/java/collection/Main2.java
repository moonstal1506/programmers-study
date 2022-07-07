package collection;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main2 {

    public static void main(String[] args) {
        new MyCollection<User>(
                Arrays.asList(
                        new User(15,"a"),
                        new User(16,"b"),
                        new User(17,"c"),
                        new User(18,"d"),
                        new User(19,"e"),
                        new User(20,"f")
                )
        ).filter(User::isOver19)
                .foreach(System.out::println);
    }
}
