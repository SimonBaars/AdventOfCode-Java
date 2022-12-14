package com.sbaars.adventofcode.util;

import com.sbaars.adventofcode.common.Pair;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AOCUtils {
    public static void verify(boolean b) {
        verify(b, "Something went wrong");
    }

    public static void verify(boolean b, String message) {
        if(!b) {
            throw new IllegalStateException(message);
        }
    }

    public static<A> Stream<Pair<A, A>> connectedPairs(List<A> l) {
        return IntStream.range(1, l.size()).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }

    public static<A> Stream<Pair<A, A>> pairs(List<A> l) {
        return IntStream.range(1, l.size()/2).map(i -> i + ((i-1)*2)).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }
}
