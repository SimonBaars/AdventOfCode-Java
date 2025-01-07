package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;
import java.util.*;

public class Day19 extends Day2023 {
  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  record Rule(char category, char operator, long value, String target) {
    static Rule parse(String s) {
      if (!s.contains(":")) return new Rule(' ', ' ', 0, s);
      var condition = s.split(":");
      return new Rule(
          condition[0].charAt(0),
          condition[0].charAt(1),
          Long.parseLong(condition[0].substring(2)),
          condition[1]
      );
    }
  }

  record Workflow(List<Rule> rules) {
    static Workflow parse(String s) {
      var parts = s.substring(s.indexOf('{')+1, s.length()-1).split(",");
      return new Workflow(Arrays.stream(parts).map(Rule::parse).toList());
    }
  }

  record Part(Map<Character, Long> ratings) {
    static Part parse(String s) {
      var ratings = new HashMap<Character, Long>();
      var parts = s.substring(1, s.length()-1).split(",");
      for (var part : parts) {
        ratings.put(part.charAt(0), Long.parseLong(part.substring(2)));
      }
      return new Part(ratings);
    }
  }

  record Range(long min, long max) {
    long size() { return max - min + 1; }
    Range intersect(long newMin, long newMax) {
      return new Range(Math.max(min, newMin), Math.min(max, newMax));
    }
  }

  @Override
  public Object part1() {
    var parts = day().split("\n\n");
    var workflows = parseWorkflows(parts[0]);
    return Arrays.stream(parts[1].split("\n"))
        .map(Part::parse)
        .filter(p -> process(p, workflows, "in"))
        .mapToLong(p -> p.ratings.values().stream().mapToLong(Long::longValue).sum())
        .sum();
  }

  private Map<String, Workflow> parseWorkflows(String input) {
    var workflows = new HashMap<String, Workflow>();
    for (var line : input.split("\n")) {
      var parts = line.split("\\{", 2);
      workflows.put(parts[0], Workflow.parse("{" + parts[1]));
    }
    return workflows;
  }

  private boolean process(Part part, Map<String, Workflow> workflows, String current) {
    if (current.equals("A")) return true;
    if (current.equals("R")) return false;
    
    var workflow = workflows.get(current);
    for (var rule : workflow.rules) {
      if (rule.category == ' ' || evaluate(part, rule)) {
        return process(part, workflows, rule.target);
      }
    }
    throw new IllegalStateException("No matching rule found");
  }

  private boolean evaluate(Part part, Rule rule) {
    if (rule.category == ' ') return true;
    var value = part.ratings.get(rule.category);
    return rule.operator == '<' ? value < rule.value : value > rule.value;
  }

  @Override
  public Object part2() {
    var workflows = parseWorkflows(day().split("\n\n")[0]);
    var initial = new HashMap<Character, Range>();
    "xmas".chars().forEach(c -> initial.put((char)c, new Range(1, 4000)));
    return countCombinations(workflows, initial, "in");
  }

  private long countCombinations(Map<String, Workflow> workflows, Map<Character, Range> ranges, String current) {
    if (current.equals("R")) return 0;
    if (current.equals("A")) return ranges.values().stream().mapToLong(Range::size).reduce(1L, (a, b) -> a * b);
    
    var workflow = workflows.get(current);
    var result = 0L;
    var currentRanges = new HashMap<>(ranges);
    
    for (var rule : workflow.rules) {
      if (rule.category == ' ') {
        result += countCombinations(workflows, currentRanges, rule.target);
        continue;
      }
      
      var range = currentRanges.get(rule.category);
      Map<Character, Range> newRanges = new HashMap<>(currentRanges);
      
      if (rule.operator == '<') {
        newRanges.put(rule.category, range.intersect(1, rule.value - 1));
        currentRanges.put(rule.category, range.intersect(rule.value, 4000));
      } else {
        newRanges.put(rule.category, range.intersect(rule.value + 1, 4000));
        currentRanges.put(rule.category, range.intersect(1, rule.value));
      }
      
      result += countCombinations(workflows, newRanges, rule.target);
    }
    return result;
  }
}
