package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import lombok.var;

public class Day12 extends Day2019 {
	public Day12(){
		super(12);
	}

	int[][] moons = {{-5,6,-11},{-8,-4,-2},{1,16,4},{11,11,-4}};
	int[][] velocity = {{0,0,0},{0,0,0},{0,0,0},{0,0,0}};

	public static void main(String[] args)  {
		new Day12().printParts();
	}

	@Override
	public Object part1()  {
		for(int n = 0; n<1000; n++) {
			determineVelocity();
			moveMoonsUsingVelocity();
		}
		int[] res = new int[moons.length];
		for(int i = 0; i<res.length; i++) {
			res[i] = Arrays.stream(moons[i]).map(Math::abs).sum() * Arrays.stream(velocity[i]).map(Math::abs).sum();
		}
		return Arrays.stream(res).sum();
	}

	@Override
	public Object part2()  {
		var sets = Collections.nCopies(moons[0].length, new HashSet<>());
		long[] res = new long[sets.size()];
		for(long n = 0; true; n++) {
			determineVelocity();
			moveMoonsUsingVelocity();

			for(int i = 0; i<sets.size(); i++) {
				if(res[i] == 0 && !sets.get(i).add(Arrays.asList(moons[0][i], moons[1][i], moons[2][i], moons[3][i],velocity[0][i], velocity[1][i], velocity[2][i], velocity[3][i]))){
					res[i] = n;
					if(Arrays.stream(res).noneMatch(x -> x == 0)) {
						return lcm(res);
					}
				}
			}
		}
	}

	private void moveMoonsUsingVelocity() {
		for(int i = 0; i<moons.length; i++) {
			for(int j = 0; j<moons[0].length; j++) {
				moons[i][j]+=velocity[i][j];
			}
		}
	}

	private void determineVelocity() {
		for(int i = 0; i<moons.length; i++) {
			for(int j = i+1; j<moons.length; j++) {
				for(int dim = 0; dim<moons[0].length; dim++) {
					int moon1 = moons[i][dim], moon2 = moons[j][dim];
					if(moon1 < moon2) {
						velocity[i][dim]++;
						velocity[j][dim]--;
					} else if(moon1 > moon2) {
						velocity[i][dim]--;
						velocity[j][dim]++;
					}
				}
			}
		}
	}

	private static long gcd(long a, long b) {
		while (b > 0) {
			long temp = b;
			b = a % b; // % is remainder
			a = temp;
		}
		return a;
	}

	private static long lcm(long a, long b) {
		return a * (b / gcd(a, b));
	}

	private static long lcm(long[] input) {
		long result = input[0];
		for(int i = 1; i < input.length; i++) result = lcm(result, input[i]);
		return result;
	}


	short[][] copy(short[][] arr){
		short [][] myInt = new short[arr.length][];
		for(int i = 0; i < arr.length; i++)
			myInt[i] = arr[i].clone();
		return myInt;
	}
}
