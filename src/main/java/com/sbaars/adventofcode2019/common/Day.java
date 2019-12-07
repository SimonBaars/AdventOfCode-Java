package com.sbaars.adventofcode2019.common;

import java.io.IOException;

import com.sbaars.adventofcode2019.util.DoesFileOperations;

public interface Day extends DoesFileOperations {
	public int part1() throws IOException;
	public int part2() throws IOException;
	
	public default void printParts() throws IOException {
		System.out.println("Part 1 = "+part1());
		System.out.println("Part 2 = "+part2());
	}
}
