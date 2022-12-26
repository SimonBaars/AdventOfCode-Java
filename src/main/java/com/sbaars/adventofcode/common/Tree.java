package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class Tree<T> {
    public enum TreeTraversal {CONTINUE, DOWN, UP}
    private final Node<T> rootNode;

    public Tree(T rootNodeData) {
        this.rootNode = new Node<>(rootNodeData);
    }

    public Tree(T root, ListMap<T, T> map) {
        this.rootNode = new Node<>(root, map.get(root), map);
    }



    public static<T> Collector<Loc, Map<Loc, Character>, InfiniteGrid> toTree(Function<?, T> parentMapper, Function<?, T> childMapper) {
        final Supplier<Map<Loc, Character>> supplier = HashMap::new;
        final BiConsumer<Map<Loc, Character>, Loc> accumulator = (a, b) -> a.put(b, c);
        final BinaryOperator<Map<Loc, Character>> combiner = (a, b) -> {a.putAll(b); return a;};
        final Function<Map<Loc, Character>, InfiniteGrid> finisher = InfiniteGrid::new;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    private class Node<T> {
        final T data;
        final List<Node<T>> children;
        final Node<T> parent;

        public Node(T data, List<Node<T>> children, Node<T> parent) {
            this.data = data;
            this.children = children;
            this.parent = parent;
        }

        public Node(ListMap<T, T> map, Node<T> parent, T data) {
            this.data = data;
            this.children = children;
            this.parent = parent;
        }

        public Node(ListMap<T, T> map, T data) {
            this(map, null, data);
        }

        public Node(T data, List<Node<T>> children) {
            this(data, children, null);
        }

        public Node(T data) {
            this(data, new ArrayList<>());
        }
    }
}
