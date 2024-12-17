package com.sbaars.adventofcode.util;

import com.google.mu.util.stream.BiStream;
import com.sbaars.adventofcode.common.*;

import java.util.*;
import java.util.stream.Stream;

import static com.google.mu.util.stream.BiStream.toBiStream;

public class MuUtils {
  public static <A> BiStream<A, A> connectedPairs(Stream<A> l) {
    return AoCUtils.connectedPairs(l).collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B), (B,C)]
  public static <A> BiStream<A, A> connectedPairs(List<A> l) {
    return AoCUtils.connectedPairs(l).collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B)]
  public static <A> BiStream<A, A> pairs(List<A> l) {
    return AoCUtils.pairs(l).collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] -> [(A,B), (A,C), (B,C)]
  public static <A> BiStream<A, A> allPairs(List<A> l) {
    return AoCUtils.allPairs(l).collect(toBiStream(Pair::a, Pair::b));
  }

  // [A,B,C] [D,E,F] -> [(A,D), (A,E), (A,F), (B,D), (B,E), (B,F), (C,D), (C,E), (C,F)]
  public static <A, B> BiStream<A, B> allPairs(List<A> l1, List<B> l2) {
    return AoCUtils.allPairs(l1, l2).collect(toBiStream(Pair::a, Pair::b));
  }

  public static <A, B> BiStream<A, B> zip(Stream<? extends A> a, Stream<? extends B> b) {
    return AoCUtils.zip(a, b).collect(toBiStream(Pair::a, Pair::b));
  }

  public static <A> BiStream<Integer, A> zipWithIndex(Stream<? extends A> a) {
    return AoCUtils.zipWithIndex(a).collect(toBiStream(IndexedElement::i, IndexedElement::e));
  }
}
