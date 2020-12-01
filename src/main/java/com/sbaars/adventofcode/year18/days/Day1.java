package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;

import java.io.IOException;

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
		return "";
	}
}
