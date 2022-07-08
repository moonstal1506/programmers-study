package com.programmers.java.baseball;

import com.programmers.java.baseball.engine.BaseBall;
import com.programmers.java.baseball.engine.io.NumberGenerator;

public class App {
    public static void main(String[] args) {
        NumberGenerator generator = new HackNumberGenerator();
        Console console = new Console();

        new BaseBall(generator, console, console).run();
    }
}
