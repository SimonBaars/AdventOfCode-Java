package com.sbaars.adventofcode.year19.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode.common.Day;

public class Day1 implements Day
{	
	public static void main(String[] args) throws IOException
	{
		new Day1().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return createNumberStream().map(this::getFuel).sum();
	}

	@Override
	public Object part2() throws IOException {
		return createNumberStream().map(this::getRequiredFuel).sum();
	}

	private IntStream createNumberStream() throws IOException {
		return Arrays.stream(readDay(1).split(System.lineSeparator())).mapToInt(Integer::parseInt);
	}

	private int getRequiredFuel(int mass) {
		int fuel = getFuel(mass);
		return fuel>0 ? fuel+getRequiredFuel(fuel) : 0;
	}

	private int getFuel(int mass) {
		return (mass/3)-2;
	}
}
