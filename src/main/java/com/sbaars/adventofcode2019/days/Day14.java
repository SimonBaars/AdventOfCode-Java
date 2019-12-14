package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.CountMap;

public class Day14 implements Day {
	public static void main(String[] args) throws IOException {
		new Day14().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return findCosts();
	}

	private int findCosts() throws IOException {
		Trade[] trades = Arrays.stream(readDay(14).split(System.lineSeparator())).map(Trade::new).toArray(Trade[]::new);
		return findCost(trades, new Item(1, "FUEL"), new CountMap<>());
	}

	private Trade getTrade(Trade[] trades, String key) {
		return Arrays.stream(trades).filter(e -> e.output.item.equals(key)).findAny().get();
	}
	
	CountMap<String> created = new CountMap<>();
	private int findCost(Trade[] trades, Item buyingItem, CountMap<String> leftOver) {
		if(buyingItem.item.equals("ORE"))
			return buyingItem.amount;
		else if(buyingItem.amount <= leftOver.get(buyingItem.item)) {
			leftOver.increment(buyingItem.item, -buyingItem.amount);
			return 0;
		}
		buyingItem.amount-=leftOver.get(buyingItem.item);
		leftOver.put(buyingItem.item, 0);
		
		return performTrade(trades, buyingItem, leftOver);
	}

	private int performTrade(Trade[] trades, Item buyingItem, CountMap<String> leftOver) {
		Trade fuelTrade = getTrade(trades, buyingItem.item);
		int timesApplied = (int)Math.ceil((double)buyingItem.amount/(double)fuelTrade.output.amount);
		int totalCost = 0;
		for(Item cost : fuelTrade.input)
			totalCost+=findCost(trades, new Item(cost.amount*timesApplied, cost.item), leftOver);
		leftOver.increment(buyingItem.item, fuelTrade.output.amount * timesApplied - buyingItem.amount);
		return totalCost;
	}
	
	@Override
	public Object part2() throws IOException {
		Trade[] trades = Arrays.stream(readDay(15).split(System.lineSeparator())).map(Trade::new).toArray(Trade[]::new);
		CountMap<String> leftOver = new CountMap<>();
		int fuel = 0;
		
		for(long ore = 1000000000000L, amount = ore / findCosts(); ore>0 && amount>0; ) {
			CountMap<String> newLeftOver = new CountMap<>(leftOver);
			int cost = findCost(trades, new Item(1, "FUEL"), newLeftOver);
			if(cost > ore) {
				amount /= 2;	
			} else {
				fuel += amount;
				ore -= cost;
				leftOver = newLeftOver;
			}
		}
		return fuel;
	}
	
	class Trade {
		final Item[] input;
		final Item output;
		
		public Trade(String trade) {
			String[] inputOutput = trade.split(" => ");
			input = Arrays.stream(inputOutput[0].split(", ")).map(Item::new).toArray(Item[]::new);
			output = new Item(inputOutput[1]);
		}

		@Override
		public String toString() {
			return "Trade [input=" + Arrays.toString(input) + ", output=" + output + "]";
		}
	}
	
	class Item {
		int amount;
		String item;
		
		public Item(String item) {
			String[] i = item.split(" ");
			amount = Integer.parseInt(i[0]);
			this.item = i[1];
		}

		public Item(int i, String string) {
			amount = i;
			item = string;
		}

		@Override
		public String toString() {
			return "Item [amount=" + amount + ", item=" + item + "]";
		}
	}
}
