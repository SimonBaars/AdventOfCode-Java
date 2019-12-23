package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;
import com.sbaars.adventofcode2019.intcode.RetentionPolicy;

public class Day23 implements Day {

	public static void main(String[] args) throws IOException {
		new Day23().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return getNetworkNumber(true);
	}
	
	@Override
	public Object part2() throws IOException {
		return getNetworkNumber(false);
	}

	private long getNetworkNumber(boolean returnNatY) throws IOException {
		long[] program = readLongArray(23);
		IntcodeComputer[] ic = IntStream.range(0, 50).mapToObj(i -> new IntcodeComputer(RetentionPolicy.EXIT_ON_EMPTY_INPUT, program, i, -1)).toArray(IntcodeComputer[]::new);
		long[] nat = new long[2];
		long sentByNat = -1;
		
		long input;
		while(true) {
			boolean idle = true;
			for(int i = 0; i<ic.length; i++) {
				if((input = ic[i].run())!=IntcodeComputer.STOP_CODE) {
					int pc = Math.toIntExact(input);
					long x = ic[i].run(), y = ic[i].run();
					if(pc == 255) {
						if(returnNatY) return y;
						nat[0] = x;
						nat[1] = y;
					} else ic[pc].addInput(x, y);
					idle = false;
				}
			}
			if(idle) {
				ic[0].addInput(nat[0], nat[1]);
				if(sentByNat == nat[1]) return nat[1];
				sentByNat = nat[1];
			}
		}
	}
}
