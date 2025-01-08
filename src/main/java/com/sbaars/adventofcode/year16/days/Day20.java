package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 extends Day2016 {
  private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+)-(\\d+)");

  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  private static class Range implements Comparable<Range> {
    final long start;
    final long end;

    Range(long start, long end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public int compareTo(Range other) {
      return Long.compare(this.start, other.start);
    }
  }

  private long findLowestUnblockedIP() {
    List<Range> ranges = new ArrayList<>();
    for (String line : dayStream().toList()) {
      Matcher m = RANGE_PATTERN.matcher(line);
      if (m.matches()) {
        ranges.add(new Range(
          Long.parseLong(m.group(1)),
          Long.parseLong(m.group(2))
        ));
      }
    }

    ranges.sort(null);
    long current = 0;

    for (Range range : ranges) {
      if (current < range.start) {
        return current;
      }
      current = Math.max(current, range.end + 1);
    }

    return current;
  }

  @Override
  public Object part1() {
    return findLowestUnblockedIP();
  }

  @Override
  public Object part2() {
    return "";
  }
}
