package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year15.Day2015;

import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day6 extends Day2015 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  public record Input(String onOff, long x1, long y1, long x2, long y2) {
    private Range getRange() {
      return new Range(x1, y1, x2, y2);
    }

    private boolean isToggle() {
      return onOff.equals("toggle");
    }

    private boolean isOn() {
      return onOff.equals("on");
    }
  }

  @Override
  public Object part1() {
    InfiniteGrid g = new InfiniteGrid();
    inputStream().forEach(action -> action.getRange().stream().forEach(l -> {
      if((action.isToggle() && g.contains(l)) || (!action.isToggle() && !action.isOn())){
        g.grid.remove(l);
      } else {
        g.set(l, '#');
      }
    }));
    return g.grid.size();
  }

  @Override
  public Object part2() {
    InfiniteGrid g = new InfiniteGrid();
    inputStream().forEach(action -> {
      action.getRange().stream().forEach(l -> {
        int add = action.isToggle() ? 2 : action.isOn() ? 1 : -1;
        if(g.contains(l)) {
          g.set(l, (char) Math.max(g.grid.get(l) + add, 0));
        } else if(action.isOn() || action.isToggle()){
          g.set(l, (char)add);
        }
      });
    });
    return g.grid.values().stream().mapToInt(c -> (int)c).sum();
  }

  private Stream<Input> inputStream() {
    return dayStream().map(s -> {
      try {
        return readString(s, "%s %n,%n through %n,%n", Input.class);
      } catch (Exception e) {
        return readString(s, "turn %s %n,%n through %n,%n", Input.class);
      }
    });
  }
}
