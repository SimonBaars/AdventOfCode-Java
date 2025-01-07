package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;

public class Day21 extends Day2018 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  private List<Long> findAllHaltingValues() {
    List<Long> values = new ArrayList<>();
    Set<Long> seen = new HashSet<>();
    long r5 = 0;
    
    while (true) {
      long r4 = r5 | 65536;
      r5 = 13159625;
      
      while (true) {
        r5 = (((r5 + (r4 & 255)) & 16777215) * 65899) & 16777215;
        
        if (256 > r4) {
          if (seen.contains(r5)) {
            return values;
          }
          seen.add(r5);
          values.add(r5);
          break;
        }
        r4 = r4 >> 8;
      }
    }
  }

  @Override
  public Object part1() {
    return findAllHaltingValues().get(0);
  }

  @Override
  public Object part2() {
    List<Long> values = findAllHaltingValues();
    return values.get(values.size() - 1);
  }
}
