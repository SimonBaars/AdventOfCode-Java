package com.sbaars.adventofcode.common;

import java.util.Objects;

public class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }

    public static<A, B> Pair<A, B> of(A a, B b) {
        return new Pair(a, b);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> either = (Pair<?, ?>) o;

        if (!Objects.equals(a, either.a)) return false;
        return Objects.equals(b, either.b);
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
