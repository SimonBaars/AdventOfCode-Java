package com.sbaars.adventofcode.year19.util;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;

public interface DoesFileOperations {
	
	default String getResourceAsString(String resource) throws IOException {
		return readFileToString(new File(DoesFileOperations.class.getClassLoader().getResource(resource).getFile()));
	}
	
	default String readDay(int day) throws IOException {
		return getResourceAsString("day"+day+".txt");
	}
	
	default long[] readLongArray(int day) throws IOException {
		return Arrays.stream(readDay(day).split(",")).mapToLong(Long::parseLong).toArray();
	}
}