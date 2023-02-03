package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.map.CountMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.*;

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
    return solution(true);
  }

  @Override
  public Object part2() {
    return solution(false);
  }

  private String solution(boolean part1) {
    return findLoc(part1).toString().replace(" ", "");
  }

  private Loc findLoc(boolean part1) {
    var tracks = new InfiniteGrid(dayGrid());
    var carts = new InfiniteGrid(tracks);
    var cartMap = new CountMap<Loc>();
    long maxY = carts.maxY();
    carts.removeIf((l, c) -> List.of('|', '-', '+', '/', '\\').contains(c));
    carts.grid.forEach((l, c) -> cartMap.put(l, 0));
    while(cartMap.size() > 1) {
      for (Loc l : carts.grid.keySet().stream().sorted().toList()) {
        if(carts.get(l).isEmpty()) continue;
        Direction d = caretToDirection(carts.get(l).get());
        Loc destination = d.move(l);
        char atDestination = tracks.get(destination).orElse(' ');
        if(carts.get(destination).isPresent()) {
          if(part1) return destination;
          carts.grid.remove(l);
          carts.grid.remove(destination);
          cartMap.remove(l);
          cartMap.remove(destination);
          continue;
        } else if (atDestination == '+') {
          int turn = switch (cartMap.get(l) % 3) {
            case 0 -> 270;
            case 1 -> 0;
            case 2 -> 90;
            default -> throw new IllegalStateException("Invalid state: " + cartMap.get(l));
          };
          d = d.turnDegrees(turn);
          cartMap.increment(l);
        } else if (atDestination == '\\') {
          d = d.turn(d == EAST || d == WEST);
        } else if (atDestination == '/') {
          d = d.turn(d == NORTH || d == SOUTH);
        }
        carts.grid.remove(l);
        int state = cartMap.remove(l);
        carts.set(destination, d.getCaret());
        cartMap.put(destination, state);
      }
    }
    return carts.stream().findFirst().get();
  }
}
