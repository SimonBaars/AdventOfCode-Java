package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.lang.Math.toIntExact;

public class Day5 extends Day2022 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day5();
    d.downloadIfNotDownloaded();
//    d.downloadExample();
    d.printParts();
    System.in.read();
//    d.submitPart1();
    d.submitPart2();
  }

  public record Move(long which, long from, long to) {}

  @Override
  public Object part1() {
    List<Deque<Integer>> stacks = input();
    List<Move> moves = dayStream().map(String::trim).map(s -> readString(s, "move %n from %n to %n", Move.class)).toList();
    for(Move m : moves) {
      for(int i = 0; i<m.which; i++) {
        int top = stacks.get(toIntExact(m.from-1)).removeLast();
        stacks.get(toIntExact(m.to-1)).addLast(top);
      }
    }

    return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
  }

  private String toASCII(int value) {
    int length = 4;
    StringBuilder builder = new StringBuilder(length);
    for (int i = length - 1; i >= 0; i--) {
      builder.append((char) ((value >> (8 * i)) & 0xFF));
    }
    return builder.toString();
  }


  @Override
  public Object part2() {
    List<Deque<Integer>> stacks = input();
    List<Move> moves = dayStream().map(String::trim).map(s -> readString(s, "move %n from %n to %n", Move.class)).toList();
    for(Move m : moves) {
      List<Integer> toBeMoved = new ArrayList<>();
      for(int i = 0; i<m.which; i++) toBeMoved.add(0, stacks.get(toIntExact(m.from-1)).removeLast());
      toBeMoved.forEach(i -> stacks.get(toIntExact(m.to-1)).addLast(i));
    }

    return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
  }

  private static List<Deque<Integer>> input() {
    List<Deque<Integer>> stacks = new ArrayList<>();
    for(int i = 1; i<=9; i++){
      Deque<Integer> s = new ArrayDeque<>();
      switch(i){
        case 1: "NSDCVQT".chars().forEach(s::add); break;
        case 2: "MFV".chars().forEach(s::add); break;
        case 3: "FQWDPNHM".chars().forEach(s::add); break;
        case 4: "DQRTF".chars().forEach(s::add); break;
        case 5: "RFMNQHVB".chars().forEach(s::add); break;
        case 6: "CFGNPWQ".chars().forEach(s::add); break;
        case 7: "WFRLCT".chars().forEach(s::add); break;
        case 8: "TZNS".chars().forEach(s::add); break;
        case 9: "MSDJRQHN".chars().forEach(s::add); break;
      }
      stacks.add(s);
    }
    return stacks;
  }
}
