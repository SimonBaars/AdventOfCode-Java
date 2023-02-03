package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.map.CountMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day13 extends Day2018 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  @Override
  public Object part1() {
    var tracks = new InfiniteGrid(dayGrid());
    var carts = new InfiniteGrid(tracks);
    var cartMap = new CountMap<Loc>();
    tracks.replace('>', '-');
    tracks.replace('<', '-');
    tracks.replace('v', '|');
    tracks.replace('^', '|');
    carts.removeIf((l, c) -> List.of('|', '-', '+', '/', '\\').contains(c));
    carts.grid.forEach((l, c) -> cartMap.put(l, 0));
    while(true) {
      for (Map.Entry<Loc, Character> e : new HashSet<>(carts.grid.entrySet())) {
        Direction d = caretToDirection(e.getValue());
        Loc destination = d.move(e.getKey());
        char atDestination = tracks.get(destination).orElse(' ');
        if(carts.get(destination).isPresent()) {
          return destination.toString().replace(" ", "");
        } else if (atDestination == '+') {
          int turn = switch (cartMap.get(e.getKey()) % 3) {
            case 0 -> 270;
            case 1 -> 0;
            case 2 -> 90;
            default -> throw new IllegalStateException("Invalid state: " + cartMap.get(e.getKey()));
          };
          d = d.turnDegrees(turn);
          cartMap.increment(e.getKey());
        } else if (atDestination == '\\') {
          d = d.turn(d == EAST || d == WEST);
        } else if (atDestination == '/') {
          d = d.turn(d == NORTH || d == SOUTH);
        }
        carts.grid.remove(e.getKey());
        int state = cartMap.remove(e.getKey());
        carts.set(destination, d.getCaret());
        cartMap.put(destination, state);
      }
//      var all = new InfiniteGrid(tracks);
//      all.grid.putAll(carts.grid);
//      System.out.println(all);
//      System.out.println("-----------");
    }
  }

  @Override
  public Object part2() {
    var tracks = new InfiniteGrid(dayGrid());
//    var carts = new InfiniteGrid(tracks);
    var carts = new Builder<InfiniteGrid>(new InfiniteGrid(tracks), InfiniteGrid.class);
    var cartMap = new CountMap<Loc>();
    tracks.replace('>', '-');
    tracks.replace('<', '-');
    tracks.replace('v', '|');
    tracks.replace('^', '|');
    carts.get().removeIf((l, c) -> List.of('|', '-', '+', '/', '\\').contains(c));
    carts.get().grid.forEach((l, c) -> cartMap.put(l, 0));
    while(true) {
      for (Map.Entry<Loc, Character> e : new HashSet<>(carts.grid.entrySet())) {
        if(carts.get(e.getKey()).isEmpty()) continue;
        Direction d = caretToDirection(e.getValue());
        Loc destination = d.move(e.getKey());
        char atDestination = tracks.get(destination).orElse(' ');
        if(carts.get(destination).isPresent()) {
          carts.grid.remove(e.getKey());
          carts.grid.remove(destination);
          cartMap.remove(e.getKey());
          cartMap.remove(destination);
          continue;
        } else if (atDestination == '+') {
          int turn = switch (cartMap.get(e.getKey()) % 3) {
            case 0 -> 270;
            case 1 -> 0;
            case 2 -> 90;
            default -> throw new IllegalStateException("Invalid state: " + cartMap.get(e.getKey()));
          };
          d = d.turnDegrees(turn);
          cartMap.increment(e.getKey());
        } else if (atDestination == '\\') {
          d = d.turn(d == EAST || d == WEST);
        } else if (atDestination == '/') {
          d = d.turn(d == NORTH || d == SOUTH);
        }
        carts.grid.remove(e.getKey());
        int state = cartMap.remove(e.getKey());
        carts.set(destination, d.getCaret());
        cartMap.put(destination, state);
      }

      if(cartMap.size() == 1) {
        var all = new InfiniteGrid(tracks);
        all.grid.putAll(carts.grid);
        System.out.println(all);
        System.out.println("-----------");
        return carts.stream().findFirst().get().toString().replace(" ", "");
      }
//      var all = new InfiniteGrid(tracks);
//      all.grid.putAll(carts.grid);
//      System.out.println(all);
//      System.out.println("-----------");
    }
  }
}
