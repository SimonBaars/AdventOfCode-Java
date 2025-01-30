package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year15.Day2015;

import java.util.function.ToLongFunction;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day2 extends Day2015 {
  public Day2() {
    super(2);
  }

  public record Dimension(long l, long w, long h) {
    private long[] smallestSides() {
      if (l >= w && l >= h) return new long[]{w, h};
      if (w >= l && w >= h) return new long[]{l, h};
      return new long[]{l, w};
    }

    private long smallestArea() {
      var s = smallestSides();
      return s[0] * s[1];
    }

    private long area() {
      return 2 * (l * w + w * h + h * l);
    }

    private long wrappingPaper() {
      return smallestArea() + area();
    }

    private long sideDistance() {
      var s = smallestSides();
      return 2 * (s[0] + s[1]);
    }

    private long volume() {
      return l * w * h;
    }

    private long ribbon() {
      return sideDistance() + volume();
    }
  }

  public static void main(String[] args) {
    Day d = new Day2();
    d.downloadIfNotDownloaded();
    d.printParts();
  }

  @Override
  public Object part1() {
    return getResult(Dimension::wrappingPaper);
  }

  @Override
  public Object part2() {
    return getResult(Dimension::ribbon);
  }

  private long getResult(ToLongFunction<Dimension> func) {
    return dayStream()
        .map(s -> readString(s, "%nx%nx%n", Dimension.class))
        .mapToLong(func)
        .sum();
  }
}
