package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.SmartArray;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.SmartArray.toSmartArray;
import static com.sbaars.adventofcode.util.AOCUtils.connectedPairs;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.util.Solver.solve;
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
    return sum().applyAsLong(growth().limit(21).reduce((a, b) -> b).get());
  }

  @Override
  public Object part2() {
//    connectedPairs(growth().map(a -> a.stream().mapToLong(e -> e).sum())).map(p -> p.b() - p.a()).limit(1000).forEach(System.out::println);
//    growth().limit(100).map(a -> a.stream().mapToLong(e -> e).sum()).forEach(System.out::println);
//    return 0;
//    return growth().limit(50000000001L).reduce((a, b) -> b).get().stream().mapToInt(e -> e).sum();
//    System.out.println(Arrays.toString(growth().limit(10000).toArray()));
//    for(long i = 0; i<50000000000L; i++);
//    return 0;
    return solve(growth(), sum(), 50000000000L);
  }

  private static ToLongFunction<SmartArray<Integer>> sum() {
    return a -> a.stream().mapToLong(e -> e).sum();
  }

  private Stream<SmartArray<Integer>> growth() {
    Input in = readString(day(), "initial state: %s\n\n%l(%s => %c)", "\n", Input.class, Note.class);
    var plants = range(0, in.initial.length()).filter(i -> in.initial.charAt(i) == '#').boxed().collect(toSmartArray());
    return Stream.iterate(plants, p -> range(p.get(0) - 2, p.get(p.size() -1) + 2)
                    .filter(i -> plantGrows(in.notes, p, i))
                    .boxed().collect(toSmartArray()));
  }

  private boolean plantGrows(List<Note> notes, SmartArray<Integer> arr, int i) {
    return notes
            .stream()
            .filter(n -> zip(n.adjecent.chars().boxed(), rangeClosed(-2, 2).boxed()).allMatch(e -> (e.a() == '#') == arr.contains(i+e.b())))
            .map(n -> n.c == '#')
            .findFirst()
            .orElse(false);
  }
}
