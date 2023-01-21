package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.sbaars.adventofcode.util.AOCUtils.fixedPoint;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Day7 extends Day2015 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  public record Op(String op, String variable) {
    private String[] expression() {
      return op.split(" ");
    }
  }

  @Override
  public Object part1() {
    return solution(empty());
  }

  @Override
  public Object part2() {
    int sol = solution(empty());
    return solution(of(sol));
  }

  private int solution(Optional<Integer> start) {
    var ops = dayStream()
        .map(s -> s.endsWith(" -> b") && start.isPresent() ? start.get() + " -> b" : s)
        .map(s -> readString(s, "%s -> %s", Op.class))
        .toList();
    return fixedPoint(
        (Map<String, Integer>) new HashMap<String, Integer>(),
        prev -> ops.stream()
            .filter(op -> !prev.containsKey(op.variable))
            .reduce(prev, this::computeOutput, (a, b) -> {
              a.putAll(b);
              return a;
            })
    ).get("a");
  }

  private Map<String, Integer> computeOutput(Map<String, Integer> sol, Op op) {
    var newMap = new HashMap<>(sol);
    if (op.expression().length == 3) {
      processBinaryOperator(newMap, op);
    } else {
      processNotOrAssign(newMap, op);
    }
    return newMap;
  }

  private void processNotOrAssign(Map<String, Integer> solved, Op op) {
    var expr = op.expression();
    var variable = expr.length == 2 ? getVariable(solved, expr[1]).map(n -> ~n & 0xFFFF) : getVariable(solved, expr[0]);
    variable.ifPresent(integer -> solved.put(op.variable, integer));
  }

  private void processBinaryOperator(Map<String, Integer> solved, Op op) {
    var expr = op.expression();
    var variable1 = getVariable(solved, expr[0]);
    var variable2 = getVariable(solved, expr[2]);
    var operator = expr[1];
    if (variable1.isPresent() && variable2.isPresent()) {
      int v1 = variable1.get();
      int v2 = variable2.get();
      solved.put(op.variable, compute(v1, operator, v2));
    }
  }

  private static Integer compute(int v1, String operator, int v2) {
    return switch (operator) {
      case "AND" -> v1 & v2;
      case "OR" -> v1 | v2;
      case "LSHIFT" -> v1 << v2;
      case "RSHIFT" -> v1 >> v2;
      default -> throw new IllegalStateException("Unknown operator " + operator);
    };
  }

  public Optional<Integer> getVariable(Map<String, Integer> sol, String name) {
    try {
      return of(parseInt(name));
    } catch (NumberFormatException e) {
      if (!sol.containsKey(name)) {
        return empty();
      }
      return of(sol.get(name));
    }
  }
}
