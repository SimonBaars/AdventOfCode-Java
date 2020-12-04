package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import java.io.IOException;

public class Day9 extends Day2019 {
	public Day9(){
		super(9);
	}

	public static void main(String[] args)  {
		new Day9().printParts();
	}

	@Override
	public Object part1()  {
		return new IntcodeComputer(9, 1).run();
	}

	@Override
	public Object part2()  {
		return new IntcodeComputer(9, 2).run();
	}
}
