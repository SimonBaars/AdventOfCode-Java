package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.HexDirection;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.map.LongCountMap.toCountMap;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day18 extends Day2022 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  @Override
  public Object part1() {
    List<Loc3D> locs = dayStream().map(s -> readString(s, "%n,%n,%n", Loc3D.class)).toList();
    long connecting = locs.stream()
        .flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1)))
        .filter(locs::contains)
        .count();
    return (locs.size() * 6L) - connecting;
  }

  @Override
  public Object part2() {
    List<Loc3D> locs = dayStream().map(s -> readString(s, "%n,%n,%n", Loc3D.class)).toList();
    List<Loc3D> connecting = locs.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).collect(Collectors.toCollection(ArrayList::new));
    List<Loc3D> exterior = connecting.stream().filter(locs::contains).toList();
    LongCountMap<Set<Loc3D>> pockets = connecting.stream().filter(l -> !locs.contains(l)).map(l -> new HashSet<>(Set.of(l))).collect(toCountMap());
    var trapped = new AtomicLong();
    for (int i = 0; i < 100; i++) {
      LongCountMap<Set<Loc3D>> newPockets = new LongCountMap<>();
      pockets.forEach((pocket, n) -> {
        Set<Loc3D> spread = pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet());
        spread.addAll(pocket);
        if (spread.size() == pocket.size()) {
          trapped.addAndGet(n);
        } else if (spread.size() <= connecting.size()) {
          var matching = newPockets.keySet().stream().filter(e -> e.stream().anyMatch(spread::contains)).findAny();
          matching.ifPresentOrElse(c -> {
            spread.addAll(c);
            newPockets.increment(spread, n + newPockets.remove(c));
          }, () -> newPockets.increment(spread, n));
        }
      });
      pockets = newPockets;
    }
    return (locs.size() * 6L) - exterior.size() - trapped.get();
  }
}
