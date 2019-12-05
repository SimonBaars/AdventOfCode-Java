package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day1 implements Day, DoesFileOperations
{	
    public static void main(String[] args) throws IOException
    {
    	new Day1().printParts();
    }

    @Override
	public int part1() throws IOException {
		return createNumberStream().map(this::getFuel).sum();
	}
	
    @Override
	public int part2() throws IOException {
		return createNumberStream().map(this::getRequiredFuel).sum();
	}

	private IntStream createNumberStream() throws IOException {
		return Arrays.stream(getFileAsString(new File(Day1.class.getClassLoader().getResource("day1.txt").getFile())).split(System.lineSeparator())).mapToInt(Integer::parseInt);
	}
	
	private int getRequiredFuel(int mass) {
		int fuel = getFuel(mass);
		return fuel>0 ? fuel+getRequiredFuel(fuel) : 0;
	}

	private int getFuel(int mass) {
		return (mass/3)-2;
	}
}
