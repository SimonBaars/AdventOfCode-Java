package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Builder;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sbaars.adventofcode.common.Direction.*;
import static java.util.stream.Collectors.toSet;

public class Day23 extends Day2023 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  public record Path(Set<Loc> visited, Loc currentLoc) {
  }

  @Override
  public Object part1() {
    return solve(false);
  }

  @Override
  public Object part2() {
    return solve(true);
  }

  private long solve(boolean part2) {
    var input = new InfiniteGrid(dayGrid());
    Loc start = new Loc(1, 0);
    Builder<Set<Path>> curr = new Builder<>(HashSet::new);
    curr.get().add(new Path(new HashSet<>(List.of(start)), start));
    Loc target = new Loc(input.width() - 2, input.height() - 1);
    long longest = 0;
    while (!curr.get().isEmpty()) {
      curr.setNew(curr.get().parallelStream().flatMap(path -> {
        Loc loc = path.currentLoc;
        return four()
            .filter(d -> checkDir(d, input.getChar(loc), part2) && checkDir(d, input.getChar(d.move(loc)), part2))
            .map(d -> d.move(loc))
            .filter(l -> !path.visited.contains(l))
            .map(l -> new Path(new HashSet<>(path.visited) {{
              add(l);
            }}, l));
      }).collect(toSet()));
      longest = Math.max(longest, curr.getNew().stream().mapToInt(p -> p.visited.size() - 1).max().getAsInt());
      curr.getNew().stream().findAny().ifPresent(x -> System.out.println(x.visited.size() + " " + curr.getNew().size()));
      curr.getNew().removeIf(p -> p.currentLoc.equals(target));
      curr.refresh();
    }
    return longest;
  }

  private boolean checkDir(Direction d, char c, boolean part2) {
    return switch (c) {
      case '>' -> part2 || d == EAST;
      case '<' -> part2 || d == WEST;
      case '^' -> part2 || d == NORTH;
      case 'v' -> part2 || d == SOUTH;
      case '#', 0 -> false;
      default -> true;
    };
  }
}
