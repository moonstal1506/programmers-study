package com.example.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTests {

    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    void hamcrestTest() {
        assertEquals(2, 1 + 1);
        assertThat(1 + 1, equalTo(2));
        assertThat(1 + 1, is(2));
        assertThat(1 + 1, anyOf(is(1), is(2))); //둘중 하나만 맞으면 참

        assertNotEquals(1, 1 + 1);
        assertThat(1 + 1, not(equalTo(1)));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListTest() {
        List<Integer> integers = List.of(1, 2, 3);
        assertThat(integers, hasSize(3));
        assertThat(integers,everyItem(greaterThan(0)));
        assertThat(integers,containsInAnyOrder(3,2,1));
        assertThat(integers,hasItem(greaterThanOrEqualTo(2)));
    }
}
