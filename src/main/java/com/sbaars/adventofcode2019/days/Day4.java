package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;

public class Day4 implements Day {

	public Day4() {}
	
	public static void main(String[] args) throws IOException {
		new Day4().printParts();
	}

	@Override
	public int part1() throws IOException {
		return checkPasswords(false);
	}
	
	@Override
	public int part2() throws IOException {
		return checkPasswords(true);
	}
	
	public long meetsCriteria(int lowerBound, int higherBound, boolean checkGroup) {
		return IntStream.range(lowerBound, higherBound+1).filter(e -> meetsCriteria(e, checkGroup)).count();
	}

	public boolean meetsCriteria(int input, boolean checkGroup)
	{	
		int lastSeen = 10, current, adjacentTheSame = -2, skip = 10;

	    for (int i = 0; input > 0; i++) {
	        current = input % 10;
	        if(skip != current && current == lastSeen) {
		        if(checkGroup && adjacentTheSame+1 == i) {
		        	adjacentTheSame = -2;
		        	skip = current;
		        } else if(adjacentTheSame == -2) {
		        	adjacentTheSame = i;
		        }
		    }
	        if (lastSeen < current)
	            return false;
	        lastSeen = current;
	        input /= 10;
	    }
	    
	    return adjacentTheSame!=-2;
	}

	private int checkPasswords(boolean checkGroup) {
		return Math.toIntExact(meetsCriteria(372037, 905157, checkGroup));
	}

}
