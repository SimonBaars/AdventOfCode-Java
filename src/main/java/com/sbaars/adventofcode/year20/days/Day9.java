package com.sbaars.adventofcode.year20.days;

import static com.google.common.primitives.Longs.asList;
import static java.lang.Math.toIntExact;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.IntStream.range;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day9 extends Day2020 {
	public Day9()  {
		super(9);
	}

	public static void main(String[] args)  {
		new Day9().printParts();
	}

	@Override
	public Object part1()  {
		long[] input = dayNumbers();
		for(int k = 0; k<input.length-25; k++) {
			Set<Long> sums = new HashSet<>();
			for (int i = k; i < k+25; i++) {
				for (int j = i + 1; j < k+25; j++) {
					sums.add(input[i] + input[j]);
				}
			}
			if (!sums.contains(input[k+25])) {
				return input[k+25];
			}
		}
		return 0;
	}

	@Override
	public Object part2()  {
		long[] input = dayNumbers();
		long part1Solution = (long)part1();
		for(int i = 2; i<input.length; i++){
			for(int j = 0; j<=input.length-i; j++){
				if(stream(input, j, j + i).sum() == part1Solution){
					long[] window = copyOfRange(input, j, j+i+1);
					return stream(window).max().getAsLong() + stream(window).min().getAsLong();
				}
			}
		}
		return 0;
	}
}
