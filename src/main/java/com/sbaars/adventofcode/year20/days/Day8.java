package com.sbaars.adventofcode.year20.days;

import static java.util.Arrays.asList;

import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 extends Day2020 {

	private Trade[] trades;

	public Day8()  {
		super(7);
		this.trades = dayStream().filter(s -> !s.contains("no other bags")).map(Trade::new).toArray(Trade[]::new);
	}

	public static void main(String[] args)  {
		new Day8().printParts();
	}

	@Override
	public Object part1()  {
		return findCost(new Item(1, "shiny gold"), new HashSet<>()).size() - 1;
	}

	private Trade[] getTrades(Item i) {
		return Arrays.stream(trades).filter(e -> Arrays.stream(e.output).anyMatch(t -> t.item.equals(i.item) && t.amount >= i.amount)).toArray(Trade[]::new);
	}

	private Set<String> findCost(Item buyingItem, Set<String> visitedColors) {
		visitedColors.add(buyingItem.item);
		Trade[] trades = getTrades(buyingItem);
		Arrays.stream(trades).forEach(t -> findCost(t.input, visitedColors));
		return visitedColors;

//		if(buyingItem.item.equals("ORE"))
//			return buyingItem.amount;
//		else if(buyingItem.amount <= leftOver.get(buyingItem.item)) {
//			leftOver.increment(buyingItem.item, -buyingItem.amount);
//			return 0;
//		}
//		buyingItem.amount-=leftOver.get(buyingItem.item);
//		leftOver.put(buyingItem.item, 0L);
//
//		return performTrade(buyingItem, leftOver);
	}

	private long performTrade(Item buyingItem, LongCountMap<String> leftOver) {
//		Trade fuelTrade = getTrade(buyingItem.item);
//		long timesApplied = (long)Math.ceil((double)buyingItem.amount/(double)fuelTrade.output.amount);
//		long totalCost = 0;
//		totalCost+=findCost(new Item(fuelTrade.input.amount*timesApplied, fuelTrade.input.item), leftOver);
//		for(Item cost : fuelTrade.output)
//			leftOver.increment(buyingItem.item, cost.amount * timesApplied - buyingItem.amount);
//		return totalCost;
		return 0;
	}

	@Override
	public Object part2()  {
//		long oreLeft = 1000000000000L;
//		long fuel = 1;
//		while(true) {
//			long cost = findCost(new Item(fuel + 1, "FUEL"), new LongCountMap<>());
//			if (cost > oreLeft) {
//				return fuel;
//			} else {
//				fuel = Math.max(fuel + 1, (fuel + 1) * oreLeft / cost);
//			}
//		}
		return 0;
	}

	class Trade {
		private final Item input;
		private final Item[] output;

		public Trade(String trade) {
			String[] inputOutput = trade.split(" contain ");
			output = Arrays.stream(inputOutput[1].split(", ")).map(Item::new).toArray(Item[]::new);
			input = new Item(1, inputOutput[0].replace(" bags", ""));
		}
	}

	class Item {
		private long amount;
		private final String item;

		public Item(String item) {
			amount = Integer.parseInt(item.substring(0,1));
			this.item = item.substring(2).replaceAll(" bag(s?)(.?)", "");
		}

		public Item(long i, String string) {
			amount = i;
			item = string;
		}
	}
}
