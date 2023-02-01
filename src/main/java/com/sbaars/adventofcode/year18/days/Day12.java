package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.SmartArray;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.List;

import static com.sbaars.adventofcode.common.SmartArray.toSmartArray;
import static com.sbaars.adventofcode.util.AOCUtils.fixedPoint;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Day12 extends Day2018 {
  public Day12() {
    super(12);
  }

  public static void main(String[] args) {
    new Day12().printParts();
  }

  public record Input(String initial, List<Note> notes) {}
  public record Note(String adjecent, char c) {}

  @Override
  public Object part1() {
    Input in = readString(day(), "initial state: %s\n\n%l(%s => %c)", "\n", Input.class, Note.class);
    var plants = range(0, in.initial.length()).filter(i -> in.initial.charAt(i) == '#').boxed().collect(toSmartArray());
    var res = fixedPoint(plants, p ->
            range(p.get(0) - 2, p.get(p.size() -1) + 2)
                    .filter(i -> plantGrows(in.notes, p, i))
                    .boxed().collect(toSmartArray()), 20);
    return res.stream().mapToInt(e -> e).sum();
  }

  private boolean plantGrows(List<Note> notes, SmartArray<Integer> arr, int i) {
    return notes
            .stream()
            .filter(n -> zip(n.adjecent.chars().boxed(), rangeClosed(-2, 2).boxed()).allMatch(e -> (e.a() == '#') == arr.contains(i+e.b())))
            .map(n -> n.c == '#')
            .findFirst()
            .orElse(false);
  }

  @Override
  public Object part2() {
    return "";
  }
}
