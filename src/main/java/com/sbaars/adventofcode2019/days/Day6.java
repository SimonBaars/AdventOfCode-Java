package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;
import com.sbaars.adventofcode2019.util.ListMap;

public class Day6 implements Day, DoesFileOperations
{	
    public static void main(String[] args) throws IOException
    {
    	new Day6().printParts();
    }

    @Override
	public int part1() throws IOException {
    	ListMap<String, String> orbits = new ListMap<>();
    	String[] nums = createNumberStream();
    	for(String num : nums) {
    		String[] parts = num.split("\\)");
    		orbits.addTo(parts[0], parts[1]);
    	}
    	AtomicInteger o = new AtomicInteger();
    	extracted(orbits, o);
		return o.get();
	}

	private void extracted(ListMap<String, String> orbits, AtomicInteger o) {
		for(Entry<String, List<String>> entry : orbits.entrySet()) {
    		extracted(orbits, o, entry.getValue());
    	}
	}

	private void extracted(ListMap<String, String> orbits, AtomicInteger o, List<String> entry) {
		for(String str : entry) {
			o.incrementAndGet();
			if(orbits.containsKey(str)) {
				extracted(orbits, o, orbits.get(str));
			}
		}
	}
	
    @Override
	public int part2() throws IOException {
		return 0;
	}

	private String[] createNumberStream() throws IOException {
		return Arrays.stream(getFileAsString(new File(Day6.class.getClassLoader().getResource("day6.txt").getFile())).split(System.lineSeparator())).toArray(String[]::new);
	}
}
