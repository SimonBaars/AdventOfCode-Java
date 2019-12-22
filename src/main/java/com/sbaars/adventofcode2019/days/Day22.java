package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

public class Day22 implements Day {
	
	Move[] moves;

	public Day22() throws IOException {
		this.moves = Arrays.stream(readDay(22).split(System.lineSeparator())).map(Move::new).toArray(Move[]::new);
	}
	
	public static void main(String[] args) throws IOException {
		new Day22().printParts();
	}
	
	@AllArgsConstructor enum Action{
		DEAL_WITH_INCREMENT("deal with increment "),
		DEAL_NEW_STACK("deal into new stack"),
		CUT("cut ");
		
		String name;
		
		public static Action actionByText(String text) {
			return Arrays.stream(values()).filter(a -> text.startsWith(a.name)).findAny().get();
		}
	}
	
	@Value @Data class Move {
		Action action;
		int amount;
		
		public Move(String s) {
			this.action = Action.actionByText(s);
			s = s.replace(action.name, "");
			if(!s.isEmpty()) amount = Integer.parseInt(s);
			else amount = 0;
		}

		public List<Integer> execute(List<Integer> cards) {
			switch(action) {
				case DEAL_NEW_STACK: Collections.reverse(cards); break;
				case CUT: {
					int n = amount > 0 ? amount : cards.size()+amount;
					List<Integer> sub = new ArrayList<>(cards.subList(n, cards.size()));
					sub.addAll(cards.subList(0, n));
					return sub;
				} 
				case DEAL_WITH_INCREMENT: {
					Integer[] deck = new Integer[cards.size()] ;
					for(int i = 0, card = 0; i<cards.size();i++) {
						deck[card] = cards.get(i);
						card = (card + amount) % deck.length;
					}
					return Arrays.asList(deck);
				}
			}
			return cards;
		}
		
		public long[] execute(long[] input, long deckSize) {
			switch(action) {
				case DEAL_NEW_STACK: return new long[] {-input[0], -(input[1]+1)};
				case CUT: input[1] += amount; break;
				case DEAL_WITH_INCREMENT: {
					long p = pow(amount, deckSize-2, deckSize);
					return new long[] {input[0]*p, input[1]*p};
				}
			}
			return input;
		}
	}
	
	private long pow(long a, long b, long c) {
		return new BigInteger(Long.toString(a)).modPow(new BigInteger(Long.toString(b)), new BigInteger(Long.toString(c))).longValue();
	}

	@Override
	public Object part1() throws IOException {
		List<Integer> cards = IntStream.range(0, 10007).boxed().collect(Collectors.toList());
		for(Move move : moves) cards = move.execute(cards);
		return cards.indexOf(2019);
	}
	
	@Override
	public Object part2() throws IOException {
		long deckSize = 119315717514047L;
		long timesShuffled = 101741582076661L;
		long[] calc = new long[] {1, 0};
		System.out.println("Max long "+Long.MAX_VALUE);
		for(Move move : reverseArray(moves)) {
			System.out.println(move);
			calc = move.execute(calc, deckSize);
			//System.out.println("Before mod: "+Arrays.toString(calc));
			for(int i = 0; i<calc.length; i++) calc[i] = Math.floorMod(calc[i], deckSize);
			//calc[0] %= deckSize;
			//calc[1] %= deckSize;
			System.out.println("after mod: "+Arrays.toString(calc));
		}
		long pow = pow(calc[0], timesShuffled, deckSize);
		return Math.floorMod(pow * 2020 + calc[1] * (pow + deckSize - 1) * pow(calc[0] - 1, deckSize - 2, deckSize), deckSize);
	}
	
	private <T> T[] reverseArray(T[] arr) {
		for(int i = 0; i < arr.length / 2; i++) {
		    T temp = arr[i];
		    arr[i] = arr[arr.length - i - 1];
		    arr[arr.length - i - 1] = temp;
		}
		return arr;
	}
}
