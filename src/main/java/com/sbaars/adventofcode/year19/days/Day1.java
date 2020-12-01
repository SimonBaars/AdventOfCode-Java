package com.sbaars.adventofcode.year19.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;

public class Day1 extends Day2019 {
	public Day1(){
		super(1);
	}

	public static void main(String[] args) throws IOException
	{
		new Day1().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return dayIntStream().map(this::getFuel).sum();
	}

	@Override
	public Object part2() throws IOException {
		return dayIntStream().map(this::getRequiredFuel).sum();
	}

	private int getRequiredFuel(int mass) {
		int fuel = getFuel(mass);
		return fuel>0 ? fuel+getRequiredFuel(fuel) : 0;
	}

	private int getFuel(int mass) {
		return (mass/3)-2;
	}
}
