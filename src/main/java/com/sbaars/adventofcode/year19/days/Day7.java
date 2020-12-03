package com.sbaars.adventofcode.year19.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import lombok.var;

public class Day7 extends Day2019 {
	public Day7(){
		super(7);
	}

	public static void main(String[] args) throws IOException {
		new Day7().printParts();
	}

	@Override
	public Object part1() throws IOException {
		var permutations = generatePerm(new ArrayList<>(Arrays.asList(0,1,2,3,4)));
		List<Long> results = new ArrayList<>();
		for(List<Integer> perm : permutations) {
			long lastVal = 0;
			for(Integer i : perm)
				lastVal = new IntcodeComputer(7, i, lastVal).run();
			results.add(lastVal);

		}
		return results.stream().mapToLong(e -> e).max().getAsLong();
	}

	@Override
	public Object part2() throws IOException {
		var permutations = generatePerm(new ArrayList<>(Arrays.asList(5,6,7,8,9)));
		List<Long> results = new ArrayList<>();
		perms: for(List<Integer> shuffle : permutations) {
			IntcodeComputer[] computers = new IntcodeComputer[5];
			for(int i = 0; i<computers.length; i++) computers[i] = new IntcodeComputer(7, shuffle.get(i));
			long lastVal = 0;
			while(true) {
				for(IntcodeComputer c : computers) {
					c.addInput(lastVal);
					long thruster = lastVal;
					lastVal = c.run();
					if(lastVal == IntcodeComputer.STOP_CODE) {
						results.add(thruster);
						continue perms;
					}
				}
			}

		}
		return results.stream().mapToLong(e -> e).max().getAsLong();
	}

	public <E> List<List<E>> generatePerm(List<E> original) {
		if (original.isEmpty()) {
			List<List<E>> result = new ArrayList<>(); 
			result.add(new ArrayList<E>()); 
			return result; 
		}
		E firstElement = original.remove(0);
		List<List<E>> returnValue = new ArrayList<>();
		List<List<E>> permutations = generatePerm(original);
		for (List<E> smallerPermutated : permutations) {
			for (int index=0; index <= smallerPermutated.size(); index++) {
				List<E> temp = new ArrayList<>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}
}
