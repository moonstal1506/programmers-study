package optional;

import collection.User;

public class Main {
    public static void main(String[] args) {
        User user = User.EMPTY;
        User user2 = getUser();
        if (user2 == User.EMPTY) {

        }

    }

    private static User getUser() {
        return User.EMPTY;
    }
}
