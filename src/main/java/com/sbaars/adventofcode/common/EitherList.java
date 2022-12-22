package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class EitherList<A, B> extends ArrayList<Either<A, B>> {
    public void forEach(Consumer<A> r1, Consumer<B> r2) {
        for(Either<A, B> either : this) {
            if(either.isA()) {
                r1.accept(either.getA());
            } else {
                r2.accept(either.getB());
            }
        }
    }

    public<C> Stream<C> stream(Function<A, C> r1, Function<B, C> r2) {
        List<C> cs = new ArrayList<>();
        for(Either<A, B> either : this) {
            if(either.isA()) {
                cs.add(r1.apply(either.getA()));
            } else {
                cs.add(r2.apply(either.getB()));
            }
        }
        return cs.stream();
    }

    public<C> C reduce(C c, BiFunction<A, C, C> r1, BiFunction<B, C, C> r2) {
        for(Either<A, B> either : this) {
            if(either.isA()) {
                c = r1.apply(either.getA(), c);
            } else {
                c = r2.apply(either.getB(), c);
            }
        }
        return c;
    }
}
