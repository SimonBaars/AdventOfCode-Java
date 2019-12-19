package com.sbaars.adventofcode2019.days;

import java.io.IOException;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day19 implements Day {

	public static void main(String[] args) throws IOException {
		new Day19().printParts();
	}

	@Override
	public Object part1() throws IOException {
		long res = 0;
		for(int x = 0; x<50;x++) {
			for(int y = 0; y<50;y++) {
				res+=new IntcodeComputer(19).run(x, y);
			}
		}
		return res;
	}
	
	@Override
	public Object part2() throws IOException {
		int i = 600, j = 0, start = 0, countWidth = 0;
		boolean atStart = true;
		while(true) {
			long n = new IntcodeComputer(19, 1).run(i, j);
			if(n == 1) {
				if(atStart) {
					start = j;
				}
				j++;
				countWidth++;
				atStart = false;
			} else if(atStart) {
				j++;
			} else {
				if(countWidth >= 100) {
					int y = j-100;
					int x = i;
					while(new IntcodeComputer(19, 1).run(x, y) == 1) x++;
					if((x-i)>=100)
						return ((x-100)*10000)+y;
				}
				i++;
				j = start;
				countWidth = 0;
				atStart = true;
			}
		}
	}
}
