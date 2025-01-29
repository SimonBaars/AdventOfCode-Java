package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day9 extends Day2017 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  @Override
  public Object part1() {
    String input = day().trim();
    int depth = 0;
    int score = 0;
    boolean inGarbage = false;
    boolean skipNext = false;
    
    for (char c : input.toCharArray()) {
      if (skipNext) {
        skipNext = false;
        continue;
      }
      
      if (c == '!') {
        skipNext = true;
        continue;
      }
      
      if (inGarbage) {
        if (c == '>') {
          inGarbage = false;
        }
        continue;
      }
      
      if (c == '{') {
        depth++;
        score += depth;
      } else if (c == '}') {
        depth--;
      } else if (c == '<') {
        inGarbage = true;
      }
    }
    
    return score;
  }

  @Override
  public Object part2() {
    String input = day().trim();
    int garbageCount = 0;
    boolean inGarbage = false;
    boolean skipNext = false;
    
    for (char c : input.toCharArray()) {
      if (skipNext) {
        skipNext = false;
        continue;
      }
      
      if (c == '!') {
        skipNext = true;
        continue;
      }
      
      if (inGarbage) {
        if (c == '>') {
          inGarbage = false;
        } else {
          garbageCount++;
        }
        continue;
      }
      
      if (c == '<') {
        inGarbage = true;
      }
    }
    
    return garbageCount;
  }
}
