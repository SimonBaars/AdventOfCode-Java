package com.sbaars.adventofcode.common.grid;

import com.google.mu.util.stream.BiStream;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.location.Loc;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.Direction.eight;
import static com.sbaars.adventofcode.common.Direction.four;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class InfiniteGrid implements Grid {
  public final Map<Loc, Character> grid;

  public InfiniteGrid(char[][] g) {
    this(g, ' ');
  }

  public InfiniteGrid(char c, long width, long height) {
    this();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid.put(new Loc(j, i), c);
      }
    }
  }

  public InfiniteGrid(char[][] g, char transparent) {
    this();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[i].length; j++) {
        if (transparent != g[i][j]) {
          grid.put(new Loc(j, i), g[i][j]);
        }
      }
    }
  }

  public InfiniteGrid(Collection<Loc> locs, char c) {
    this();
    locs.forEach(l -> grid.put(l, c));
  }

  public InfiniteGrid(Map<Loc, Character> grid) {
    this.grid = new HashMap<>(grid);
  }

  public InfiniteGrid() {
    this(new HashMap<>());
  }

  public InfiniteGrid(String s) {
    this(new CharGrid(s).grid);
  }

  public InfiniteGrid(InfiniteGrid g) {
    this(g.grid);
  }

  public IntStream iterate() {
    return grid.values().stream().mapToInt(e -> e);
  }

  public Optional<Character> get(Loc p) {
    return grid.containsKey(p) ? of(grid.get(p)) : empty();
  }

  public Optional<Character> get(long x, long y) {
    return get(x, y);
  }

  public Character getOptimistic(long x, long y) {
    return get(x, y).orElseThrow();
  }

  public Character getOptimistic(Loc l) {
    return get(l).orElseThrow();
  }

  public char getChar(Loc p) {
    return get(p).orElse((char) 0);
  }

  public void set(Loc p, char c) {
    grid.put(p, c);
  }

  public String toString() {
    return toCharGrid().toString();
  }

  public static Collector<Loc, Map<Loc, Character>, InfiniteGrid> toInfiniteGrid(char c) {
    final Supplier<Map<Loc, Character>> supplier = HashMap::new;
    final BiConsumer<Map<Loc, Character>, Loc> accumulator = (a, b) -> a.put(b, c);
    final BinaryOperator<Map<Loc, Character>> combiner = (a, b) -> {
      a.putAll(b);
      return a;
    };
    final Function<Map<Loc, Character>, InfiniteGrid> finisher = InfiniteGrid::new;
    return Collector.of(supplier, accumulator, combiner, finisher);
  }

  public static Collector<Pair<Loc, Character>, Map<Loc, Character>, InfiniteGrid> toInfiniteGrid() {
    final Supplier<Map<Loc, Character>> supplier = HashMap::new;
    final BiConsumer<Map<Loc, Character>, Pair<Loc, Character>> accumulator = (a, b) -> a.put(b.a(), b.b());
    final BinaryOperator<Map<Loc, Character>> combiner = (a, b) -> {
      a.putAll(b);
      return a;
    };
    final Function<Map<Loc, Character>, InfiniteGrid> finisher = InfiniteGrid::new;
    return Collector.of(supplier, accumulator, combiner, finisher);
  }

  public boolean canPlace(InfiniteGrid g, long r, long c) {
    return g.grid.keySet().stream().map(l -> l.move(c, r)).noneMatch(grid::containsKey);
  }

  public boolean canPlace(InfiniteGrid g) {
    return g.grid.keySet().stream().noneMatch(grid::containsKey);
  }

  public long minY() {
    return grid.keySet().stream().mapToLong(e -> e.y).min().orElse(0);
  }

  public long maxY() {
    return grid.keySet().stream().mapToLong(e -> e.y).max().orElse(0);
  }

  public long minX() {
    return grid.keySet().stream().mapToLong(e -> e.x).min().orElse(0);
  }

  public long maxX() {
    return grid.keySet().stream().mapToLong(e -> e.x).max().orElse(0);
  }

  public InfiniteGrid move(long x, long y) {
    return new InfiniteGrid(grid.entrySet().stream().map(e -> new Pair<>(e.getKey().move(x, y), e.getValue())).collect(Collectors.toMap(Pair::a, Pair::b)));
  }

  public void place(InfiniteGrid s) {
    s.grid.forEach(this::set);
  }

  public long height() {
    return maxY() + 1 - minY();
  }

  public long width() {
    return maxX() + 1 - minX();
  }

  public boolean contains(Loc p) {
    return grid.containsKey(p);
  }

  public Stream<Loc> stream() {
    return grid.keySet().stream();
  }

  public BiStream<Loc, Character> streamChars() {
    BiStream.Builder<Loc, Character> builder = BiStream.builder();
    grid.forEach(builder::add);
    return builder.build();
  }

  public BiStream<List<Loc>, String> findGroups(Predicate<Character> predicate, boolean horizontal) {
    BiStream.Builder<List<Loc>, String> builder = BiStream.builder();
    StringBuilder currentGroup = new StringBuilder();
    List<Loc> currentLocs = new ArrayList<>();
    long width = width();
    long height = height();
    for (int i = 0; i < (horizontal ? width : height); i++) {
      for (int j = 0; j < (horizontal ? height : width); j++) {
        Loc l = new Loc(horizontal ? j : i, horizontal ? i : j);
        Character c = getOptimistic(l);
        boolean matchesPredicate = predicate.test(c);
        if (matchesPredicate) {
          currentGroup.append(c);
          currentLocs.add(l);
        }
        if ((!matchesPredicate && !currentGroup.isEmpty()) || j == (horizontal ? height : width) - 1) {
          builder.add(new ArrayList<>(currentLocs), currentGroup.toString());
          currentGroup.delete(0, currentGroup.length());
          currentLocs.clear();
        }
      }
    }
    return builder.build();
  }

  public Stream<Loc> findAll(Character c) {
    return stream().filter(l -> contains(l) && getOptimistic(l) == c);
  }

  public Stream<Loc> findAround(Predicate<Character> predicate, Stream<Loc> locs, boolean diagonal) {
    return locs.flatMap(l -> (diagonal ? eight() : four()).map(d -> d.move(l))).filter(l -> get(l).filter(predicate).isPresent());
  }

  public void removeIf(BiPredicate<Loc, Character> p) {
    new HashSet<>(grid.entrySet()).stream().filter(e -> p.test(e.getKey(), e.getValue())).forEach(e -> grid.remove(e.getKey()));
  }

  public char[][] getGrid() {
    return toCharGrid().getGrid();
  }

  private CharGrid toCharGrid() {
    long minX = minX();
    long minY = minY();
    CharGrid g = new CharGrid(' ', new Loc(width(), height()));
    grid.forEach((p, i) -> g.set(new Loc(p.x - minX, p.y - minY), i));
    return g;
  }

  public long area() {
    return (maxY() - minY()) + (maxX() - minX());
  }

  public void replace(char find, char replaceWith) {
    grid.entrySet().stream().filter(e -> e.getValue() == find).forEach(e -> grid.put(e.getKey(), replaceWith));
  }

  public Set<Loc> floodFill(Loc start, Predicate<Character> predicate) {
    Set<Loc> output = new HashSet<>();
    Set<Loc> toCheck = new HashSet<>();
    toCheck.add(start);
    while (!toCheck.isEmpty()) {
      Set<Loc> newToCheck = new HashSet<>();
      for (Loc l : toCheck) {
        if (predicate.test(getChar(l))) {
          output.add(l);
          newToCheck.addAll(four().map(d -> d.move(l)).filter(this::contains).filter(l2 -> !output.contains(l2)).collect(Collectors.toSet()));
        }
      }
      toCheck = newToCheck;
    }
    return output;
  }

  public InfiniteGrid withBorder(long thickness, char borderChar) {
    InfiniteGrid g = new InfiniteGrid(this);
    long minX = minX();
    long minY = minY();
    long maxX = maxX();
    long maxY = maxY();
    for (long i = minY - thickness; i <= maxY + thickness; i++) {
      for (long j = 1; j <= thickness; j++) {
        g.set(new Loc(minX - j, i), borderChar);
        g.set(new Loc(maxX + j, i), borderChar);
      }
    }
    for (long i = minX - thickness; i <= maxX + thickness; i++) {
      for (long j = 1; j <= thickness; j++) {
        g.set(new Loc(i, minY - j), borderChar);
        g.set(new Loc(i, maxY + j), borderChar);
      }
    }
    return g;
  }

  public Map<Long, List<Loc>> columns() {
    return stream().sorted().collect(Collectors.groupingBy(Loc::getX));
  }

  public Map<Long, List<Character>> columnValues() {
    return columns().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(this::getChar).collect(Collectors.toList())));
  }

  public Map<Long, List<Loc>> rows() {
    return stream().sorted().collect(Collectors.groupingBy(Loc::getY));
  }

  public Map<Long, List<Character>> rowValues() {
    return rows().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(this::getChar).collect(Collectors.toList())));
  }
}
