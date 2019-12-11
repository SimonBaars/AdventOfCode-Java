package com.sbaars.adventofcode2019.common;

import java.io.IOException;

import com.sbaars.adventofcode2019.util.DoesFileOperations;

public interface Day extends DoesFileOperations {
	public Object part1() throws IOException;
	public Object part2() throws IOException;
	
	public default void printParts() throws IOException {
		System.out.println("Part 1: "+part1());
		System.out.println("Part 2: "+part2());
	}
}
