package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import java.io.IOException;

public class Day19 extends Day2019 {

	public static void main(String[] args) throws IOException {
		new Day19().printParts();
	}

	public Day19(){super(19);}

	@Override
	public Object part1() throws IOException {
		long res = 0;
		for(int x = 0; x<50;x++) 
			for(int y = 0; y<50;y++) 
				res+=new IntcodeComputer(19).run(x, y);
		return res;
	}

	@Override
	public Object part2() throws IOException {
		int x = 500, y = 0;
		while(true) {
			if(beam(x, y)) {
				if(beam(x-99, y+99)) return 10000 * (x - 99) + y;
				else x++;
			} else y++;
		}
	}

	private boolean beam(int x, int y) throws IOException {
		return new IntcodeComputer(19).run(x, y) == 1L;
	}
}
