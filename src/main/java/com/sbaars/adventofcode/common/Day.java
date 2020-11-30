package com.sbaars.adventofcode.common;

import java.io.IOException;

public interface Day extends DoesFileOperations {
	Object part1() throws IOException;
	Object part2() throws IOException;
	
	default void printParts() throws IOException {
		System.out.println("Part 1: "+part1());
		System.out.println("Part 2: "+part2());
	}
}
