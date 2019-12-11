package com.sbaars.adventofcode2019.days;

import java.io.IOException;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day2 implements Day {

	public static void main(String[] args) throws IOException {
		new Day2().printParts();
	}
	
	@Override
	public Object part1() throws IOException {
		return execute(12,2);
	}
	
	@Override 
	public Object part2() throws IOException {
		return bruteForceFindingNumber(19690720, 99);
	}

	private int bruteForceFindingNumber(int number, int bound) throws IOException {
		for(int i = 0; i<bound;i++) {
			for(int j = 0; j<bound; j++) {
					if(execute(i, j) == number) {
						return 100 * i + j;
					}
				}
		}
		return -1;
	}

	private long execute(int x, int y) throws IOException {
		IntcodeComputer computer = new IntcodeComputer(2, x, y);
		computer.run();
		return computer.firstElement();
	}
}
