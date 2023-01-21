package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year21.Day2021;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class Day18 extends Day2021 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  @Override
  public Object part1() {
    var in = dayStream().filter(e -> !e.isEmpty()).map(this::reduce).collect(Collectors.toCollection(ArrayList::new));
    while (in.size() > 1) {
      String one = in.remove(0);
      String two = in.remove(0);
      in.add(0, add(one, two));
    }
    return magnitude(in.get(0));
  }

  private String add(String one, String two) {
    return reduce("[" + one + "," + two + "]");
  }

  private String reduce(String s) {
    String prev = "";
    while (!prev.equals(s)) {
      prev = s;
      s = explode(s);
      if (prev.equals(s)) s = split(s);
    }
    return s;
  }

  private String split(String s) {
    for (int i = 0; i < s.length(); i++) {
      int num = parseNumAt(i, s);
      if (num >= 10) {
        String pair = "[" + (num / 2) + "," + (num % 2 == 0 ? num / 2 : (num / 2) + 1) + "]";
        return s.substring(0, i) + pair + s.substring(i + 2);
      }
    }
    return s;
  }

  private String explode(String s) {
    int depth = 0;
    for (int i = 0; i < s.length() - 5; i++) {
      if (s.charAt(i) == '[') depth++;
      else if (s.charAt(i) == ']') depth--;
      if (depth > 4) {
        String pair = s.substring(i);
        String repl = pair.replaceFirst("\\[[0-9]*,[0-9]*\\]", "");
        if (repl.length() < pair.length()) {
          pair = pair.substring(0, pair.indexOf(']') + 1);
          int leftNum = parseNumAt(1, pair);
          int rightNum = parseNumAt(pair.length() - 2, pair);
          int leftIndex = -1;
          int rightIndex = -1;
          for (int j = i - 1; j >= 0; j--) {
            if (isNum(s.charAt(j))) {
              leftIndex = j;
              break;
            }
          }
          for (int k = i + pair.length(); k < s.length(); k++) {
            if (isNum(s.charAt(k))) {
              rightIndex = k;
              break;
            }
          }
          String leftPart = leftIndex == -1 ? s.substring(0, i) : s.substring(0, leftIndex - (Integer.toString(parseNumAt(leftIndex, s)).length() - 1)) + (leftNum + parseNumAt(leftIndex, s)) + s.substring(leftIndex + 1, i);
          String rightPart = rightIndex == -1 ? s.substring(i + pair.length()) : s.substring(i + pair.length(), rightIndex) + (rightNum + parseNumAt(rightIndex, s)) + s.substring(rightIndex + Integer.toString(parseNumAt(rightIndex, s)).length());
          return leftPart + 0 + rightPart;
        }
      }
    }
    return s;
  }

  private int magnitude(String s) {
    String prev = "";
    while (!prev.equals(s)) {
      prev = s;
      s = calcMag(s);
    }
    return Integer.parseInt(s);
  }

  private String calcMag(String s) {
    for (int i = 0; i < s.length() - 5; i++) {
      String pair = s.substring(i);
      if (pair.endsWith(pair.replaceFirst("\\[[0-9]*,[0-9]*\\]", ""))) {
        int leftNum = 0;
        int rightNum = 0;
        int leftIndex = -1;
        int rightIndex = -1;
        for (int j = i + 1; j < s.length(); j++) {
          char c = s.charAt(j);
          if (c < '0' || c > '9') {
            leftNum = parseInt(s.substring(i + 1, j));
            leftIndex = j;
            break;
          }
        }
        for (int k = leftIndex + 1; k < s.length(); k++) {
          char c = s.charAt(k);
          if (c < '0' || c > '9') {
            rightNum = parseInt(s.substring(leftIndex + 1, k));
            rightIndex = k;
            break;
          }
        }
        return s.substring(0, i) + ((leftNum * 3) + (rightNum * 2)) + s.substring(rightIndex + 1);
      }
    }
    return s;
  }

  int parseNumAt(int i, String s) {
    if (!isNum(s.charAt(i))) return -1;
    int j = i;
    for (; isNum(s.charAt(j)); j++) ;
    for (; isNum(s.charAt(i)); i--) ;
    return parseInt(s.substring(i + 1, j));
  }

  boolean isNum(char c) {
    return c >= '0' && c <= '9';
  }

  @Override
  public Object part2() {
    var in = dayStream().filter(e -> !e.isEmpty()).map(this::reduce).collect(Collectors.toCollection(ArrayList::new));
    return IntStream.range(0, in.size()).boxed().flatMap(x -> IntStream.range(0, in.size()).filter(y -> x != y).mapToObj(y -> new Point(x, y))).mapToInt(p -> magnitude(reduce("[" + in.get(p.x) + "," + in.get(p.y) + "]"))).max().getAsInt();
  }
}
