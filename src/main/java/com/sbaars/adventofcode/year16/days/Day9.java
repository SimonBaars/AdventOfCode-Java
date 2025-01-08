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

  private long decompressV1(String input) {
    long length = 0;
    int pos = 0;

    while (pos < input.length()) {
      if (input.charAt(pos) == '(') {
        Matcher m = MARKER_PATTERN.matcher(input.substring(pos));
        if (m.find() && m.start() == 0) {
          int chars = Integer.parseInt(m.group(1));
          int repeat = Integer.parseInt(m.group(2));
          pos += m.end();
          length += (long) chars * repeat;
          pos += chars;
        } else {
          length++;
          pos++;
        }
      } else {
        length++;
        pos++;
      }
    }

    return length;
  }

  private long decompressV2(String input) {
    if (input.isEmpty()) {
      return 0;
    }

    long length = 0;
    int pos = 0;

    while (pos < input.length()) {
      if (input.charAt(pos) == '(') {
        Matcher m = MARKER_PATTERN.matcher(input.substring(pos));
        if (m.find() && m.start() == 0) {
          int chars = Integer.parseInt(m.group(1));
          int repeat = Integer.parseInt(m.group(2));
          pos += m.end();
          String repeatedSection = input.substring(pos, pos + chars);
          length += decompressV2(repeatedSection) * repeat;
          pos += chars;
        } else {
          length++;
          pos++;
        }
      } else {
        length++;
        pos++;
      }
    }

    return length;
  }

  @Override
  public Object part1() {
    return decompressV1(day().trim());
  }

  @Override
  public Object part2() {
    return decompressV2(day().trim());
  }
}
