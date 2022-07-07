package optional;

import collection.User;

import java.util.Optional;

public class Main2 {
    public static void main(String[] args) {
        Optional<User> optional = Optional.empty();
        optional = Optional.of(new User(1, "2"));

        optional.isEmpty();//값이 없으면(null) true
        optional.isPresent();//값이 있으면 true

        optional.ifPresent(user -> {
            //do1
        });

        optional.ifPresentOrElse(user -> {
            //do1,
        }, () -> {
            //do2
        });
    }
}
