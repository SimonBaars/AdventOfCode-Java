package com.sbaars.adventofcode.year18;

import com.sbaars.adventofcode.common.Day;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException {
    for (int day = 1; day <= 25; day++) {
      System.out.println("Day " + day + ":");
      Day instance = (Day) Class.forName("com.sbaars.adventofcode.year18.days.Day" + day).getDeclaredConstructor().newInstance();
      
      long startTime = System.nanoTime();
      Object part1 = instance.part1();
      long part1Time = (System.nanoTime() - startTime) / 1_000_000; // Convert to milliseconds
      
      startTime = System.nanoTime();
      Object part2 = instance.part2();
      long part2Time = (System.nanoTime() - startTime) / 1_000_000; // Convert to milliseconds
      
      System.out.println("Part 1: " + part1 + " (" + part1Time + "ms)");
      System.out.println("Part 2: " + part2 + " (" + part2Time + "ms)");
      System.out.println();
    }
  }
}
