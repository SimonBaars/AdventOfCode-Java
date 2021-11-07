package com.sbaars.adventofcode.year20.days;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public class Day7 extends Day2020 {

  private final Trade[] trades;

  public Day7() {
    super(7);
    this.trades = dayStream().filter(s -> !s.contains("no other bags")).map(Trade::new).toArray(Trade[]::new);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    return findBagTypes(new Item(1, "shiny gold"), new HashSet<>()).size() - 1;
  }

  @Override
  public Object part2() {
    return findNumberOfBags(new LinkedList<>(singletonList(new Item(1, "shiny gold")))) - 1;
  }

  private Trade[] getTrades(Item i) {
    return stream(trades).filter(e -> stream(e.output).anyMatch(t -> t.item.equals(i.item) && t.amount >= i.amount)).toArray(Trade[]::new);
  }

  private Optional<Trade> getTrade(Item i) {
    return stream(trades).filter(e -> e.input.item.equals(i.item)).findAny();
  }

  private Set<String> findBagTypes(Item buyingItem, Set<String> visitedColors) {
    visitedColors.add(buyingItem.item);
    Trade[] possibleTrades = getTrades(buyingItem);
    stream(possibleTrades).forEach(t -> findBagTypes(t.input, visitedColors));
    return visitedColors;
  }

  private long findNumberOfBags(Deque<Item> leftOver) {
    long total = 0;
    while (!leftOver.isEmpty()) total += findBagsInside(leftOver, leftOver.pop());
    return total;
  }

  private long findBagsInside(Deque<Item> leftOver, Item buyingItem) {
    Optional<Trade> fuelTrade = getTrade(buyingItem);
    fuelTrade.ifPresent(trade -> stream(trade.output).flatMap(i -> IntStream.rangeClosed(1, toIntExact(i.amount)).mapToObj(e -> new Item(1, i.item))).forEach(leftOver::add));
    return buyingItem.amount;
  }

  public static class Trade {
    public final Item input;
    public final Item[] output;

    public Trade(String trade) {
      String[] inputOutput = trade.split(" contain ");
      output = stream(inputOutput[1].split(", ")).map(Item::new).toArray(Item[]::new);
      input = new Item(1, inputOutput[0].replace(" bags", ""));
    }
  }

  public static class Item {
    public final String item;
    public long amount;

    public Item(String item) {
      amount = Integer.parseInt(item.substring(0, 1));
      this.item = item.substring(2).replaceAll(" bags?\\.?", "");
    }

    public Item(long i, String string) {
      amount = i;
      item = string;
    }
  }
}
