package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.CircularList;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;

public class Day20 extends Day2022 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  @Override
  public Object part1() {
    CircularList in = new CircularList(dayNumberStream().toArray());
    var nodes = new ArrayList<>(in.valueMap);
    for(int i = 0; i<nodes.size(); i++) {
      var toMove = nodes.get(i);
      var moveTo = toMove.move(toMove.value, nodes.size());
      in.insertAfter(toMove, moveTo);
    }
    in.setCurrent(nodes.stream().filter(n -> n.value == 0).findAny().get());
    long[] nums = in.next(3000);
    return nums[1000-1] + nums[2000-1] + nums[3000-1];
  }

  @Override
  public Object part2() {
    CircularList in = new CircularList(dayNumberStream().map(l -> l * 811589153L).toArray());
    var nodes = new ArrayList<>(in.valueMap);
    for(int j = 0; j<10; j++) {
      for (int i = 0; i < nodes.size(); i++) {
        var toMove = nodes.get(i);
        var moveTo = toMove.move(toMove.value, nodes.size());
        in.insertAfter(toMove, moveTo);
      }
    }
    in.setCurrent(nodes.stream().filter(n -> n.value == 0).findAny().get());
    long[] nums = in.next(3000);
    return nums[1000-1] + nums[2000-1] + nums[3000-1];
  }
}
