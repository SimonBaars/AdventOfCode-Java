package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Tuple.of;
import static com.sbaars.adventofcode.util.AOCUtils.verify;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.Optional.of;

public interface ReadsFormattedString {
  static <T> T readString(String s, String pattern, Class<T> target, Class<?>...nested) {
    return readString(s, pattern, ",", target, nested);
  }

  static <T> T readString(String s, String pattern, String listSeparator, Class<T> target, Class<?>...nested) {
    List<Object> mappedObjs = new ArrayList<>();
    int listIndex = 0;
    while (s.length() > 0) {
      if (pattern.length() > 1 && pattern.charAt(0) == '%') {
        char c = pattern.charAt(1);
        var data = crunch(s, pattern, listSeparator, c == 'l' && pattern.charAt(2) == '(' ? nested[listIndex] : null);
        if (data.isPresent()) {
          if(c == 'l' && pattern.charAt(2) == '(') listIndex++;
          var d = data.get();
          mappedObjs.add(d.a());
          s = s.substring(d.b());
          pattern = pattern.substring(d.c());
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
      verify(stream(target.getConstructors()).anyMatch(c -> c.getParameterCount() == mappedObjs.size()), "Class "+target+" has no constructor of size "+mappedObjs.size()+"!");
      return (T) stream(target.getConstructors()).filter(c -> c.getParameterCount() == mappedObjs.size()).findAny().get().newInstance(mappedObjs.toArray());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static<T> Optional<Tuple<?, Integer, Integer>> crunch(String s, String pattern, String listSeparator, Class<T> target) {
    char c = pattern.charAt(1);
    return switch (c) {
      case 'd' -> of(crunchDouble(s, pattern));
      case 'n' -> of(crunchNumber(s, pattern));
      case 'i' -> of(crunchInteger(s, pattern));
      case 'l' -> of(crunchList(s, pattern, listSeparator, target));
      case 'c' -> of(of(s.charAt(0), 1, 2));
      case 's', 'u' -> of(crunchString(s, pattern));
      default -> null;
    };
  }

  private static Tuple<Long, Integer, Integer> crunchNumber(String s, String pattern) {
    return crunchString(s, pattern).map((a, b, c) -> of(parseLong(a), b, c));
  }

  private static Tuple<Integer, Integer, Integer> crunchInteger(String s, String pattern) {
    return crunchString(s, pattern).map((a, b, c) -> of(parseInt(a), b, c));
  }

  private static Tuple<Double, Integer, Integer> crunchDouble(String s, String pattern) {
    return crunchString(s, pattern).map((a, b, c) -> of(parseDouble(a), b, c));
  }

  private static<T> Tuple<List<?>, Integer, Integer> crunchList(String s, String pattern, String listSeparator, Class<T> target) {
    char type = pattern.charAt(2);
    var mapped = crunchString(s, pattern);
    return of(stream(mapped.a().split(listSeparator)).map(String::trim).map(e ->
            switch (type) {
              case 'd' -> parseDouble(e);
              case 'n' -> parseLong(e);
              case 'i' -> parseInt(e);
              case 'c' -> e.charAt(0);
              case '(' -> readString(e, pattern.substring(3, pattern.lastIndexOf(')')), " and ", target);
              default -> e;
            }
    ).collect(Collectors.toCollection(ArrayList::new)), mapped.b(), type == '(' ? pattern.lastIndexOf(')') + 1 : 3);
  }

  private static Tuple<String, Integer, Integer> crunchString(String s, String pattern) {
    if(pattern.length()<=2) return of(s, s.length(), 2);
    int next = pattern.indexOf('%', 2);
    String substring = pattern.substring(pattern.charAt(1) == 'l' ? 3 : 2, next == -1 ? pattern.length() : next);
    int end = substring.isEmpty() ? s.length() : s.indexOf(substring);
    verify(end != -1, "Malformatted pattern ("+pattern+") for string ("+s+")");
    return Tuple.of(s.substring(0, end), end, 2);
  }
}
