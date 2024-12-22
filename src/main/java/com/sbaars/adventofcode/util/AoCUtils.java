package com.sbaars.adventofcode.util;

import com.sbaars.adventofcode.common.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.function.TriFunction;

import static com.sbaars.adventofcode.common.MutableElement.mutable;
import static com.sbaars.adventofcode.common.Pair.pair;
import static java.lang.Long.MAX_VALUE;
import static java.util.Objects.requireNonNull;
import static java.util.Spliterators.iterator;
import static java.util.Spliterators.spliterator;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.iterate;

public class AoCUtils {
  public static void verify(boolean b) {
    verify(b, "Something went wrong");
  }

  public static void verify(boolean b, String message) {
    if (!b) {
      throw new IllegalStateException(message);
    }
  }

  public static <A> A doTimes(A start, int times, UnaryOperator<A> func) {
    return iterate(start, func).limit(times + 1).reduce((first, second) -> second).orElse(start);
  }

  public static <A> Stream<A> applySelf(A start, int times, UnaryOperator<A> func) {
    return iterate(start, func).limit(times + 1);
  }

  public static <A> A findWhere(Stream<A> l, Predicate<A> condition) {
    return l.filter(condition).findAny().get();
  }

  public static <A> A fixedPoint(A value, UnaryOperator<A> func) {
    return fixedPoint(value, func, MAX_VALUE);
  }

  public static <A> void alternating(Stream<A> stream, Consumer<A> func1, Consumer<A> func2) {
    zipWithIndex(stream).forEach(p -> {
      if (p.i() % 2 == 0) {
        func1.accept(p.e());
      } else {
        func2.accept(p.e());
      }
    });
  }

  public static <A, B> A recurse(A value, B seed, TriFunction<A, Queue<B>, B, A> func) {
    var stack = new ArrayDeque<B>();
    stack.push(seed);
    return recurseQueue(value, stack, func);
  }

  public static <A, B> A recurseQueue(A value, Queue<B> stack, TriFunction<A, Queue<B>, B, A> func) {
    A next = null;
    while (!stack.isEmpty()) {
      B current = stack.poll();
      next = func.apply(value, stack, current);
    }
    return next;
  }

  public static <B> B recurse(B seed, Predicate<B> accept, BiConsumer<Queue<B>, B> func) {
    var stack = new ArrayDeque<B>();
    stack.push(seed);
    while (!stack.isEmpty()) {
      B current = stack.poll();
      if (accept.test(current)) {
        return current;
      }
      func.accept(stack, current);
    }
    throw new IllegalStateException("No solution found");
  }

  public static <A> A fixedPoint(A value, UnaryOperator<A> func, long limit) {
    A a = value;
    A a2 = func.apply(a);
    for (long i = 0; !a2.equals(a) && i < limit - 1; i++) {
      a = a2;
      a2 = func.apply(a);
    }
    return a2;
  }

  public static <A> Stream<A> appendWhile(UnaryOperator<A> func, Predicate<A> pred, A start) {
    return iterate(start, func).takeWhile(pred);
  }

  public static <A> Stream<A> transformStream(Stream<A> inputStream, UnaryOperator<Stream<A>> transformFunction, Predicate<A> predicate) {
    Stream<A> currentStream = inputStream;
    Stream.Builder<A> outputBuilder = Stream.builder();

    while (true) {
      currentStream = transformFunction.apply(currentStream);
      List<A> currentList = currentStream.toList();

      if (currentList.isEmpty()) {
        break;
      }

      currentList.stream().filter(predicate).forEach(outputBuilder::add);
      currentStream = currentList.stream().filter(a -> !predicate.test(a));
    }
    return outputBuilder.build();
  }

  public static <A, B> B split(Stream<A> stream, BiFunction<Stream<A>, Stream<A>, B> zipper) {
    List<A> list = stream.toList();
    return zipper.apply(list.stream(), list.stream());
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

  public static <A> Stream<List<A>> window(Stream<A> stream, int size) {
    List<A> list = stream.toList();
    return range(0, list.size() - size + 1).mapToObj(start -> list.subList(start, start + size));
  }

  public static <A> Stream<Pair<A, A>> connectedPairs(Stream<A> l) {
    MutableElement<A> m = mutable();
    return l.map(e -> {
      var p = new Pair<>(m.get(), e);
      m.set(e);
      return p;
    }).filter(p -> p.a() != null);
  }

  // [A,B,C] -> [(A,B), (B,C)]
  public static <A> Stream<Pair<A, A>> connectedPairs(List<A> l) {
    return range(1, l.size()).mapToObj(i -> pair(l.get(i - 1), l.get(i)));
  }

  // [A,B,C] -> [(A,B)]
  public static <A> Stream<Pair<A, A>> pairs(List<A> l) {
    return range(0, l.size() / 2).mapToObj(i -> new Pair<>(l.get(i * 2), l.get(i * 2 + 1)));
  }

  // [A,B,C] -> [(A,B), (A,C), (B,C)]
  public static <A> Stream<Pair<A, A>> allPairs(List<A> l) {
    return range(0, l.size()).boxed().flatMap(i -> range(i + 1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
  }

  // [A,B,C] [D,E,F] -> [(A,D), (A,E), (A,F), (B,D), (B,E), (B,F), (C,D), (C,E), (C,F)]
  public static <A, B> Stream<Pair<A, B>> allPairs(List<A> l1, List<B> l2) {
    return range(0, l1.size()).boxed().flatMap(i -> l2.stream().map(b -> new Pair<>(l1.get(i), b)));
  }

  public static <A, B> Stream<Pair<A, B>> zip(Stream<? extends A> a, Stream<? extends B> b) {
    return zip(a, b, Pair::new);
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
      either.add(new Either<String, Long>(!digit ? substring : null, digit ? Long.parseLong(substring) : null));
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

  public static AtomicLong al() {
    return new AtomicLong();
  }
}
