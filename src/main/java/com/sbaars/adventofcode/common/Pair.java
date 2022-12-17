package com.sbaars.adventofcode.common;

import java.util.function.BiFunction;

public record Pair<A, B>(A a, B b) {

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair(a, b);
    }

    public A getLeft() {
        return a;
    }

    public B getRight() {
        return b;
    }

    public<C, D> Pair<C, D> map(BiFunction<A, B, Pair<C, D>> func) {
        return func.apply(a(), b());
    }
}
