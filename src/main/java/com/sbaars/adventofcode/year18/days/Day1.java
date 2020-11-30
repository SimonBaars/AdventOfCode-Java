package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Day;
import java.io.IOException;
import java.util.stream.LongStream;

public class Day1 implements Day
{	
	public static void main(String[] args) throws IOException
	{
		new Day1().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return LongStream.of(dayNumbers2018(1, "\n")).sum();
	}

	@Override
	public Object part2() throws IOException {
		return "";
	}
}
