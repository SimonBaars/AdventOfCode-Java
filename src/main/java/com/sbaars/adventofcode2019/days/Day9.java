package com.sbaars.adventofcode2019.days;

import java.io.IOException;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day9 implements Day {

	public static void main(String[] args) throws IOException {
		new Day9().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return new IntcodeComputer(9, 1).run();
	}
	
	@Override
	public Object part2() throws IOException {
		return new IntcodeComputer(9, 2).run();
	}
}
