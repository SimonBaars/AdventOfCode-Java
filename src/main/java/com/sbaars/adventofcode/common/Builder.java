package com.sbaars.adventofcode.common;

import java.util.Arrays;
import java.util.function.Supplier;

public class Builder<E> {
    private E e;
    private E newE;

    public Builder(E e) {
        this.e = e;
    }

    public Builder(Class<E> c) {
        try {
            this.e = (E) Arrays.stream(c.getConstructors()).filter(e -> e.getParameterCount() == 0).findAny().orElseThrow().newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public Builder(Supplier<E> s) {
        this.e = s.get();
    }

    public E get() {
        return e;
    }
}
