package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.toIntExact;

public class Day5 extends Day2022 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) throws IOException {
    new Day5().printParts();
  }

  public record Move(long which, long from, long to) {}

  @Override
  public Object part1() {
    List<Deque<Integer>> stacks = input();
    List<Move> moves = getMoves();
    for(Move m : moves) {
      for(int i = 0; i<m.which; i++) {
        int top = stacks.get(toIntExact(m.from-1)).pop();
        stacks.get(toIntExact(m.to-1)).push(top);
      }
    }
    return stacks.stream().map(Deque::peek).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
  }

  @Override
  public Object part2() {
    List<Deque<Integer>> stacks = input();
    List<Move> moves = getMoves();
    for(Move m : moves) {
      List<Integer> toBeMoved = new ArrayList<>();
      for(int i = 0; i<m.which; i++) toBeMoved.add(0, stacks.get(toIntExact(m.from-1)).pop());
      toBeMoved.forEach(i -> stacks.get(toIntExact(m.to-1)).push(i));
    }
    return stacks.stream().map(Deque::peek).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
  }

  private List<Move> getMoves() {
    return dayStream().map(String::trim).map(s -> readString(s, "move %n from %n to %n", Move.class)).toList();
  }

  private static List<Deque<Integer>> input() {
    List<Deque<Integer>> stacks = new ArrayList<>();
    for(int i = 1; i<=9; i++){
      Deque<Integer> s = new ArrayDeque<>();
      switch (i) {
        case 1 -> "NSDCVQT".chars().forEach(s::push);
        case 2 -> "MFV".chars().forEach(s::push);
        case 3 -> "FQWDPNHM".chars().forEach(s::push);
        case 4 -> "DQRTF".chars().forEach(s::push);
        case 5 -> "RFMNQHVB".chars().forEach(s::push);
        case 6 -> "CFGNPWQ".chars().forEach(s::push);
        case 7 -> "WFRLCT".chars().forEach(s::push);
        case 8 -> "TZNS".chars().forEach(s::push);
        case 9 -> "MSDJRQHN".chars().forEach(s::push);
      }
      stacks.add(s);
    }
    return stacks;
  }
}
