package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1 extends Day2018 {
	public static void main(String[] args) throws IOException {
		new Day1().printParts();
	}

	public Day1(){super(1);}

	@Override
	public Object part1() throws IOException {
		return dayNumberStream().sum();
	}

	@Override
	public Object part2() throws IOException {
		Set<Long> encountered = new HashSet<>();
		long[] ns = dayNumbers();
		long acc = 0;
		outerloop: while(true) {
			for (long n : ns) {
				acc += n;
				if (!encountered.add(acc)) break outerloop;
			}
		}
		return acc;
	}
}
