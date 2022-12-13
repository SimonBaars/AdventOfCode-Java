package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sbaars.adventofcode.util.AOCUtils.verify;

public interface ReadsFormattedString {
  static <T> T readString(String s, String pattern, Class<T> target) {
    List<Object> mappedObjs = new ArrayList<>();
    while (s.length() > 0) {
      if (pattern.length() > 1 && pattern.charAt(0) == '%') {
        int size = mappedObjs.size();
        switch ( pattern.charAt( 1 ) )
        {
          case 'n' -> mappedObjs.add(crunchNumber(s, pattern));
          case 'c' -> mappedObjs.add(s.charAt(0));
          case 's' -> mappedObjs.add(crunchString(s, pattern));
        }
        if (mappedObjs.size() != size) {
          s = s.substring(mappedObjs.get(size).toString().length());
          pattern = pattern.substring(2);
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
      return (T) Arrays.stream(target.getConstructors()).filter(c -> c.getParameterCount() == mappedObjs.size()).findAny().get()//.getConstructor(mappedObjs.stream().map(Object::getClass).toArray(Class[]::new))
          .newInstance(mappedObjs.toArray());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static long crunchNumber(String s, String pattern) {
    return Long.parseLong(crunchString(s, pattern));
  }

  static String crunchString(String s, String pattern) {
    return pattern.length() > 2 ? s.substring(0, s.indexOf(pattern.charAt(2))) : s;
  }

  default int i(long n) {
    return Math.toIntExact(n);
  }
}
