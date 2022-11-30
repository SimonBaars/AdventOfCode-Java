package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

public class Day1 extends Day2022 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    Day d = new Day1();
    d.downloadIfNotDownloaded();
    d.printParts();
    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
    long[] input = dayNumbers();
    int res = 0;
    for(int i = 0; i<input.length; i++){
      if(input[i-1] > input[i]){
        res++;
      }
    }
    return res;
  }

  @Override
  public Object part2() {
    return 0;
  }
}
