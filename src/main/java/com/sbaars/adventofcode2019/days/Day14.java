package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.LongCountMap;

public class Day14 implements Day {
	private Trade[] trades;
	
	public Day14() throws IOException {
		this.trades = Arrays.stream(readDay(14).split(System.lineSeparator())).map(Trade::new).toArray(Trade[]::new);
	}
	
	public static void main(String[] args) throws IOException {
		new Day14().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return findCost(new Item(1, "FUEL"), new LongCountMap<>());
	}

	private Trade getTrade(String key) {
		return Arrays.stream(trades).filter(e -> e.output.item.equals(key)).findAny().get();
	}

	private long findCost(Item buyingItem, LongCountMap<String> leftOver) {
		if(buyingItem.item.equals("ORE"))
			return buyingItem.amount;
		else if(buyingItem.amount <= leftOver.get(buyingItem.item)) {
			leftOver.increment(buyingItem.item, -buyingItem.amount);
			return 0;
		}
		buyingItem.amount-=leftOver.get(buyingItem.item);
		leftOver.put(buyingItem.item, 0L);
		
		return performTrade(buyingItem, leftOver);
	}

	private long performTrade(Item buyingItem, LongCountMap<String> leftOver) {
		Trade fuelTrade = getTrade(buyingItem.item);
		long timesApplied = (long)Math.ceil((double)buyingItem.amount/(double)fuelTrade.output.amount);
		long totalCost = 0;
		for(Item cost : fuelTrade.input)
			totalCost+=findCost(new Item(cost.amount*timesApplied, cost.item), leftOver);
		leftOver.increment(buyingItem.item, fuelTrade.output.amount * timesApplied - buyingItem.amount);
		return totalCost;
	}
	
	@Override
	public Object part2() throws IOException {
		long oreLeft = 1000000000000L;
		long fuel = 1;
		while(true) {
			long cost = findCost(new Item(fuel + 1, "FUEL"), new LongCountMap<>());
			if (cost > oreLeft) {
		        return fuel;
		    } else {
		        fuel = Math.max(fuel + 1, (fuel + 1) * oreLeft / cost);
		    }
		}
	}
	
	class Trade {
		private final Item[] input;
		private final Item output;
		
		public Trade(String trade) {
			String[] inputOutput = trade.split(" => ");
			input = Arrays.stream(inputOutput[0].split(", ")).map(Item::new).toArray(Item[]::new);
			output = new Item(inputOutput[1]);
		}
	}
	
	class Item {
		private long amount;
		private final String item;
		
		public Item(String item) {
			String[] i = item.split(" ");
			amount = Integer.parseInt(i[0]);
			this.item = i[1];
		}

		public Item(long i, String string) {
			amount = i;
			item = string;
		}
	}
}
