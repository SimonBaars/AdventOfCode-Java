package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.CountMap;

public class Day14 implements Day {
	public static void main(String[] args) throws IOException {
		new Day14().printParts();
	}

	@Override
	public Object part1() throws IOException {
		Trade[] trades = Arrays.stream(readDay(15).split(System.lineSeparator())).map(Trade::new).toArray(Trade[]::new);
		return findCost(trades, getTrade(trades, "FUEL"), 1);
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
	
	private int findCost(Trade[] trades, Trade fuelTrade, int nNeeded) {
		int timesApplied = (int)Math.ceil((double)nNeeded/(double)fuelTrade.output.amount);
		int totalCost = 0;
		for(Entry<String, Integer> cost : fuelTrade.input.entrySet()) {
			if(cost.getKey().equals("ORE")) {
				totalCost+=cost.getValue();
			} else {
				totalCost+=findCost(trades, getTrade(trades, cost.getKey()), cost.getValue());
			}
		}
		System.out.println(fuelTrade.output.item+" costs "+totalCost+" times "+timesApplied+" needed "+nNeeded);
		return totalCost * timesApplied;
	}

	private boolean canMakeFuel(Trade[] trades, CountMap<String> items) {
		//System.out.println(items);
		//System.out.println("----");
		//Trade fuelTrade = Arrays.stream(trades).filter(e -> e.output.item.equals("FUEL")).findAny().get();
		//findCost
		
		/*for(Trade trade : trades) {
			CountMap<String> newItems = new CountMap<>(items);
			if(trade.perform(newItems)) {
				if(trade.output.item.equals("FUEL")) {
					return true;
				}
				if(canMakeFuel(trades, newItems))
					return true;
			}
		}*/
		return false;
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
	}
	
	class Trade {
		CountMap<String> input = new CountMap<>();
		Item output;
		
		public Trade(String trade) {
			String[] inputOutput = trade.split(" => ");
			Arrays.stream(inputOutput[0].split(", ")).map(Item::new).forEach(e -> input.increment(e.item, e.amount));
			output = new Item(inputOutput[1]);
		}
		
		public boolean perform(CountMap<String> items) {
			for(Entry<String, Integer> item : input.entrySet()) {
				if(!items.containsKey(item.getKey()) || items.get(item.getKey()) < item.getValue())
					return false;
				else {
					items.increment(item.getKey(), -item.getValue());
				}
			}
			items.increment(output.item, output.amount);
			return true;
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
	}
}
