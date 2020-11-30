package com.sbaars.adventofcode.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface ProcessesImages {
	public default String printAsciiArray(int[][] pixels) {
		return System.lineSeparator()+Arrays.stream(pixels).map(a -> Arrays.stream(a).boxed().map(x -> x == 0 ? " " : "█").collect(Collectors.joining())).collect(Collectors.joining(System.lineSeparator()));
	}
}
