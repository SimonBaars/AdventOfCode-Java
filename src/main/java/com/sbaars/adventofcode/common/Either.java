package com.sbaars.adventofcode.common;

import java.util.Objects;
import java.util.Optional;

public record Either<A, B> (Optional<A> a, Optional<B> b) {
    public Either(A a, B b){
        this(Optional.ofNullable(a), Optional.ofNullable(b));
    }

    public A getA() {
        return a.get();
    }

    public B getB() {
        return b.get();
    }

    public boolean isA(){
        return a.isPresent();
    }

    public boolean isB(){
        return b.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Either<?, ?> either = (Either<?, ?>) o;

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
