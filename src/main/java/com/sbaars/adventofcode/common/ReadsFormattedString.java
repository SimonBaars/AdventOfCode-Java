package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sbaars.adventofcode.common.Pair.of;
import static com.sbaars.adventofcode.util.AOCUtils.verify;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.Optional.of;

public interface ReadsFormattedString {
  static <T> T readString(String s, String pattern, Class<T> target) {
    return readString(s, pattern, ",", target);
  }

  static <T> T readString(String s, String pattern, String listSeparator, Class<T> target) {
    List<Object> mappedObjs = new ArrayList<>();
    while (s.length() > 0) {
      if (pattern.length() > 1 && pattern.charAt(0) == '%') {
        char c = pattern.charAt(1);
        var data = crunch(s, pattern, listSeparator);
        if (data.isPresent()) {
          var d = data.get();
          mappedObjs.add(d.a());
          s = s.substring(d.b());
          pattern = pattern.substring(c == 'l' ? 3 : 2);
          if(c == 'u') mappedObjs.remove(mappedObjs.size()-1);
          continue;
        }
      }
      if (pattern.charAt(0) == s.charAt(0)) {
        s = s.substring(1);
        pattern = pattern.substring(1);
      } else {
        throw new IllegalStateException("Illegal crunch, pattern = " + pattern + " and s = " + s);
      }
    }
    try {
      verify(target.getConstructors().length > 0, "Class "+target+" has no constructor!");
      return (T) stream(target.getConstructors()).filter(c -> c.getParameterCount() == mappedObjs.size()).findAny().get().newInstance(mappedObjs.toArray());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Optional<Pair<? extends Object, Integer>> crunch(String s, String pattern, String listSeparator) {
    char c = pattern.charAt(1);
    return switch (c) {
      case 'd' -> of(crunchDouble(s, pattern));
      case 'n' -> of(crunchNumber(s, pattern));
      case 'i' -> of(crunchInteger(s, pattern));
      case 'l' -> of(crunchList(s, pattern, listSeparator));
      case 'c' -> of(of(s.charAt(0), 1));
      case 's', 'u' -> of(crunchString(s, pattern));
      default -> null;
    };
  }

  private static Pair<Long, Integer> crunchNumber(String s, String pattern) {
    return crunchString(s, pattern).map((a, b) -> of(parseLong(a), b));
  }

  private static Pair<Integer, Integer> crunchInteger(String s, String pattern) {
    return crunchString(s, pattern).map((a, b) -> of(parseInt(a), b));
  }

  private static Pair<Double, Integer> crunchDouble(String s, String pattern) {
    return crunchString(s, pattern).map((a, b) -> of(parseDouble(a), b));
  }

  private static Pair<List<?>, Integer> crunchList(String s, String pattern, String listSeparator) {
    char type = pattern.charAt(2);
    var mapped = crunchString(s, pattern);
    return of(stream(mapped.a().split(listSeparator)).map(String::trim).map(e ->
            switch (type) {
              case 'd' -> parseDouble(e);
              case 'n' -> parseLong(e);
              case 'i' -> parseInt(e);
              case 'c' -> e.charAt(0);
              default -> e;
            }
    ).toList(), mapped.b());
  }

  private static Pair<String, Integer> crunchString(String s, String pattern) {
    if(pattern.length()<=2) return of(s, s.length());
    int next = pattern.indexOf('%', 2);
    String substring = pattern.substring(pattern.charAt(1) == 'l' ? 3 : 2, next == -1 ? pattern.length() : next);
    int end = substring.isEmpty() ? s.length() : s.indexOf(substring);
    verify(end != -1, "Malformatted pattern ("+pattern+") for string ("+s+")");
    return of(s.substring(0, end), end);
  }
}
