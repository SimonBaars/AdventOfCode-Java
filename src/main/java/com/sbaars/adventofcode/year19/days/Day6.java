package com.sbaars.adventofcode.year19.days;

import com.google.common.collect.ArrayListMultimap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.sbaars.adventofcode.common.Day;

public class Day6 implements Day {

	ArrayListMultimap<String, String> orbits = ArrayListMultimap.create();

	public static void main(String[] args) throws IOException
	{
		new Day6().printParts();
	}

	@Override
	public Object part1() throws IOException {
		String[] nums = createOrbitArray();
		for(String num : nums) {
			String[] parts = num.split("\\)");
			orbits.put(parts[0], parts[1]);
		}
		AtomicInteger o = new AtomicInteger();
		for(Entry<String, Collection<String>> entry : orbits.asMap().entrySet())
			countOrbitsInList(orbits, o, entry.getValue());
		return o.get();
	}

	private void countOrbitsInList(ArrayListMultimap<String, String> orbits, AtomicInteger o, Collection<String> entry) {
		for(String str : entry) {
			o.incrementAndGet();
			if(orbits.containsKey(str)) {
				countOrbitsInList(orbits, o, orbits.get(str));
			}
		}
	}

	@Override
	public Object part2() throws IOException {
		return findRoute("YOU", "SAN");
	}

	private int findRoute(String from, String to) {
		return findRoute(from, to, new ArrayList<>(), 0);
	}

	private int findRoute(String from, String to, List<String> visited, int depth) {
		if(visited.contains(from))
			return 0;
		visited.add(from);
		List<String> str = collectAll(from);
		if(str.contains(to))
			return depth-1;
		for(String s : str) {
			int findRoute = findRoute(s, to, visited, depth + 1);
			if(findRoute>0) return findRoute;
		}
		return -1;
	}

	private List<String> collectAll(String s1) {
		List<String> s = findOrbit(s1);
		s.addAll(orbits.get(s1));
		return s;
	}

	public List<String> findOrbit(String orbitValue) {
		return orbits.asMap().entrySet().stream().filter(e -> e.getValue().contains(orbitValue)).map(e -> e.getKey()).collect(Collectors.toList());
	}

	private String[] createOrbitArray() throws IOException {
		return Arrays.stream(day2019(6).split(System.lineSeparator())).toArray(String[]::new);
	}
}
