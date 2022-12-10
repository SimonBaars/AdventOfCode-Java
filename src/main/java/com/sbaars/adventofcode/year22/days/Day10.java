package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends Day2022 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    Day d = new Day10();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
    long cycle = 1;
    long x  = 1;
    List<Long> output = new ArrayList<>();
    for(String op : dayStrings()) {
      if(op.equals("noop")) {
        cycle++;
      } else if(op.startsWith("addx")) {
        long add = Long.parseLong(op.substring(5));
        cycle++;
        if((cycle+20) % 40 == 0) {
          output.add(cycle*x);
//          System.out.println(cycle+", "+x);
        }
        cycle++;
        x+=add;
      }
      if((cycle+20) % 40 == 0) {
        output.add(cycle*x);
//        System.out.p  rintln(cycle+", "+x);
      }
      if(cycle>220) break;
    }
//    System.out.println(cycle+", "+x+", "+ Arrays.toString(output.toArray()));
    return output.stream().mapToLong(e -> e).sum();
  }

  @Override
  public Object part2() {
    long cycle = 1;
    long x  = 1;
//    List<Long> output = new ArrayList<>();
    String pixels = "";
    for(String op : dayStrings()) {
      if((cycle-1) % 40 == 0) {
        pixels+="\n";
      }
      pixels += List.of(x-1, x, x+1).contains((cycle-1)%40) ? "#" : ".";
      if(op.equals("noop")) {
        cycle++;
      } else if(op.startsWith("addx")) {
        long add = Long.parseLong(op.substring(5));
        cycle++;
        if((cycle-1) % 40 == 0) {
          pixels+="\n";
        }
        pixels += List.of(x-1, x, x+1).contains((cycle-1)%40) ? "#" : ".";
        cycle++;
        x+=add;
      }
    }
//    System.out.println(cycle+", "+x+", "+ Arrays.toString(output.toArray()));
    return pixels;
  }
}
