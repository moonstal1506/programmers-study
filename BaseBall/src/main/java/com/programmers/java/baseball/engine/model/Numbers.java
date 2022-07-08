package com.programmers.java.baseball.engine.model;

import lombok.AllArgsConstructor;

import java.util.BitSet;
import java.util.function.BiConsumer;

@AllArgsConstructor
public class Numbers {
    private Integer[] nums;

    public void indexedForEach(BiConsumer<Integer, Integer> consumer) {
        for (int i = 0; i < nums.length; i++) {
            consumer.accept(nums[i],i);
        }
    }
}
