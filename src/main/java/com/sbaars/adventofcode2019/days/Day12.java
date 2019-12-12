package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;

public class Day12 implements Day {

	int[][] moons = {{-5,6,-11},{-8,-4,-2},{1,16,4},{11,11,-4}};
	//int[][] moons = {{-8,-10,0},{5,5,10},{2,-7,3},{9,-8,-3}};
	//int[][] moons = {{-1,0,2},{2,-10,-7},{4,-8,8},{3,5,-1}};
	int[][] velocity = {{0,0,0},{0,0,0},{0,0,0},{0,0,0}};

	public static void main(String[] args) throws IOException {
		new Day12().printParts();
	}

	@Override
	public Object part1() throws IOException {
		for(int n = 0; n<1000; n++) {
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
			//System.out.println("Res = "+Arrays.deepToString(velocity));
			for(int i = 0; i<moons.length; i++) {
				for(int j = 0; j<moons[0].length; j++) {
					moons[i][j]+=velocity[i][j];
				}
			}
			//System.out.println("Moons = "+Arrays.deepToString(moons));
		}
		int[] res = new int[moons.length];
		for(int i = 0; i<res.length; i++) {
			res[i] = Arrays.stream(moons[i]).map(Math::abs).sum() * Arrays.stream(velocity[i]).map(Math::abs).sum();
		}
		return Arrays.stream(res).sum();
	}

	@Override
	public Object part2() throws IOException {
		Set<Numbers> set = new HashSet<>();
		long n;
		for(n = 0; true; n++) {
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
			//System.out.println("Res = "+Arrays.deepToString(velocity));
			for(int i = 0; i<moons.length; i++) {
				for(int j = 0; j<moons[0].length; j++) {
					moons[i][j]+=velocity[i][j];
				}
			}
			//System.out.println("Moons = "+Arrays.deepToString(moons));
			List<Integer> stuff = new ArrayList<>();
			stuff.addAll(Arrays.stream(moons).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
			stuff.addAll(Arrays.stream(velocity).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
			if(!set.add(new Numbers(stuff.stream().mapToInt(e -> e).toArray()))){
				break;
			}
		}
		return n;
	}
	
	class Numbers{
		int[] numbers;
		
		public Numbers(int...nums) {
			this.numbers=nums;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(numbers);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Numbers other = (Numbers) obj;
			if (!Arrays.equals(numbers, other.numbers))
				return false;
			return true;
		}
	}
}
