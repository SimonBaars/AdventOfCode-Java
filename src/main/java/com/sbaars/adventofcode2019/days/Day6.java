package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;
import com.sbaars.adventofcode2019.util.ListMap;

public class Day6 implements Day, DoesFileOperations
{	
	
	ListMap<String, String> orbits = new ListMap<>();
	
    public static void main(String[] args) throws IOException
    {
    	new Day6().printParts();
    }

    @Override
	public int part1() throws IOException {
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
    	
		return goFrom("YOU", "SAN");
	}
    
    public int goFrom(String s1, String s2) {
    	AtomicInteger steps = new AtomicInteger();
    	step(s1, 0);
    	return 0;
    }
    
    List<String> been = new ArrayList<>();
    
    private void step(String s1, int depth) {
    	if(been.contains(s1))
    		return;
    	been.add(s1);
    	List<String> str = collectAll(s1);
    	//System.out.println(Arrays.toString(str.toArray()));
    	if(str.contains("SAN")) {
    		System.out.println(depth+1);
    		//System.exit(1);
    	}
    	//steps.incrementAndGet();
    	for(String s : str) {
    		step(s, depth + 1);
    	}
	}

	private List<String> collectAll(String s1) {
    	List<String> s = findOrbit(s1);
    	s.addAll(orbits.get(s1));
		return s;
	}

	public List<String> findOrbit(String orbitValue) {
		List<String> orbit = new ArrayList<>();
    	for(Entry<String, List<String>> entry : orbits.entrySet()) {
    		if(entry.getValue().contains(orbitValue)) {
    			orbit.add(entry.getKey());
    		}
    	}
    	return orbit;
    }

	private String[] createNumberStream() throws IOException {
		return Arrays.stream(getFileAsString(new File(Day6.class.getClassLoader().getResource("day6.txt").getFile())).split(System.lineSeparator())).toArray(String[]::new);
	}
}
