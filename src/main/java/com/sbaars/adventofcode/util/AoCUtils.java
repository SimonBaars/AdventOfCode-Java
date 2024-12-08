package com.sbaars.adventofcode.util;

import com.sbaars.adventofcode.common.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.sbaars.adventofcode.common.MutableElement.mutable;
import static com.sbaars.adventofcode.common.Pair.pair;
import static java.lang.Long.MAX_VALUE;
import static java.util.stream.IntStream.range;

public class AoCUtils {
  public static void verify(boolean b) {
    verify(b, "Something went wrong");
  }

  public static void verify(boolean b, String message) {
    if (!b) {
      throw new IllegalStateException(message);
    }
  }

  public static <A> A findWhere(Stream<A> l, Predicate<A> condition) {
    return l.filter(condition).findAny().get();
  }

  public static <A> A fixedPoint(A value, UnaryOperator<A> func) {
    return fixedPoint(value, func, MAX_VALUE);
  }

  public static <A> A fixedPoint(A value, UnaryOperator<A> func, long limit) {
    A a = func.apply(value);
    A a2;
    for(long i = 0; !(a2 = func.apply(a)).equals(a) && i<limit-1; i++) {
      a = a2;
    }
    return a;
  }

  public static <A> Stream<A> appendWhile(UnaryOperator<A> func, Predicate<A> pred, A start) {
    return Stream.iterate(start, func).takeWhile(pred);
  }

  public static <A> A findMax(Collection<A> l, ToLongFunction<A> condition) {
    A res = null;
    long max = Long.MIN_VALUE;
    for (A a : l) {
      long val = condition.applyAsLong(a);
      if (val > max) {
        max = val;
        res = a;
      }
    }
    return res;
  }

  public static <A> Stream<Pair<A, A>> connectedPairs(Stream<A> l) {
    MutableElement<A> m = mutable();
    return l.map(e -> {
      var p = new Pair<>(m.get(), e);
      m.set(e);
      return p;
    }).filter(p -> p.a() != null);
  }

  public static <A> Stream<Pair<A, A>> connectedPairs(List<A> l) {
    return range(1, l.size()).mapToObj(i -> pair(l.get(i - 1), l.get(i)));
  }

  public static <A> Stream<Pair<A, A>> pairs(List<A> l) {
    return range(0, l.size() / 2).mapToObj(i -> new Pair<>(l.get(i * 2), l.get(i * 2 + 1)));
  }

  public static <A> Stream<Pair<A, A>> allPairs(List<A> l) {
    return range(0, l.size()).boxed().flatMap(i -> range(i + 1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
  }

  public static <A, B> Stream<Pair<A, B>> allPairs(List<A> l1, List<B> l2) {
    return range(0, l1.size()).boxed().flatMap(i -> l2.stream().map(b -> new Pair<>(l1.get(i), b)));
  }

  public static <E> E last(List<E> list) {
    return list.get(list.size() - 1);
  }

  public static <A, B> Stream<Pair<A, B>> zip(Stream<? extends A> a, Stream<? extends B> b) {
    return zip(a, b, Pair::new);
  }

  public static <A> Stream<IndexedElement<A>> zipWithIndex(Stream<? extends A> a) {
    return zip(range(0, Integer.MAX_VALUE).boxed(), a, IndexedElement::new);
  }

  public static <A, B, C> Stream<C> zip(Stream<? extends A> a, Stream<? extends B> b, BiFunction<? super A, ? super B, ? extends C> zipper) {
    Objects.requireNonNull(zipper);
    Spliterator<? extends A> aSpliterator = Objects.requireNonNull(a).spliterator();
    Spliterator<? extends B> bSpliterator = Objects.requireNonNull(b).spliterator();

    int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
        ~(Spliterator.DISTINCT | Spliterator.SORTED);

    long zipSize = ((characteristics & Spliterator.SIZED) != 0)
        ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
        : -1;

    Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
    Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
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

    Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
    return (a.isParallel() || b.isParallel())
        ? StreamSupport.stream(split, true)
        : StreamSupport.stream(split, false);
  }

  public static long binarySearch(Function<Long, Long> testFunction, long target, long low, long high) {
    while (true) {
      if (low == high) return low;
      long size = (high - low) / 3;
      long l1 = low + size;
      long res1 = testFunction.apply(l1);
      long diff1 = Math.abs(res1 - target);
      if (diff1 == 0) return l1;
      long l2 = l1 + size;
      long res2 = testFunction.apply(l2);
      long diff2 = Math.abs(res2 - target);
      if (diff2 == 0) return l2;
      if (diff1 == Long.MAX_VALUE && diff2 == Long.MAX_VALUE) high = l1 - 1;
      else {
        if (diff1 <= diff2) high = l2 - 1;
        if (diff1 >= diff2) low = l1 + 1;
      }
    }
  }

  public static long binarySearch(Function<Long, Long> testFunction, long low, long high) {
    return binarySearch(testFunction, 0, low, high);
  }

  public static long binarySearch(Function<Long, Long> testFunction) {
    return binarySearch(testFunction, 0, 0, Long.MAX_VALUE);
  }

  public static long safeMultiply(long a, long b) {
    long res = a * b;
    if (b != 0 && res / b != a) {
      throw new IllegalStateException("It is not safe to multiply " + a + " by " + b + ": long overflow will occur");
    }
    return res;
  }

  public static long safeAdd(long a, long b) {
    long res = a + b;
    if ((b < 0 && res > a) || (b > 0 && res < a)) {
      throw new IllegalStateException("It is not safe to add " + a + " by " + b + ": long overflow will occur");
    }
    return res;
  }

  public static long safeSubtract(long a, long b) {
    long res = a - b;
    if ((b > 0 && res > a) || (b < 0 && res < a)) {
      throw new IllegalStateException("It is not safe to subtract " + a + " by " + b + ": long overflow will occur");
    }
    return res;
  }

  public static EitherList<String, Long> alternating(String s) {
    EitherList<String, Long> either = new EitherList<>();
    while (!s.isEmpty()) {
      boolean digit = Character.isDigit(s.charAt(0));
      int i;
      for (i = 0; i < s.length() && digit == Character.isDigit(s.charAt(i)); i++) ;
      String substring = s.substring(0, i);
      either.add(new Either<>(!digit ? substring : null, digit ? Long.parseLong(substring) : null));
      s = s.substring(i);
    }
    return either;
  }

  public static <A, B> A findReduce(Stream<A> chars, B acc, BiFunction<A, B, B> func, Predicate<B> pred) {
    MutablePair<A, B> pair = new MutablePair<>(null, acc);
    return chars.peek(p -> pair.set(p, func.apply(p, pair.b()))).filter(p -> pred.test(pair.b())).findFirst().get();
  }

  public static long gcd(long a, long b) {
    while (b != 0) {
        long temp = b;
        b = a % b;
        a = temp;
    }
    return a;
  }
}
