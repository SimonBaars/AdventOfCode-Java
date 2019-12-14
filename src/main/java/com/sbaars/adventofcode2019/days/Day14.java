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
		Trade[] trades = Arrays.stream(readDay(15).split(System.lineSeparator())).map(Trade::new).toArray(Trade[]::new);
		int cost = findCost(trades, new Item(1, "FUEL"), new CountMap<>());
		System.out.println(created);
		return cost;
		//return Arrays.stream(trades).map(e -> e.output.item).distinct().count();
		/*CountMap<String> items = new CountMap<>();
		for(int i = 13311; i<15000; i++) {
			System.out.println("We now have "+i+" ORE");
			items.put("ORE", i);
			if(i == 14000) {
				System.out.println("Hi");
			}
			if(canMakeFuel(trades, items)) {
				return i;
			}
		}
		return 0;*/
	}

	private Trade getTrade(Trade[] trades, String key) {
		return Arrays.stream(trades).filter(e -> e.output.item.equals(key)).findAny().get();
	}
	
	CountMap<String> created = new CountMap<>();
	private int findCost(Trade[] trades, Item buyingItem, CountMap<String> leftOver) {
		Trade fuelTrade = getTrade(trades, buyingItem.item);
		int timesApplied = (int)Math.ceil((double)buyingItem.amount/(double)fuelTrade.output.amount);
		leftOver.increment(fuelTrade.output.item, buyingItem.amount % fuelTrade.output.amount);
		
		//System.out.println(fuelTrade.output.item+" nLeftOver "+leftOver.get(fuelTrade.output.item));
		int totalCost = 0;
		for(int i = 0; i<timesApplied; i++) {			
			for(Item cost : fuelTrade.input) {
				if(leftOver.get(cost.item) >= cost.amount) {
					leftOver.increment(fuelTrade.output.item, -fuelTrade.output.amount);
					//skip = fuelTrade.output.item;
					System.out.println("Enough "+fuelTrade.output.item+" LEFTOVER!");
					continue;
				}
				if(cost.item.equals("ORE")) {
					totalCost+=cost.amount;
					System.out.println("Spend "+cost.amount+" ORE to get "+fuelTrade.output.amount+" "+fuelTrade.output.item);
				} else {
					totalCost+=findCost(trades, new Item(cost.amount, cost.item), leftOver);
				}
			}
			created.increment(buyingItem.item, fuelTrade.output.amount);
		}
		System.out.println("Bought "+(timesApplied*fuelTrade.output.amount)+" "+buyingItem.item+" for "+totalCost);
		//System.out.println(fuelTrade.output.item+" costs "+totalCost+" times "+timesApplied);
		return totalCost;
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
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
