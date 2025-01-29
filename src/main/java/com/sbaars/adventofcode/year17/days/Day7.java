package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Day2017 {
  private static class Program {
    String name;
    int weight;
    List<Program> children = new ArrayList<>();
    Program parent;
    
    Program(String name, int weight) {
      this.name = name;
      this.weight = weight;
    }
    
    int getTotalWeight() {
      return weight + children.stream().mapToInt(Program::getTotalWeight).sum();
    }
  }
  
  private Map<String, Program> buildTree(String[] lines) {
    Map<String, Program> programs = new HashMap<>();
    Pattern pattern = Pattern.compile("(\\w+)\\s+\\((\\d+)\\)(?:\\s+->\\s+(.*))?");
    
    // First pass: create all programs
    for (String line : lines) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        String name = m.group(1);
        int weight = Integer.parseInt(m.group(2));
        programs.put(name, new Program(name, weight));
      }
    }
    
    // Second pass: build relationships
    for (String line : lines) {
      Matcher m = pattern.matcher(line);
      if (m.find()) {
        Program program = programs.get(m.group(1));
        String children = m.group(3);
        if (children != null) {
          for (String childName : children.split(",\\s+")) {
            Program child = programs.get(childName);
            program.children.add(child);
            child.parent = program;
          }
        }
      }
    }
    
    return programs;
  }
  
  private int findCorrectWeight(Program root) {
    // For each node, check if its children are balanced
    for (Program program : root.children) {
      int result = findCorrectWeight(program);
      if (result != -1) return result;
    }
    
    // Get weights of all siblings
    if (root.parent != null) {
      Map<Integer, List<Program>> weightGroups = new HashMap<>();
      for (Program sibling : root.parent.children) {
        int weight = sibling.getTotalWeight();
        weightGroups.computeIfAbsent(weight, k -> new ArrayList<>()).add(sibling);
      }
      
      // If we have exactly two different weights and one is unique
      if (weightGroups.size() == 2) {
        int correctWeight = -1;
        int wrongWeight = -1;
        Program wrongProgram = null;
        
        for (Map.Entry<Integer, List<Program>> entry : weightGroups.entrySet()) {
          if (entry.getValue().size() == 1) {
            wrongWeight = entry.getKey();
            wrongProgram = entry.getValue().get(0);
          } else {
            correctWeight = entry.getKey();
          }
        }
        
        if (wrongProgram != null) {
          int weightDiff = wrongWeight - correctWeight;
          return wrongProgram.weight - weightDiff;
        }
      }
    }
    
    return -1;
  }

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
    String[] lines = dayStrings();
    Map<String, Program> programs = buildTree(lines);
    
    // Find root program
    Program root = programs.values().stream()
        .filter(p -> p.parent == null)
        .findFirst()
        .orElseThrow();
    
    return findCorrectWeight(root);
  }
}
