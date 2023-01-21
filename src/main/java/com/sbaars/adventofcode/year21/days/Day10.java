package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year21.Day2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day10 extends Day2021 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) throws IOException {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    return getScore(false).get(0);
  }

  @Override
  public Object part2() {
    List<Long> scores = getScore(true);
    return scores.stream().sorted().skip(scores.size()/2).findFirst().get();
  }

  private List<Long> getScore(boolean part) {
    Map<Character, Character> m = Map.of(')', '(', ']', '[', '>', '<', '}', '{');
    Map<Character, Integer> p = part ? Map.of('(', 1, '[', 2, '{', 3, '<', 4) : Map.of(')', 3, ']', 57, '>', 25137, '}', 1197);
    var in = dayStrings();
    List<Long> scores = new ArrayList<>();
    long score1 = 0;
    out: for(String line : in){
      Stack<Character> s = new Stack<>();
      for(Character c : line.toCharArray()){
        if(m.containsKey(c)){
          if(!s.isEmpty()){
            Character stackC = s.pop();
            if(!m.get(c).equals(stackC)){
              if(!part) score1+=p.get(c);
              continue out;
            }
          }
        } else {
          s.push(c);
        }
      }
      if(!part) continue;
      long score = 0;
      while(!s.isEmpty()){
        Character c = s.pop();
        score = (score * 5) + p.get(c);
      }
      scores.add(score);
    }
    if(!part) return List.of(score1);
    return scores;
  }
}
