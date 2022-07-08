package com.programmers.java.baseball.engine;

import com.programmers.java.baseball.engine.io.Input;
import com.programmers.java.baseball.engine.io.NumberGenerator;
import com.programmers.java.baseball.engine.io.Output;
import com.programmers.java.baseball.engine.model.BallCount;
import com.programmers.java.baseball.engine.model.Numbers;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor//생성자 주입을 통해 의존성 주입받음: 결합도 낮춤
public class BaseBall implements Runnable {

    private final int COUNT_OF_NUMBERS = 3;//확장가능
    private NumberGenerator generator;
    private Input input;
    private Output output;

    @Override
    public void run() {
        Numbers answer = generator.generate(COUNT_OF_NUMBERS);

        while (true) {
            String inputString = input.input("숫자를 맞춰보세요: ");
            Optional<Numbers> inputNumbers = parse(inputString);
            if (inputNumbers.isEmpty()) {
                output.inputError();
                continue;
            }

            BallCount bc = ballCount(answer, inputNumbers.get());
            output.ballCount(bc);
            if (bc.getStrike() == COUNT_OF_NUMBERS) {
                output.correct();
                break;
            }

        }
    }

    private Optional<Numbers> parse(String inputString) {
        if(inputString.length()!=COUNT_OF_NUMBERS) return Optional.empty();

        long count = inputString.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)//숫자값으로 바꿈
                .filter(i -> i>0)
                .distinct()
                .count();

        if(count != COUNT_OF_NUMBERS) return Optional.empty();

        return Optional.of(
                new Numbers(
                        inputString.chars()
                                .map(Character::getNumericValue)//IntStream
                                .boxed()//Integer로 바꿔
                                .toArray(Integer[]::new)
                )
        );
    }

    private BallCount ballCount(Numbers answer, Numbers inputNumbers) {

        //멀티 쓰레드 환경 functional interface 구현체 안에서 스코프 밖에 있는 변수 변경하면 x
        //동기화 기능 추가해야함
        AtomicInteger strike = new AtomicInteger();
        AtomicInteger ball = new AtomicInteger();

        answer.indexedForEach((comNum, comIdx) -> {
            inputNumbers.indexedForEach((userNum, userIdx) -> {
                if (!comNum.equals(userNum)) return;
                if (comIdx.equals(userIdx)) strike.addAndGet(1);//값 가져오고 나서 1증가
                else ball.addAndGet(1);
            });
        });
        return new BallCount(strike.get(), ball.get());
    }
}
