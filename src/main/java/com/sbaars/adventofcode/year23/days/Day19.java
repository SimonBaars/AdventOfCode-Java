package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.year23.days.Day19.Constraint.newLessThan;
import static com.sbaars.adventofcode.year23.days.Day19.Constraint.newMoreThan;
import static java.util.Arrays.stream;
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

  public record Constraint(long moreThan, long lessThan) {
    public static Constraint newMoreThan(long moreThan) {
      return new Constraint(moreThan, 4001);
    }

    public static Constraint newLessThan(long lessThan) {
      return new Constraint(-1, lessThan);
    }

    public Constraint moreThan(long moreThan) {
      return new Constraint(Math.max(this.moreThan, moreThan), lessThan);
    }

    public Constraint lessThan(long lessThan) {
      return new Constraint(moreThan, Math.min(this.lessThan, lessThan));
    }

    public long numsAccepted() {
      if (moreThan > lessThan) return 0;
      return lessThan - moreThan - 1;
    }
  }

  @Override
  public Object part1() {
    var workflows = getWorkflows();
    List<Part> parts = stream(day().split("\n\n")[1].split("\n")).map(s -> readString(s, "{%l(%c=%n)}", ",", Part.class, Number.class)).toList();
    return parts.stream().filter(p -> isAccepted(workflows, p, "in")).mapToLong(p -> p.numbers.values().stream().mapToLong(l -> l).sum()).sum();
  }

  private Map<String, List<WorkflowItem>> getWorkflows() {
    return stream(day().split("\n\n")[0].split("\n")).map(s -> readString(s, "%s{%ls}", ",", Workflow.class)).collect(toMap(e -> e.name, e -> e.steps));
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
    return countAccepted(getWorkflows(), new HashMap<>(), "in");
  }

  private long countAccepted(Map<String, List<WorkflowItem>> workflows, Map<Character, Constraint> constraints, String workflow) {
    var items = workflows.get(workflow);
    long sum = 0;
    for (var item : items) {
      if (item.c == ' ') {
        sum += checkItem2(workflows, constraints, item);
      } else {
        if (item.op == '<') {
          var newConstraints = new HashMap<>(constraints);
          if (constraints.containsKey(item.c)) {
            var constraint = constraints.get(item.c);
            newConstraints.put(item.c, constraint.lessThan(item.n));
            constraints.put(item.c, constraint.moreThan(item.n - 1));
          } else {
            newConstraints.put(item.c, newLessThan(item.n));
            constraints.put(item.c, newMoreThan(item.n - 1));
          }
          sum += checkItem2(workflows, newConstraints, item);
        } else if (item.op == '>') {
          var newConstraints = new HashMap<>(constraints);
          if (constraints.containsKey(item.c)) {
            var constraint = constraints.get(item.c);
            newConstraints.put(item.c, constraint.moreThan(item.n));
            constraints.put(item.c, constraint.lessThan(item.n + 1));
          } else {
            newConstraints.put(item.c, newMoreThan(item.n));
            constraints.put(item.c, newLessThan(item.n + 1));
          }
          sum += checkItem2(workflows, newConstraints, item);
        } else throw new IllegalStateException("Unknown operator: " + item.op);
      }
    }
    return sum;
  }

  private long checkItem2(Map<String, List<WorkflowItem>> workflows, Map<Character, Constraint> constraints, WorkflowItem item) {
    if (item.s.equals("A")) {
      return constraints.values().stream().mapToLong(Constraint::numsAccepted).reduce(1, (a, b) -> a * b);
    } else if (item.s.equals("R")) return 0;
    else return countAccepted(workflows, constraints, item.s);
  }
}
