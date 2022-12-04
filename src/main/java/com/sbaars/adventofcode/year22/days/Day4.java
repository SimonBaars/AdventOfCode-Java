package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day4 extends Day2022 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day4();
    d.downloadIfNotDownloaded();
//    d.downloadExample();
    d.printParts();
    System.in.read();
//    d.submitPart1();
    d.submitPart2();
  }

  public record Assignment(long aStart, long aEnd, long bStart, long bEnd){
    public boolean contained(){
      return (aStart>=bStart && aEnd<=bEnd) || (bStart>=aStart && bEnd<=aEnd);
    }

    public boolean overlap(){
      return aStart <= bEnd && aEnd >= bStart;
    }
  }

  @Override
  public Object part1() {
    return dayStream().map(String::trim).map(s -> readString(s, "%n-%n,%n-%n", Assignment.class)).filter(Assignment::contained).count();
  }

  @Override
  public Object part2() {
    return dayStream().map(String::trim).map(s -> readString(s, "%n-%n,%n-%n", Assignment.class)).filter(Assignment::overlap).count();
  }
}
