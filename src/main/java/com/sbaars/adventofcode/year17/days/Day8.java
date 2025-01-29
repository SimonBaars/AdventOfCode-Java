package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 extends Day2017 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }
  
  private record ProcessResult(int maxFinal, int maxEver) {}
  
  private ProcessResult processInstructions() {
    Map<String, Integer> registers = new HashMap<>();
    Pattern pattern = Pattern.compile("(\\w+) (inc|dec) (-?\\d+) if (\\w+) ([<>=!]+) (-?\\d+)");
    int maxEver = 0;
    
    for (String line : dayStrings()) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        String register = m.group(1);
        String operation = m.group(2);
        int value = Integer.parseInt(m.group(3));
        String condRegister = m.group(4);
        String operator = m.group(5);
        int condValue = Integer.parseInt(m.group(6));
        
        int regValue = registers.getOrDefault(register, 0);
        int condRegValue = registers.getOrDefault(condRegister, 0);
        
        boolean condition = switch (operator) {
          case ">" -> condRegValue > condValue;
          case "<" -> condRegValue < condValue;
          case ">=" -> condRegValue >= condValue;
          case "<=" -> condRegValue <= condValue;
          case "==" -> condRegValue == condValue;
          case "!=" -> condRegValue != condValue;
          default -> throw new IllegalStateException("Unknown operator: " + operator);
        };
        
        if (condition) {
          int newValue = operation.equals("inc") ? regValue + value : regValue - value;
          registers.put(register, newValue);
          maxEver = Math.max(maxEver, newValue);
        }
      }
    }
    
    int maxFinal = registers.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    return new ProcessResult(maxFinal, maxEver);
  }

  @Override
  public Object part1() {
    return processInstructions().maxFinal();
  }

  @Override
  public Object part2() {
    return processInstructions().maxEver();
  }
}
