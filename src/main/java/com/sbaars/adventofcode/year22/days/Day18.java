package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.HexDirection;
import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year19.util.CountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.allPairs;
import static java.lang.Math.round;

public class Day18 extends Day2022 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    Day18 d = new Day18();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
    List<Loc3D> locs = dayStream().map(s -> readString(s, "%n,%n,%n", Loc3D.class)).toList();
    System.out.println(allPairs(locs).map(l -> round(l.a().distance(l.b())) == 1L).count());
    return (locs.size()*6L)-locs.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> locs.contains(l)).count();
  }

  @Override
  public Object part2() {
    CountMap<Loc3D> cm = new CountMap<>();
    List<Loc3D> locs = dayStream().map(s -> readString(s, "%n,%n,%n", Loc3D.class)).toList();
    List<Loc3D> connecting = locs.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).collect(Collectors.toCollection(ArrayList::new));
    List<Loc3D> exterior = connecting.stream().filter(l -> locs.contains(l)).collect(Collectors.toCollection(ArrayList::new));
    connecting.removeAll(exterior);
    List<Set<Loc3D>> pockets = connecting.stream().map(l -> new HashSet<>(Set.of(l))).collect(Collectors.toCollection(ArrayList::new));
    int trapped = 0;
    for(int i = 0; i<5000; i++) {
      for(int j = 0; j<pockets.size(); j++) {
        Set<Loc3D> pocket = pockets.get(j);
        Set<Loc3D> s = new HashSet<>(pocket);
        pocket.addAll(pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet()));
        if(s.size() == pocket.size()) {
          trapped++;
          pockets.remove(j);
          j--;
        } else if(pocket.size()>50000) {
          pockets.remove(j);
          j--;
        }
      }
      System.out.println(i);
    }

    return (locs.size()*6L) - exterior.size() - trapped;
  }
}
