package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Day2017 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    String[] lines = dayStrings();
    Set<String> allPrograms = new HashSet<>();
    Set<String> childPrograms = new HashSet<>();
    Pattern pattern = Pattern.compile("(\\w+)\\s+\\(\\d+\\)(?:\\s+->\\s+(.*))?");
    
    for (String line : lines) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        String program = m.group(1);
        allPrograms.add(program);
        
        String children = m.group(2);
        if (children != null) {
          for (String child : children.split(",\\s+")) {
            childPrograms.add(child);
          }
        }
      }
    }
    
    // The root program is the only one that is not a child of any other program
    allPrograms.removeAll(childPrograms);
    return allPrograms.iterator().next();
  }

  @Override
  public Object part2() {
    return "";
  }
}
