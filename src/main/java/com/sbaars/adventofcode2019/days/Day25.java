package com.sbaars.adventofcode2019.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;
import com.sbaars.adventofcode2019.intcode.RetentionPolicy;

public class Day25 implements Day {

	public static void main(String[] args) throws IOException {
		new Day25().printParts();
	}

	@Override
	public Object part1() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(RetentionPolicy.EXIT_ON_EMPTY_INPUT, readLongArray(25));
		while(true) {
			long res;
			while((res = ic.run()) != IntcodeComputer.STOP_CODE) {
				System.out.print((char)res);
			}
			BufferedReader reader =
	                new BufferedReader(new InputStreamReader(System.in));
			String name = reader.readLine();
			ic.setInput(name+"\n");
		}
	}
	
	@Override
	public Object part2() throws IOException {
		return new IntcodeComputer(9, 2).run();
	}
}
