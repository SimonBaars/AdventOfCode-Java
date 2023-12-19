package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.Collectors.toMap;

public class Day19 extends Day2023 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  public record Workflow(List<WorkflowItem> steps, String name) {
    public Workflow(String n, List<String> strings) {
      this(strings.stream().map(s -> {
        try {
          return readString(s, "%c%c%n:%s", WorkflowItem.class);
        } catch (Exception e) {
          return new WorkflowItem(s);
        }
      }).toList(), n);
    }
  }

  public record WorkflowItem(char c, char op, long n, String s) {
    public WorkflowItem(String s) {
      this(' ', ' ', 0, s);
    }
  }

  public record Part(Map<Character, Long> numbers) {
    public Part(List<Number> numbers) {
      this(numbers.stream().collect(toMap(n -> n.c, n -> n.n)));
    }
  }

  public record Number(char c, long n) {
  }

  @Override
  public Object part1() {
    String[] in = day().split("\n\n");
    Map<String, List<WorkflowItem>> workflows = Arrays.stream(in[0].split("\n")).map(s -> readString(s, "%s{%ls}", ",", Workflow.class)).collect(toMap(e -> e.name, e -> e.steps));
    List<Part> parts = Arrays.stream(in[1].split("\n")).map(s -> readString(s, "{%l(%c=%n)}", ",", Part.class, Number.class)).toList();
    return parts.stream().filter(p -> isAccepted(workflows, p, "in")).mapToLong(p -> p.numbers.values().stream().mapToLong(l -> l).sum()).sum();
  }

  private boolean isAccepted(Map<String, List<WorkflowItem>> workflows, Part p, String workflow) {
    var items = workflows.get(workflow);
    for (var item : items) {
      if (item.c == ' ') {
        return checkItem(workflows, p, item);
      } else {
        if (item.op == '<') {
          if (p.numbers.get(item.c) < item.n) return checkItem(workflows, p, item);
        } else if (item.op == '>') {
          if (p.numbers.get(item.c) > item.n) return checkItem(workflows, p, item);
        } else throw new IllegalStateException("Unknown operator: " + item.op);
      }
    }
    throw new IllegalStateException("Reached end = impossible");
  }

  private boolean checkItem(Map<String, List<WorkflowItem>> workflows, Part p, WorkflowItem item) {
    if (item.s.equals("A")) return true;
    else if (item.s.equals("R")) return false;
    else return isAccepted(workflows, p, item.s);
  }

  @Override
  public Object part2() {
    return "";
  }
}
