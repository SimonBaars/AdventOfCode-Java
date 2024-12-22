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
  private static final InfiniteGrid numpad = new InfiniteGrid("""
      789
      456
      123
       0A
      """);
  private static final Loc a = arrows.find('A');

  @Override
  public Object part1() {
    return computeScore(3);
  }

  @Override
  public Object part2() {
    return computeScore(26);
  }

  private long computeScore(int layersLimit) {
    Map<String, Long> cache = new HashMap<>();
    return dayStream().mapToLong(line ->
      parseInt(line.substring(0, 3)) * connectedPairs(("A" + line).chars().boxed()).mapToLong((s, e) ->
        shortest(numpad.find((char)(int)s), numpad.find((char)(int)e), layersLimit, cache, layersLimit)
      ).sum()
    ).sum();
  }

  private long shortest(Loc start, Loc end, int layers, Map<String, Long> cache, int layersLimit) {
    String key = start.toString() + "|" + end.toString() + "|" + layers;
    if (cache.containsKey(key)) {
      return cache.get(key);
    } else if (layers == 0) {
      return 1L;
    }
    Loc s = (start != null) ? start : a;
    Loc e = (end != null) ? end : a;
    Loc v = e.y < s.y ? arrows.find('^') : (e.y > s.y ? arrows.find('v') : null);
    Loc h = e.x < s.x ? arrows.find('<') : (e.x > s.x ? arrows.find('>') : null);
    cache.put(key, calculateResult(s, e, h, v, layers, cache, layersLimit, layers < layersLimit));
    return cache.get(key);
  }

  private long calculateResult(Loc startPos, Loc endPos, Loc h, Loc v, int layers, Map<String, Long> cache, int layersLimit, boolean b) {
    if (h == null && v == null) {
      return shortest(a, a, layers - 1, cache, layersLimit);
    } else if (h == null) {
      return shortest(a, v, layers - 1, cache, layersLimit)
              + (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(v, v, layers - 1, cache, layersLimit)
              + shortest(v, a, layers - 1, cache, layersLimit);
    } else if (v == null) {
      return shortest(a, h, layers - 1, cache, layersLimit)
              + (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(h, h, layers - 1, cache, layersLimit)
              + shortest(h, a, layers - 1, cache, layersLimit);
    }
    return Math.min(
      endPos.getX() == 0 && (b || startPos.getY() == 3) ? Long.MAX_VALUE : 
        shortest(a, h, layers - 1, cache, layersLimit) +
        (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(h, h, layers - 1, cache, layersLimit) +
        shortest(h, v, layers - 1, cache, layersLimit) +
        (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(v, v, layers - 1, cache, layersLimit) +
        shortest(v, a, layers - 1, cache, layersLimit),
      startPos.getX() == 0 && (b || endPos.getY() == 3) ? Long.MAX_VALUE : 
        shortest(a, v, layers - 1, cache, layersLimit) +
        (Math.abs(endPos.getY() - startPos.getY()) - 1) * shortest(v, v, layers - 1, cache, layersLimit) +
        shortest(v, h, layers - 1, cache, layersLimit) +
        (Math.abs(endPos.getX() - startPos.getX()) - 1) * shortest(h, h, layers - 1, cache, layersLimit) +
        shortest(h, a, layers - 1, cache, layersLimit)
    );
  }
}
