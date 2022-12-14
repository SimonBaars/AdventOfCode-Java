package com.sbaars.adventofcode.common;

import java.util.Objects;

public record Pair<A, B>(A a, B b) {

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> either = (Pair<?, ?>) o;

        if (!Objects.equals(a, either.a)) return false;
        return Objects.equals(b, either.b);
    }

    public A getLeft() {
        return a;
    }

    public B getRight() {
        return b;
    }
}
