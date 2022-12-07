package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Long.parseLong;

public class Day24 extends Day2021 implements HasRecursion {

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  @Override
  public Object part1() {
    return calcSolution(9, false);
  }

  private String calcSolution(int i, boolean part) {
    var in = dayStream().map(e -> new Instruction(e.split(" "))).toList();
    Map<Character, Long> vars = new HashMap<>();
    Map<String, String> processed = new HashMap<>(8192, 0.5f);
    return processNumbers(new State(vars, processed, in, i, part), 0).get();
  }

  @Override
  public Object part2() {
    return calcSolution(3, true);
  }

  public record Instruction (String op, String n1, String n2){
    public Instruction (String[] s){
      this(s[0], s[1], s.length>2 ? s[2] : "");
    }

    public int execute(Map<Character, Long> vars) {
      char c = n1.charAt(0);
      switch (op) {
        case "add" -> vars.put(c, getVar(vars, n1) + getVar(vars, n2));
        case "mul" -> vars.put(c, getVar(vars, n1) * getVar(vars, n2));
        case "div" -> vars.put(c, getVar(vars, n1) / getVar(vars, n2));
        case "mod" -> vars.put(c, getVar(vars, n1) % getVar(vars, n2));
        case "eql" -> vars.put(c, getVar(vars, n1) == getVar(vars, n2) ? 1L : 0L);
        default -> throw new IllegalStateException("Don't care " + op);
      }
      return 0;
    }

    public boolean isInput(){
      return op.equals("inp");
    }

    public long getVar(Map<Character, Long> vars, String v) {
      char c = v.charAt(0);
      if(c >= '0' && c <= '9' || c=='-') return parseLong(v);
      if(vars.containsKey(c)) {
        return vars.get(c);
      }
      return 0;
    }
  }

  public record State(Map<Character, Long> vars, Map<String, String> processed, List<Instruction> in, int firstNum, boolean p) {
    public State clone(Map<Character, Long> vars) {
      return new State(vars, processed, in, firstNum, p);
    }
  }

  public Optional<String> findBestNumbers(State s, int curInstruction) {
    String state= curInstruction + s.vars.toString();
    if (s.processed.containsKey(state)) {
      return Optional.ofNullable(s.processed.get(state));
    }
    Optional<String> res = processNumbers(s, curInstruction);
    s.processed.put(state, res.orElse(null));
    return res;
  }

  public Optional<String> processNumbers(State s, int curInstruction) {
    if (curInstruction == s.in.size()) {
      long z = s.vars.get('z');
      if (z==0) return Optional.of("");
      return Optional.empty();
    }

    Instruction line = s.in.get(curInstruction);
    if (line.isInput()) {
      for(long i=s.p ? 1 : 9; s.p ? i<=9 : i>0; i+=s.p?1:-1) {
        long e = curInstruction<5 ? s.firstNum : i;
        Map<Character, Long> n = new HashMap<>(s.vars);
        n.put(line.n1.charAt(0), e);
        Optional<String> sol = findBestNumbers(s.clone(n), curInstruction+1);
        if (sol.isPresent()) return sol.map(s2 -> e+s2);
      }
      return Optional.empty();
    } else {
      line.execute(s.vars);
      return processNumbers(s, curInstruction+1);
    }
  }
}
