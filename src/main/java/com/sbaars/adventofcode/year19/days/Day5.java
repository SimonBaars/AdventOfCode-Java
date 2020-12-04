package com.sbaars.adventofcode.year19.days;

import java.io.IOException;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;

public class Day5 extends Day2019 {
	public Day5(){
		super(5);
	}

	public static void main(String[] args)  {
		new Day5().printParts();
	}

	@Override
	public Object part1()  {
		long res;
		IntcodeComputer intcodeComputer = new IntcodeComputer(5, 1);
		while((res = intcodeComputer.run()) == 0);
		return res;
	}

	@Override
	public Object part2()  {
		return new IntcodeComputer(5, 5).run();
	}
}
