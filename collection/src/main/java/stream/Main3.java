package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args) {
        //주사위 100번 던져 6 나올 확률
        Random r = new Random();
        long count = Stream.generate(() -> r.nextInt(6) + 1)
                .limit(100)
                .filter(n -> n == 6)
                .count();
        System.out.println(count);

        //1~9 사이 값 중 겹치지 않게 3개 출력
        Random r2 = new Random();
        int[] arr = Stream.generate(() -> r.nextInt(9) + 1)
                .distinct()
                .limit(3)
                .mapToInt(i -> i)
                .toArray();
        System.out.println(Arrays.toString(arr));

        //0~200 사이 값 중에서 랜덤값 5개 뽑아 순서대로 표시
        Random r3 = new Random();
        int[] arr2 = Stream.generate(() -> r.nextInt(200))
                .limit(5)
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .toArray();
        System.out.println(Arrays.toString(arr2));

    }
}
