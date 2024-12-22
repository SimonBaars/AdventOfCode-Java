package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;

import static com.sbaars.adventofcode.util.MuUtils.connectedPairs;
import static java.lang.Integer.parseInt;

import java.util.HashMap;
import java.util.Map;

public class Day21 extends Day2024 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  private static final InfiniteGrid arrows = new InfiniteGrid(" ^A\n<v>");
  private static final InfiniteGrid numpad = new InfiniteGrid("789\n456\n123\n 0A");
  private static final Loc a = arrows.find('A');

  public record State(Loc s, Loc e, int layers) {}

  @Override
  public Object part1() {
    return computeScore(3);
  }

  @Override
  public Object part2() {
    return computeScore(26);
  }

  private long computeScore(int limit) {
    Map<State, Long> cache = new HashMap<>();
    return dayStream().mapToLong(line ->
      parseInt(line.substring(0, 3)) * connectedPairs(("A" + line).chars().boxed()).mapToLong((s, e) ->
        shortest(new State(numpad.find((char)(int)s), numpad.find((char)(int)e), limit), cache, limit)
      ).sum()
    ).sum();
  }

  private long shortest(State state, Map<State, Long> cache, int limit) {
    if (cache.containsKey(state)) {
      return cache.get(state);
    } else if (state.layers == 0) {
      return 1L;
    }
    Loc v = state.e.y < state.s.y ? arrows.find('^') : (state.e.y > state.s.y ? arrows.find('v') : null);
    Loc h = state.e.x < state.s.x ? arrows.find('<') : (state.e.x > state.s.x ? arrows.find('>') : null);
    cache.put(state, calculateResult(state.s, state.e, h, v, state.layers, cache, limit, state.layers < limit));
    return cache.get(state);
  }

  private long calculateResult(Loc startPos, Loc endPos, Loc h, Loc v, int layers, Map<State, Long> cache, int limit, boolean b) {
    if (h == null && v == null) {
      return shortest(new State(a, a, layers - 1), cache, limit);
    } else if (h == null) {
      return shortest(new State(a, v, layers - 1), cache, limit)
              + (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(new State(v, v, layers - 1), cache, limit)
              + shortest(new State(v, a, layers - 1), cache, limit);
    } else if (v == null) {
      return shortest(new State(a, h, layers - 1), cache, limit)
              + (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(new State(h, h, layers - 1), cache, limit)
              + shortest(new State(h, a, layers - 1), cache, limit);
    }
    return Math.min(
      endPos.getX() == 0 && (b || startPos.getY() == 3) ? Long.MAX_VALUE : 
        shortest(new State(a, h, layers - 1), cache, limit) +
        (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(new State(h, h, layers - 1), cache, limit) +
        shortest(new State(h, v, layers - 1), cache, limit) +
        (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(new State(v, v, layers - 1), cache, limit) +
        shortest(new State(v, a, layers - 1), cache, limit),
      startPos.getX() == 0 && (b || endPos.getY() == 3) ? Long.MAX_VALUE : 
        shortest(new State(a, v, layers - 1), cache, limit) +
        (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(new State(v, v, layers - 1), cache, limit) +
        shortest(new State(v, h, layers - 1), cache, limit) +
        (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(new State(h, h, layers - 1), cache, limit) +
        shortest(new State(h, a, layers - 1), cache, limit)
    );
  }
}
