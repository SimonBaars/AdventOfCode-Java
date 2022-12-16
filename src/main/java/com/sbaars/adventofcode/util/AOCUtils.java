package com.sbaars.adventofcode.util;

import com.sbaars.adventofcode.common.Pair;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

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
        return range(1, l.size()).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }

    public static<A> Stream<Pair<A, A>> pairs(List<A> l) {
        return range(1, l.size()/2).map(i -> i + ((i-1)*2)).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }

    public static<A> Stream<Pair<A, A>> allPairs(List<A> l) {
        return range(0, l.size()).boxed().flatMap(i -> range(i+1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
    }

    public static<A, B> Stream<Pair<A, B>> allPairs(List<A> l1, List<B> l2) {
        return range(0, l1.size()).boxed().flatMap(i -> l2.stream().map(b -> new Pair<>(l1.get(i), b)));
    }
}
