package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.stream.Stream;
import com.sbaars.adventofcode.network.Submit;

public class Day8 extends Day2015 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    Day8 day = new Day8();
    day.printParts();
    Submit submit = new Submit();
    submit.submit(day.part1(), 2015, 8, 1);
    submit.submit(day.part2(), 2015, 8, 2);
  }

  @Override
  public Object part1() {
    return dayStream().mapToInt(s -> s.length() - countMemoryChars(s)).sum();
  }

  @Override
  public Object part2() {
    return dayStream().mapToInt(s -> countEncodedChars(s) - s.length()).sum();
  }

  private int countMemoryChars(String s) {
    // Remove quotes at start and end
    String content = s.substring(1, s.length() - 1);
    int count = 0;
    for (int i = 0; i < content.length(); i++) {
      if (content.charAt(i) == '\\') {
        if (i + 1 < content.length()) {
          char next = content.charAt(i + 1);
          if (next == '\\' || next == '"') {
            i++; // Skip the escaped character
          } else if (next == 'x' && i + 3 < content.length()) {
            i += 3; // Skip the hex sequence
          }
        }
      }
      count++;
    }
    return count;
  }

  private int countEncodedChars(String s) {
    int count = 2; // Opening and closing quotes
    for (char c : s.toCharArray()) {
      if (c == '"' || c == '\\') {
        count += 2; // Each quote or backslash needs to be escaped
      } else {
        count++; // Regular character
      }
    }
    return count;
  }

  protected Stream<String> dayStream() {
    return day().lines();
  }
}
