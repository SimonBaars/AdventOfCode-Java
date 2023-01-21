package com.sbaars.adventofcode.common;

public record IndexedElement<A>(int i, A e) implements Comparable<IndexedElement<A>> {
    @Override
    public int compareTo(IndexedElement<A> t) {
        if(e instanceof Comparable && t.e instanceof Comparable) {
            return ((Comparable)e).compareTo(t.e);
        }
        return 0;
    }
}
