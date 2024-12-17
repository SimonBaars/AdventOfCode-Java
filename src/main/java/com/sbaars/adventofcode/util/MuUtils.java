package com.sbaars.adventofcode.util;

import com.google.mu.util.stream.BiStream;
import com.sbaars.adventofcode.common.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.mu.util.stream.BiStream.toBiStream;
import static com.sbaars.adventofcode.common.MutableElement.mutable;
import static com.sbaars.adventofcode.common.Pair.pair;
import static java.util.Objects.requireNonNull;
import static java.util.Spliterators.iterator;
import static java.util.Spliterators.spliterator;
import static java.util.stream.IntStream.range;

public class MuUtils {
    public static <A> BiStream<A, A> connectedPairs(Stream<A> l) {
    MutableElement<A> m = mutable();
    return l.map(e -> {
      var p = new Pair<>(m.get(), e);
      m.set(e);
      return p;
    }).filter(p -> p.a() != null).collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B), (B,C)]
  public static <A> BiStream<A, A> connectedPairs(List<A> l) {
    return range(1, l.size()).mapToObj(i -> pair(l.get(i - 1), l.get(i)))
        .collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B)]
  public static <A> BiStream<A, A> pairs(List<A> l) {
    return range(0, l.size() / 2).mapToObj(i -> new Pair<>(l.get(i * 2), l.get(i * 2 + 1)))
        .collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B), (A,C), (B,C)]
  public static <A> BiStream<A, A> allPairs(List<A> l) {
    return range(0, l.size()).boxed().flatMap(i -> range(i + 1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))))
        .collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] [D,E,F] -> [(A,D), (A,E), (A,F), (B,D), (B,E), (B,F), (C,D), (C,E), (C,F)]
  public static <A, B> BiStream<A, B> allPairs(List<A> l1, List<B> l2) {
    return range(0, l1.size()).boxed().flatMap(i -> l2.stream().map(b -> new Pair<>(l1.get(i), b)))
        .collect(toBiStream(Pair::a, Pair::b));
  }

  public static <E> E last(List<E> list) {
    return list.get(list.size() - 1);
  }

  public static <A, B> BiStream<A, B> zip(Stream<? extends A> a, Stream<? extends B> b) {
    return zip(a, b, Pair::new).collect(toBiStream(Pair::a, Pair::b));
  }

  public static <A> Stream<IndexedElement<A>> zipWithIndex(Stream<? extends A> a) {
    return zip(range(0, Integer.MAX_VALUE).boxed(), a, IndexedElement::new);
  }

  public static <A, B, C> Stream<C> zip(Stream<? extends A> a, Stream<? extends B> b, BiFunction<? super A, ? super B, ? extends C> zipper) {
    requireNonNull(zipper);
    Spliterator<? extends A> aSpliterator = requireNonNull(a).spliterator();
    Spliterator<? extends B> bSpliterator = requireNonNull(b).spliterator();

    int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
        ~(Spliterator.DISTINCT | Spliterator.SORTED);

    long zipSize = ((characteristics & Spliterator.SIZED) != 0)
        ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
        : -1;

    Iterator<A> aIterator = iterator(aSpliterator);
    Iterator<B> bIterator = iterator(bSpliterator);
    Iterator<C> cIterator = new Iterator<>() {
      @Override
      public boolean hasNext() {
        return aIterator.hasNext() && bIterator.hasNext();
      }

      @Override
      public C next() {
        return zipper.apply(aIterator.next(), bIterator.next());
      }
    };

    Spliterator<C> split = spliterator(cIterator, zipSize, characteristics);
    return (a.isParallel() || b.isParallel())
        ? StreamSupport.stream(split, true)
        : StreamSupport.stream(split, false);
  }
}
