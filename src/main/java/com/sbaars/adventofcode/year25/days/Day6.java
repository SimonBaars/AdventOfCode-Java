package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;
import java.util.*;

public class Day6 extends Day2025 {
  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    String[] lines = dayStream().toArray(String[]::new);
    
    // Find columns (separated by all-space columns)
    List<Integer> problemStarts = new ArrayList<>();
    for (int col = 0; col < lines[0].length(); col++) {
      boolean isAllSpaces = true;
      for (String line : lines) {
        if (col < line.length() && line.charAt(col) != ' ') {
          isAllSpaces = false;
          break;
        }
      }
      if (!isAllSpaces) {
        // Check if prev column was spaces
        if (problemStarts.isEmpty() || col > 0) {
          boolean prevWasSpaces = col == 0;
          if (col > 0) {
            prevWasSpaces = true;
            for (String line : lines) {
              if (col - 1 < line.length() && line.charAt(col - 1) != ' ') {
                prevWasSpaces = false;
                break;
              }
            }
          }
          if (prevWasSpaces) {
            problemStarts.add(col);
          }
        }
      }
    }
    
    long total = 0;
    
    // Process each problem
    for (int i = 0; i < problemStarts.size(); i++) {
      int startCol = problemStarts.get(i);
      int endCol = (i + 1 < problemStarts.size()) ? problemStarts.get(i + 1) : lines[0].length();
      
      // Extract numbers and operator
      List<Long> numbers = new ArrayList<>();
      char operator = '+';
      
      for (String line : lines) {
        String segment = line.substring(startCol, Math.min(endCol, line.length())).trim();
        if (!segment.isEmpty()) {
          if (segment.equals("+") || segment.equals("*")) {
            operator = segment.charAt(0);
          } else {
            try {
              numbers.add(Long.parseLong(segment));
            } catch (NumberFormatException e) {
              // Skip non-numeric
            }
          }
        }
      }
      
      // Calculate result
      if (!numbers.isEmpty()) {
        long result = numbers.get(0);
        for (int j = 1; j < numbers.size(); j++) {
          if (operator == '+') {
            result += numbers.get(j);
          } else {
            result *= numbers.get(j);
          }
        }
        total += result;
      }
    }
    
    return total;
  }

  @Override
  public Object part2() {
    String[] lines = dayStream().toArray(String[]::new);
    
    // For each column, build numbers reading top to bottom
    // Process columns right-to-left
    long total = 0;
    
    for (int startCol = lines[0].length() - 1; startCol >= 0; startCol--) {
      // Check if this column starts a problem
      boolean isAllSpaces = true;
      for (String line : lines) {
        if (startCol < line.length() && line.charAt(startCol) != ' ') {
          isAllSpaces = false;
          break;
        }
      }
      
      if (isAllSpaces) continue;
      
      // Find the extent of this problem (until next all-space column or start)
      int endCol = startCol;
      while (endCol > 0) {
        boolean allSpace = true;
        for (String line : lines) {
          if (endCol - 1 < line.length() && line.charAt(endCol - 1) != ' ') {
            allSpace = false;
            break;
          }
        }
        if (allSpace) break;
        endCol--;
      }
      
      // Now process columns from endCol to startCol (right-to-left within problem)
      List<Long> numbers = new ArrayList<>();
      char operator = '+';
      
      for (int col = startCol; col >= endCol; col--) {
        // Build number from this column (top to bottom)
        StringBuilder numBuilder = new StringBuilder();
        for (String line : lines) {
          if (col < line.length()) {
            char ch = line.charAt(col);
            if (ch >= '0' && ch <= '9') {
              numBuilder.append(ch);
            } else if (ch == '+' || ch == '*') {
              operator = ch;
            }
          }
        }
        if (numBuilder.length() > 0) {
          numbers.add(Long.parseLong(numBuilder.toString()));
        }
      }
      
      // Calculate result
      if (!numbers.isEmpty()) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
          if (operator == '+') {
            result += numbers.get(i);
          } else {
            result *= numbers.get(i);
          }
        }
        total += result;
      }
      
      startCol = endCol; // Skip to next problem
    }
    
    return total;
  }
}
