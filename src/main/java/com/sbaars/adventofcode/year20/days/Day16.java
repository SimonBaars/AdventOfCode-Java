package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.subarray;

public class Day16 extends Day2020 {
  private final Rule[] rules;
  private final long[] myTicket;
  private final List<List<Long>> tickets;

  public Day16() {
    super(16);
    String[] input = day().split("\n\n");
    this.rules = stream(input[0].split("\n")).map(s -> readString(s, "%s: %n-%n or %n-%n", Rule.class)).toArray(Rule[]::new);
    this.myTicket = stream(input[1].split("\n")[1].split(",")).mapToLong(Long::parseLong).toArray();
    String[] ticketStrings = input[2].split("\n");
    this.tickets = stream(subarray(ticketStrings, 1, ticketStrings.length)).map(s -> stream(s.split(",")).map(Long::parseLong).collect(toList())).collect(toList());
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  @Override
  public Object part1() {
    return tickets.stream().flatMapToLong(t -> t.stream().filter(n -> stream(rules).noneMatch(r -> r.check(n))).mapToLong(e -> e)).sum();
  }

  @Override
  public Object part2() {
    List<List<Long>> valid = tickets.stream().filter(t -> t.stream().allMatch(n -> stream(rules).anyMatch(r -> r.check(n)))).collect(toList());

    ListMap<Integer, Rule> ruleIndex = new ListMap<>();
    for (Rule r : rules) {
      for (int j = 0; j < valid.get(0).size(); j++) {
        int finalJ = j;
        if (valid.stream().allMatch(t -> r.check(t.get(finalJ)))) {
          ruleIndex.addTo(j, r);
        }
      }
    }

    Optional<Map.Entry<Integer, List<Rule>>> rs;
    Set<Integer> indices = new HashSet<>();
    while ((rs = ruleIndex.entrySet().stream().filter(e -> e.getValue().size() == 1 && !indices.contains(e.getKey())).findAny()).isPresent()) {
      Map.Entry<Integer, List<Rule>> r = rs.get();
      int index = r.getKey();
      for (int i = 0; i < rules.length; i++) {
        Map.Entry<Integer, List<Rule>> t = new ArrayList<>(ruleIndex.entrySet()).get(i);
        if (t.getKey() != index) {
          t.getValue().remove(r.getValue().get(0));
        }
      }
      indices.add(index);
    }

    return ruleIndex.entrySet().stream().filter(e -> e.getValue().stream().anyMatch(Rule::isDeparture)).mapToLong(e -> myTicket[e.getKey()]).reduce((a, b) -> a * b).getAsLong();
  }

  public record Rule(String name, long lower1, long upper1, long lower2, long upper2) {
    public boolean check(long val) {
      return (val >= lower1 && val <= upper1) || (val >= lower2 && val <= upper2);
    }

    public boolean isDeparture() {
      return name.startsWith("departure");
    }
  }
}
