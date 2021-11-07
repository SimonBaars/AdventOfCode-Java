package com.sbaars.adventofcode.haskell.year20.days;

import static java.util.Arrays.stream;

import com.sbaars.adventofcode.haskell.year20.HaskellDay2020;
import com.sbaars.adventofcode.year20.days.Day7.Item;
import com.sbaars.adventofcode.year20.days.Day7.Trade;

public class Day7 extends HaskellDay2020 {
  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    return dayStream().filter(s -> !s.contains("no other bags")).map(Trade::new)
        .map(t -> tuple(convert(t.input.item), stream(t.output).map(this::convertItem).collect(haskellList())))
        .collect(haskellList());
  }

  private String convertItem(Item i) {
    return tup(i.amount, i.item);
  }

  @Override
  public Object part2() {
    return null;
  }
}
