package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.CircularList;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.Arrays;

public class Day20 extends Day2022 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    Day20 d = new Day20();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
//    System.out.println(dayNumberStream().count());
//    System.out.println(dayNumberStream().distinct().count());
    CircularList in = new CircularList(dayNumberStream().mapToInt(Math::toIntExact).toArray());
    var nodes = new ArrayList<>(in.valueMap);
    for(int i = 0; i<nodes.size(); i++) {
      var toMove = nodes.get(i);
      if(toMove.value == 0) continue;
//      var moveTo = in.valueMap.get(Math.floorMod(in.valueMap.indexOf(toMove) + toMove.value, nodes.size()));
      var moveTo = toMove.move(toMove.value, nodes.size());
//      System.out.println(toMove.value+" moves after "+moveTo.value);
      in.insertAfter(toMove, moveTo);
      System.out.println(Arrays.toString(in.next(nodes.size())));
//      System.out.println(Arrays.toString(in.prev(nodes.size())));
    }
    while(in.current() != 0) {
      in.next();
    }
    int[] nums = in.next(3000);
    return nums[1000-1] + nums[2000-1] + nums[3000-1];
  }

  @Override
  public Object part2() {
    return "";
  }
}
