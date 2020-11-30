package com.sbaars.adventofcode.common;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public interface DoesFileOperations {
	
	default String getResourceAsString(String resource) throws IOException {
		return readFileToString(new File(DoesFileOperations.class.getClassLoader().getResource(resource).getFile()));
	}
	
	default String day2019(int day) throws IOException {
		return getResourceAsString("2019/day"+day+".txt");
	}

	default String day2018(int day) throws IOException {
		return getResourceAsString("2018/day"+day+".txt");
	}

	default String day(int day) throws IOException {
		return getResourceAsString("2020/day"+day+".txt");
	}

	default String[] dayStrings(int day) throws IOException {
		return Arrays.stream(day(day).split(System.lineSeparator())).toArray(String[]::new);
	}
	
	default long[] dayNumbers2019(int day) throws IOException {
		return Arrays.stream(day2019(day).split(",")).mapToLong(Long::parseLong).toArray();
	}

	default long[] dayNumbers(int day, String split) throws IOException {
		return Arrays.stream(day(day).split(split)).mapToLong(Long::parseLong).toArray();
	}

	default long[] dayNumbers2018(int day, String split) throws IOException {
		return Arrays.stream(day2018(day).split(split)).mapToLong(Long::parseLong).toArray();
	}
}