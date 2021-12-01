package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year21.Day2021;

public class Day1 extends Day2021 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  @Override
  public Object part1() {
    long[] input = dayNumbers();
    int res = 0;
    for(int i = 1; i<input.length; i++){
      if(input[i-1] < input[i]){
        res++;
      }
    }
    return res;
  }

  @Override
  public Object part2() {
    long[] input = dayNumbers();
    int res = 0;
    for(int i = 3; i<input.length; i++){
      if(input[i-3] + input[i-2] + input[i-1] < input[i] + input[i-2] + input[i-1]){
        res++;
      }
    }
    return res;
  }
}
