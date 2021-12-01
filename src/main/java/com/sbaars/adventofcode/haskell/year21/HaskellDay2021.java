package com.sbaars.adventofcode.haskell.year21;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import com.sbaars.adventofcode.year21.Day2021;
import java.util.List;
import java.util.stream.Collector;

public abstract class HaskellDay2021 extends Day2021 {
  protected HaskellDay2021(int day) {
    super(day);
  }

  protected Collector<CharSequence, ?, String> haskellList() {
    return joining(", ", "[", "]");
  }

  protected String tuple(Object... elements) {
    return stream(elements).map(Object::toString).collect(joining(", ", "(", ")"));
  }

  protected String tup(Object a, Object b) {
    return "(" + convert(a) + ", " + convert(b) + ")";
  }

  protected String convert(Object[] a) {
    return stream(a).map(this::convert).collect(haskellList());
  }

  protected String convert(List<?> a) {
    return a.stream().map(this::convert).collect(haskellList());
  }

  protected String convert(Object a) {
    if (a instanceof Number) {
      return a.toString();
    } else if (a instanceof Character) {
      return "'" + a + "'";
    }
    return "\"" + a + "\"";
  }
}
