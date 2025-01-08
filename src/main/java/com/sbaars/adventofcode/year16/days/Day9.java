package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 extends Day2016 {
  private static final Pattern MARKER_PATTERN = Pattern.compile("\\((\\d+)x(\\d+)\\)");

  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  private int decompressedLength(String input) {
    int length = 0;
    int i = 0;

    while (i < input.length()) {
      if (input.charAt(i) == '(') {
        Matcher m = MARKER_PATTERN.matcher(input.substring(i));
        if (m.find() && m.start() == 0) {
          int chars = Integer.parseInt(m.group(1));
          int repeat = Integer.parseInt(m.group(2));
          i += m.end();
          length += chars * repeat;
          i += chars;
        } else {
          length++;
          i++;
        }
      } else {
        length++;
        i++;
      }
    }

    return length;
  }

  @Override
  public Object part1() {
    return decompressedLength(day().trim());
  }

  @Override
  public Object part2() {
    return "";
  }
}
