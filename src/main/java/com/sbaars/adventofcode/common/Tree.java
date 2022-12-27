package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;


public class Tree<T> {
    private final Node<T> rootNode;

    public Tree(T rootNodeData) {
        this.rootNode = new Node<>(rootNodeData, 0);
    }

    public Tree(ListMap<T, T> map) {
        this.rootNode = new Node<>(map, map.keySet().stream().filter(e -> !map.hasValue(e)).findAny().get(), 0);
    }

    public static<T> Collector<Pair<T, T>, ListMap<T, T>, Tree<T>> toTree() {
        final Supplier<ListMap<T, T>> supplier = ListMap::new;
        final BiConsumer<ListMap<T, T>, Pair<T, T>> accumulator = (a, b) -> a.addTo(b.a(), b.b());
        final BinaryOperator<ListMap<T, T>> combiner = ListMap::mergeWith;
        final Function<ListMap<T, T>, Tree<T>> finisher = Tree::new;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    public<A> Stream<A> forEach(BiFunction<Node<T>, Integer, A> func) {
        func.apply(rootNode, 0);
        List<A> res = new ArrayList<>();
        List<Node<T>> nodes = List.of(rootNode);
        for(int i = 0; !nodes.isEmpty(); i++) {
            for(Node<T> n : nodes) {
                res.add(func.apply(n, i));
            }
            nodes = nodes.stream().map(Node::children).flatMap(List::stream).toList();
        }
        return res.stream();
    }

    public Stream<Node<T>> stream() {
        return toList().stream();
    }

    public List<Node<T>> toList() {
        List<Node<T>> res = new ArrayList<>();
        List<Node<T>> nodes = List.of(rootNode);
        while(!nodes.isEmpty()) {
            res.addAll(nodes);
            nodes = nodes.stream().map(Node::children).flatMap(List::stream).toList();
        }
        return res;
    }

    public Node<T> get(T t) {
        return stream().filter(n -> n.data.equals(t)).findAny().get();
    }

    public int distance(Node<T> n1, Node<T> n2) {
        if(n1.equals(n2)) return 0;
        List<Node<T>> parents = n1.parents();
        parents.retainAll(n2.parents());
        if(parents.isEmpty()) return -1;
        return (n1.depth-parents.get(0).depth) + (n2.depth-parents.get(0).depth);
    }

    public class Node<T> {
        public final T data;
        public final List<Node<T>> children;
        public final Node<T> parent;
        public final int depth;
        public final int nChildren;

        public Node(T data, List<Node<T>> children, Node<T> parent, int depth) {
            this.data = data;
            this.children = children;
            this.parent = parent;
            this.nChildren = children.stream().mapToInt(e -> e.nChildren + 1).sum();
            this.depth = depth;
        }

        public Node(ListMap<T, T> map, Node<T> parent, T data, int depth) {
            this.data = data;
            this.children = map.get(data).stream().map(d -> new Node<>(map, this, d, depth + 1)).toList();
            this.parent = parent;
            this.nChildren = children.stream().mapToInt(e -> e.nChildren + 1).sum();
            this.depth = depth;
        }

        public Node(ListMap<T, T> map, T data, int depth) {
            this(map, null, data, depth);
        }

        public Node(T data, List<Node<T>> children, int depth) {
            this(data, children, null, depth);
        }

        public Node(T data, int depth) {
            this(data, new ArrayList<>(), depth);
        }

        public List<Node<T>> children() {
            return children;
        }

        public List<Node<T>> parents() {
            List<Node<T>> parents = new ArrayList<>();
            Node<T> n = this;
            while(n.parent != null) {
                parents.add(n.parent);
                n = n.parent;
            }
            return parents;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return data.equals(node.data);
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }
    }
}
