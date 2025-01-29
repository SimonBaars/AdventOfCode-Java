package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.stream.IntStream;

public class Day11 extends Day2015 {

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    Day11 day = new Day11();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 11, 1);
    new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 11, 2);
  }

  @Override
  public Object part1() {
    return findNextPassword(day().trim());
  }

  @Override
  public Object part2() {
    return findNextPassword(findNextPassword(day().trim()));
  }

  private String findNextPassword(String password) {
    char[] chars = password.toCharArray();
    do {
      increment(chars);
    } while (!isValid(chars));
    return new String(chars);
  }

  private void increment(char[] chars) {
    int i = chars.length - 1;
    while (i >= 0) {
      if (chars[i] == 'z') {
        chars[i] = 'a';
        i--;
      } else {
        chars[i]++;
        // Skip i, o, l
        if (chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'l') {
          chars[i]++;
        }
        break;
      }
    }
  }

  private boolean isValid(char[] chars) {
    return hasStraight(chars) && !hasInvalidChars(chars) && hasTwoPairs(chars);
  }

  private boolean hasStraight(char[] chars) {
    return IntStream.range(0, chars.length - 2)
        .anyMatch(i -> chars[i + 1] == chars[i] + 1 && chars[i + 2] == chars[i] + 2);
  }

  private boolean hasInvalidChars(char[] chars) {
    return new String(chars).matches(".*[iol].*");
  }

  private boolean hasTwoPairs(char[] chars) {
    int pairs = 0;
    for (int i = 0; i < chars.length - 1; i++) {
      if (chars[i] == chars[i + 1]) {
        pairs++;
        i++; // Skip the next character to avoid overlapping
      }
    }
    return pairs >= 2;
  }
}
