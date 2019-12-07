package com.sbaars.adventofcode2019.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public interface DoesFileOperations {

	public default String getFileAsString(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}
	
	public default String getResourceAsString(String resource) throws IOException {
		return getFileAsString(new File(DoesFileOperations.class.getClassLoader().getResource(resource).getFile()));
	}
	
	public default String readDay(int day) throws IOException {
		return getResourceAsString("day"+day+".txt");
	}
}