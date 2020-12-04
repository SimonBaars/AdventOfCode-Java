package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import java.io.IOException;

public class Day21 extends Day2019 {

	public static void main(String[] args)  {
		new Day21().printParts();
	}

	public Day21(){super(21);}

	@Override
	public Object part1()  {
		IntcodeComputer ic = new IntcodeComputer(21);
		// !(A && B && C) && D
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
	public Object part2()  {
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
