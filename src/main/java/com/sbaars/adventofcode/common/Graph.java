package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.common.map.ListMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public record Graph<T> (Map<T, Node<T>> nodes) {
    public Graph(ListMap<T, T> map) {
        this(Stream.concat(map.keySet().stream(), map.valueStream()).distinct().map(Node::new).collect(Collectors.toMap(Node::data, a -> a)));
        map.forEach((a, b) -> nodes.get(a).children.addAll(b.stream().map(nodes::get).toList()));
        map.forEach((a, b) -> b.forEach(c -> nodes.get(c).parents.add(nodes.get(a))));
    }

    public static<T> Collector<Pair<T, T>, ListMap<T, T>, Graph<T>> toGraph() {
        final Supplier<ListMap<T, T>> supplier = ListMap::new;
        final BiConsumer<ListMap<T, T>, Pair<T, T>> accumulator = (a, b) -> a.addTo(b.a(), b.b());
        final BinaryOperator<ListMap<T, T>> combiner = ListMap::mergeWith;
        final Function<ListMap<T, T>, Graph<T>> finisher = Graph::new;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    public Collection<Node<T>> getNodes() {
        return nodes.values();
    }

    public Stream<Node<T>> stream() {
        return getNodes().stream();
    }

    public record Node<V> (V data, List<Node<V>> children, List<Node<V>> parents) implements Comparable<Node<V>> {
        public Node(V data) {
            this(data, new ArrayList<>(), new ArrayList<>());
        }

        public List<Node<V>> children() {
            return children;
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

        @Override
        public int compareTo(Node<V> t) {
            if(data instanceof Comparable && t.data instanceof Comparable) {
                return ((Comparable)data).compareTo(t.data);
            }
            return 0;
        }
    }
}
