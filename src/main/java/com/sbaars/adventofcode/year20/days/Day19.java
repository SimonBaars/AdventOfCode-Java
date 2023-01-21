package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.map.SetMap;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.Set.copyOf;

public class Day19 extends Day2020 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  @Override
  public Object part1() {
    return getSolution(day());
  }

  private long getSolution(String inputFile) {
    String[] input = inputFile.split("\n\n");
    CnfGrammar cnf = new CnfGrammar(new Grammar(input[0]));
    return stream(input[1].split("\n")).filter(e -> new Cyk(cnf, e).inGrammar()).count();
  }

  @Override
  public Object part2() {
    return getSolution(day().replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", "11: 42 31 | 42 11 31"));
  }

  // Cyk parser adapted from solution by akaritakai: https://github.com/akaritakai/AdventOfCode2020/blob/main/src/main/java/net/akaritakai/aoc2020/Puzzle19.java
  private static class Cyk {
    private final Set<String>[][] cyk;
    private final int size;

    public Cyk(CnfGrammar grammar, String inputString) {
      var rules = grammar.reverseRules;
      var input = tokenize(inputString);
      size = input.size();
      //noinspection unchecked
      cyk = new Set[size][size];

      for (var x = 0; x < size; x++) {
        cyk(0, x).addAll(rules.get(input.subList(x, x + 1)));
      }
      for (var y = 1; y < size; y++) {
        for (var x = 0; x < size - y; x++) {
          for (var i = 0; i < y; i++) {
            for (var n1 : cyk(i, x)) {
              for (var n2 : cyk(y - i - 1, x + i + 1)) {
                for (var rule : rules.getOrDefault(List.of(n1, n2), emptySet())) {
                  cyk(y, x).add(rule);
                }
              }
            }
          }
        }
      }
    }

    private Set<String> cyk(int y, int x) {
      if (cyk[y][x] == null) {
        cyk[y][x] = new HashSet<>();
      }
      return cyk[y][x];
    }

    private boolean inGrammar() {
      return cyk[size - 1][0] != null && cyk[size - 1][0].contains(CnfGrammar.START_SYMBOL);
    }
  }


  private static class CnfGrammar {
    private static final String START_SYMBOL = "0";
    private final Map<String, Set<List<String>>> rules = new HashMap<>();
    private final Map<List<String>, Set<String>> reverseRules = new HashMap<>();

    public CnfGrammar(Grammar grammar) {
      rules.putAll(grammar.rules);
      startStep();
      // TERM step (eliminate rules with non-solitary terminals) not needed in our grammar
      binStep();
      // DEL step (eliminate empty string rules) not needed in our grammar
      unitStep();
      createReverseRules();
    }

    private void startStep() {
      rules.computeIfAbsent(START_SYMBOL, s -> new HashSet<>()).add(Collections.singletonList(START_SYMBOL));
    }

    private void binStep() {
      for (var rule : copyOf(rules.entrySet())) {
        var lhs = rule.getKey();
        var rhsValues = rule.getValue();
        for (var rhs : copyOf(rhsValues)) {
          if (rhs.size() > 2) {
            rules.get(lhs).remove(rhs);
            binStep(lhs, rhs);
          }
        }
      }
    }

    private void binStep(String lhs, List<String> rhs) {
      if (rhs.size() <= 2) {
        rules.computeIfAbsent(lhs, s -> new HashSet<>()).add(rhs);
      } else {
        var nextRhs = rhs.subList(1, rhs.size());
        var nextLhs = String.join(",", nextRhs);
        binStep(lhs, List.of(rhs.get(0), nextLhs));
        binStep(nextLhs, nextRhs);
      }
    }

    private void unitStep() {
      var numMutations = 0;
      for (var rule : copyOf(rules.entrySet())) {
        var lhs = rule.getKey();
        var rhsValues = rule.getValue();
        if (rhsValues.size() == 0) {
          rules.remove(lhs);
          numMutations++;
        } else {
          for (var rhs : copyOf(rhsValues)) {
            if (rhs.size() == 0) {
              rules.get(lhs).remove(rhs);
              numMutations++;
            } else if (rhs.size() == 1 && !isTerminal(rhs)) {
              rules.get(lhs).remove(rhs);
              rules.get(lhs).addAll(rules.get(rhs.get(0)));
              numMutations++;
            }
          }
        }
      }
      if (numMutations > 0) {
        unitStep();
      }
    }

    private void createReverseRules() {
      rules.forEach((k, values) -> values.forEach(v -> reverseRules.computeIfAbsent(v, s -> new HashSet<>()).add(k)));
    }

    private boolean isTerminal(List<String> symbols) {
      return symbols.stream().allMatch(symbol -> symbol.equals("a") || symbol.equals("b"));
    }
  }

  private static class Grammar {
    private final SetMap<String, List<String>> rules = new SetMap<>();

    public Grammar(String inputString) {
      for (var line : inputString.split("\n")) {
        generateRule(line);
      }
    }

    private void generateRule(String ruleString) {
      if (ruleString.contains(": ")) {
        var lhsString = ruleString.split(": ")[0];
        var rhsString = ruleString.split(": ")[1];
        if (rhsString.contains(" | ")) {
          generateRule(lhsString + ": " + rhsString.replaceAll("^(.*?) \\| (.*)$", "$1"));
          generateRule(lhsString + ": " + rhsString.replaceAll("^(.*?) \\| (.*)$", "$2"));
        } else {
          rules.computeIfAbsent(lhsString, s -> new HashSet<>()).add(tokenize(rhsString));
        }
      }
    }
  }

  private static List<String> tokenize(String input) {
    var tokens = new ArrayList<String>();
    if (input.matches("^[ab]+$")) {
      for (var c : input.toCharArray()) {
        tokens.add(String.valueOf(c));
      }
    } else {
      var matcher = Pattern.compile("(\\d+)|\"([ab])\"").matcher(input);
      while (matcher.find()) {
        tokens.add(Optional.ofNullable(matcher.group(1)).orElse(matcher.group(2)));
      }
    }
    return tokens;
  }
}
