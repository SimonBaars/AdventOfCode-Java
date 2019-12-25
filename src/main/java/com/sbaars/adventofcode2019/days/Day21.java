package com.sbaars.adventofcode2019.days;

import java.io.IOException;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day21 implements Day {

	public static void main(String[] args) throws IOException {
		new Day21().printParts();
	}

	@Override
	public Object part1() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(21);
		// !(A && B && C) || D
		ic.setInput("OR A T\n" + 
				"AND B T\n" + 
				"AND C T\n" + 
				"NOT T T\n" +  //flip T
				"AND D T\n" + 
				"OR T J\n" + 
				"WALK\n");
		long res;
		while((res = ic.run()) != IntcodeComputer.STOP_CODE)
			if(res>255) return res;
		return 0;
	}

	@Override
	public Object part2() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(21);
		// (((!B && H) || !A) || (!C && H)) && D
		ic.setInput("NOT A J\n" + 
				"NOT B T\n" + 
				"AND H T\n" + 
				"OR T J\n" + 
				"NOT C T\n" + 
				"AND H T\n" + 
				"OR T J\n" + 
				"AND D J\n" + 
				"RUN\n");
		long res;
		while((res = ic.run()) != IntcodeComputer.STOP_CODE)
			if(res>255) return res;
		return 0;
	}
}
