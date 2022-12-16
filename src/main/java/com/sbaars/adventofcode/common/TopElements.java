package com.sbaars.adventofcode.common;

import java.util.*;

public class TopElements<E> extends PriorityQueue<E> {
    private final int capacity;
    public TopElements(int capacity, Comparator<? super E> comparator) {
        super(capacity, comparator);
        this.capacity = capacity;
    }

    public TopElements(int capacity, Collection<? extends E> c, Comparator<? super E> comparator) {
        super(capacity, comparator);
        this.capacity = capacity;
        addAll(c);
    }

    @Override
    public boolean add(E e) {
        return size() <= capacity && super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for(E e : c) {
            if(add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    public Set<E> toSet() {
        return new HashSet<>(this);
    }
}
