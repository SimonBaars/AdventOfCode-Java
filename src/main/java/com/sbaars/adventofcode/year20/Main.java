package com.sbaars.adventofcode.year20;

import com.sbaars.adventofcode.common.Day;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException {
    for (int day = 1; day <= 25; day++) {
      System.out.println("Day " + day + ":");
      Day instance = (Day) Class.forName("com.sbaars.adventofcode.year20.days.Day" + day).getDeclaredConstructor().newInstance();
      instance.printParts();
      System.out.println();
    }
  }
}
