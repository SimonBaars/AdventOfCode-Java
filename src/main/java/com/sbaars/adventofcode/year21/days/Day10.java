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
    System.in.read();
//    new Day10().submitPart1();
    new Day10().submitPart2();
  }

  @Override
  public Object part1() {
    Map<Character, Character> m = Map.of(')', '(', ']', '[', '>', '<', '}', '{');
    Map<Character, Integer> p = Map.of(')', 3, ']', 57, '>', 25137, '}', 1197);
    var in = dayStrings();
    int score = 0;
    out: for(String line : in){
      Stack<Character> s = new Stack<>();
      for(Character c : line.toCharArray()){
        if(m.containsKey(c)){
          if(!s.isEmpty()){
            Character stackC = s.pop();
            if(!m.get(c).equals(stackC)){
              score+=p.get(c);
              continue out;
            }
          }
        } else {
          s.push(c);
        }
      }
    }
    return score;
  }

  @Override
  public Object part2() {
    Map<Character, Character> m = Map.of(')', '(', ']', '[', '>', '<', '}', '{');
    Map<Character, Character> m2 = Map.of('(', ')', '[', ']', '<', '>', '{', '}');
    Map<Character, Integer> p = Map.of(')', 3, ']', 57, '>', 25137, '}', 1197);
    Map<Character, Integer> p2 = Map.of('(', 1, '[', 2, '{', 3, '<', 4);
    var in = dayStrings();
    List<Long> scores = new ArrayList<>();
    out: for(String line : in){
      Stack<Character> s = new Stack<>();
      for(Character c : line.toCharArray()){
        if(m.containsKey(c)){
          if(!s.isEmpty()){
            Character stackC = s.pop();
            if(!m.get(c).equals(stackC)){
              //score+=p.get(c);
              continue out;
            }
          }
        } else {
          s.push(c);
        }
      }
      long score = 0;
      while(!s.isEmpty()){
        Character c = s.pop();
        score = (score * 5) + p2.get(c);
//        score += switch ((char)m2.get(c)){
//          case '(' -> 1;
//          case '[' -> 2;
//          case '{' -> 3;
//          case '<' -> 4;
//          default -> throw new IllegalStateException("Not in switch "+c);
//        };
      }
      scores.add(score);
    }
    return scores.stream().sorted().skip(scores.size()/2).findFirst().get();
  }
}
