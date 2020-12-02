package com.sbaars.adventofcode.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class Day {
	protected final int year;
	protected final int day;
	private static final String DEFAULT_DELIMITER = System.lineSeparator();

	public Day(int year, int day){
		this.year = year;
		this.day = day;
	}

	public abstract Object part1() throws IOException;
	public abstract Object part2() throws IOException;
	
	public void printParts() throws IOException {
		System.out.println("Part 1: "+part1());
		System.out.println("Part 2: "+part2());
	}

	protected String getResourceAsString(String resource) {
		try {
			return readFileToString(new File(DoesFileOperations.class.getClassLoader().getResource(resource).getFile()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String day() {
		return getResourceAsString(year+"/day"+day+".txt");
	}

	protected String[] dayStrings() {
		return dayStrings(DEFAULT_DELIMITER);
	}

	protected String[] dayStrings(String delimiter) {
		return Arrays.stream(day().split(delimiter)).toArray(String[]::new);
	}

	protected Stream<String> dayStream() {
		return dayStream(DEFAULT_DELIMITER);
	}

	protected Stream<String> dayStream(String delimiter) {
		return Arrays.stream(day().split(delimiter));
	}

	protected IntStream dayIntStream() {
		return dayIntStream(DEFAULT_DELIMITER);
	}

	protected IntStream dayIntStream(String delimiter) {
		return Arrays.stream(day().split(delimiter)).mapToInt(Integer::parseInt);
	}

	protected long[] dayNumbers() {
		return dayNumbers(DEFAULT_DELIMITER);
	}

	protected long[] dayNumbers(String delimiter) {
		return dayNumberStream(delimiter).toArray();
	}

	protected LongStream dayNumberStream() {
		return dayNumberStream(DEFAULT_DELIMITER);
	}

	protected LongStream dayNumberStream(String delimiter) {
		return dayStream(delimiter).mapToLong(Long::parseLong);
	}

	protected char[][] dayGrid() {
		return dayGrid(DEFAULT_DELIMITER);
	}

	protected char[][] dayGrid(String delimiter) {
		return dayStream(delimiter).map(String::toCharArray).toArray(char[][]::new);
	}
}
