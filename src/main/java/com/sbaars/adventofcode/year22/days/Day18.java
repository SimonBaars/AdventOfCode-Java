package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.HexDirection;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

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
    connecting.removeAll(exterior);
    List<Set<Loc3D>> pockets = connecting.stream().map(l -> new HashSet<>(Set.of(l))).collect(Collectors.toCollection(ArrayList::new));
    int trapped = 0;
    for(int i = 0; i<1000; i++) {
      for(int j = 0; j<pockets.size(); j++) {
        Set<Loc3D> pocket = pockets.get(j);
        Set<Loc3D> s = new HashSet<>(pocket);
        pocket.addAll(pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet()));
        if(s.size() == pocket.size()) {
          trapped++;
          pockets.remove(j);
          j--;
        } else if(pocket.size()>2000) {
          pockets.remove(j);
          j--;
        }
      }
    }
    return (locs.size()*6L) - exterior.size() - trapped;
  }
}
