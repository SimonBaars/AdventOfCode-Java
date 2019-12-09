package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day7 implements Day, DoesFileOperations {

	public static void main(String[] args) throws IOException {
		new Day7().printParts();
	}

	@Override
	public int part1() throws IOException {
		List<List<Integer>> permutations = generatePerm(new ArrayList<>(Arrays.asList(0,1,2,3,4)));
		List<Integer> results = new ArrayList<>();
		for(List<Integer> perm : permutations) {
			int lastVal = 0;
			for(Integer i : perm)
				lastVal = new IntcodeComputer(7, i, lastVal).runInt();
			results.add(lastVal);

		}
		return results.stream().mapToInt(e -> e).max().getAsInt();
	}

	@Override
	public int part2() throws IOException {
		List<List<Integer>> permutations = generatePerm(new ArrayList<>(Arrays.asList(5,6,7,8,9)));
		List<Integer> results = new ArrayList<>();
		perms: for(List<Integer> shuffle : permutations) {
			IntcodeComputer[] computers = new IntcodeComputer[5];
			for(int i = 0; i<computers.length; i++) computers[i] = new IntcodeComputer(7, shuffle.get(i));
			int lastVal = 0;
			while(true) {
				for(IntcodeComputer c : computers) {
					c.addInput(lastVal);
					int thruster = lastVal;
					lastVal = c.runInt();
					if(lastVal == -1) {
						results.add(thruster);
						continue perms;
					}
				}
			}

		}
		return results.stream().mapToInt(e -> e).max().getAsInt();
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
