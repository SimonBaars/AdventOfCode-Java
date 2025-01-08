package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.List;

public class Day7 extends Day2016 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  private boolean hasAbba(String s) {
    for (int i = 0; i < s.length() - 3; i++) {
      if (s.charAt(i) == s.charAt(i + 3) && 
          s.charAt(i + 1) == s.charAt(i + 2) && 
          s.charAt(i) != s.charAt(i + 1)) {
        return true;
      }
    }
    return false;
  }

  private boolean supportsTls(String ip) {
    List<String> outsideBrackets = new ArrayList<>();
    List<String> insideBrackets = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    boolean inBrackets = false;

    for (char c : ip.toCharArray()) {
      if (c == '[') {
        outsideBrackets.add(current.toString());
        current = new StringBuilder();
        inBrackets = true;
      } else if (c == ']') {
        insideBrackets.add(current.toString());
        current = new StringBuilder();
        inBrackets = false;
      } else {
        current.append(c);
      }
    }
    if (!current.isEmpty()) {
      outsideBrackets.add(current.toString());
    }

    return outsideBrackets.stream().anyMatch(this::hasAbba) && 
           insideBrackets.stream().noneMatch(this::hasAbba);
  }

  @Override
  public Object part1() {
    return dayStream()
        .filter(this::supportsTls)
        .count();
  }

  @Override
  public Object part2() {
    return "";
  }
}
