package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 extends Day2016 {
  private static final String PASSWORD = "abcdefgh";
  private static final Pattern SWAP_POSITION = Pattern.compile("swap position (\\d+) with position (\\d+)");
  private static final Pattern SWAP_LETTER = Pattern.compile("swap letter (\\w) with letter (\\w)");
  private static final Pattern ROTATE = Pattern.compile("rotate (left|right) (\\d+) steps?");
  private static final Pattern ROTATE_LETTER = Pattern.compile("rotate based on position of letter (\\w)");
  private static final Pattern REVERSE = Pattern.compile("reverse positions (\\d+) through (\\d+)");
  private static final Pattern MOVE = Pattern.compile("move position (\\d+) to position (\\d+)");

  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  private void swapPositions(StringBuilder str, int x, int y) {
    char temp = str.charAt(x);
    str.setCharAt(x, str.charAt(y));
    str.setCharAt(y, temp);
  }

  private void swapLetters(StringBuilder str, char x, char y) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == x) {
        str.setCharAt(i, y);
      } else if (str.charAt(i) == y) {
        str.setCharAt(i, x);
      }
    }
  }

  private void rotate(StringBuilder str, boolean right, int steps) {
    steps %= str.length();
    if (!right) {
      steps = str.length() - steps;
    }
    String rotated = str.substring(str.length() - steps) + str.substring(0, str.length() - steps);
    str.replace(0, str.length(), rotated);
  }

  private void rotateBasedOnLetter(StringBuilder str, char x) {
    int index = str.indexOf(String.valueOf(x));
    int steps = 1 + index + (index >= 4 ? 1 : 0);
    rotate(str, true, steps);
  }

  private void reverse(StringBuilder str, int x, int y) {
    while (x < y) {
      swapPositions(str, x++, y--);
    }
  }

  private void move(StringBuilder str, int x, int y) {
    char c = str.charAt(x);
    str.deleteCharAt(x);
    str.insert(y, c);
  }

  private String scramble(String password) {
    StringBuilder result = new StringBuilder(password);

    for (String line : dayStream().toList()) {
      Matcher m;
      if ((m = SWAP_POSITION.matcher(line)).matches()) {
        swapPositions(result, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      } else if ((m = SWAP_LETTER.matcher(line)).matches()) {
        swapLetters(result, m.group(1).charAt(0), m.group(2).charAt(0));
      } else if ((m = ROTATE.matcher(line)).matches()) {
        rotate(result, m.group(1).equals("right"), Integer.parseInt(m.group(2)));
      } else if ((m = ROTATE_LETTER.matcher(line)).matches()) {
        rotateBasedOnLetter(result, m.group(1).charAt(0));
      } else if ((m = REVERSE.matcher(line)).matches()) {
        reverse(result, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      } else if ((m = MOVE.matcher(line)).matches()) {
        move(result, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
      }
    }

    return result.toString();
  }

  @Override
  public Object part1() {
    return scramble(PASSWORD);
  }

  @Override
  public Object part2() {
    return "";
  }
}
