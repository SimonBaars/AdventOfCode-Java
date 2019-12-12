package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sbaars.adventofcode2019.common.Day;

public class Day12 implements Day {

	//int[][] moons = {{-5,6,-11},{-8,-4,-2},{1,16,4},{11,11,-4}};
	int[][] moons = {{-8,-10,0},{5,5,10},{2,-7,3},{9,-8,-3}};
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
							//moons[i][dim]++;
							//moons[j][dim]--;
						} else if(moon1 > moon2) {
							velocity[i][dim]--;
							velocity[j][dim]++;
							//moons[j][dim]++;
							//moons[i][dim]--;
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
			//List<Integer> stuff = new ArrayList<>();
			//stuff.addAll(Arrays.stream(moons).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
			//stuff.addAll(Arrays.stream(velocity).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
			if(!set.add(new Numbers(new int[][][] {moons,velocity}))){
				//System.out.println(Arrays.deepToString(new int[][][] {moons,velocity}));
				break;
			}
			
			if(n % 1000000 == 0) {
				int minMoon = Arrays.stream(moons).flatMapToInt(e -> Arrays.stream(e)).min().getAsInt();
				int maxMoon = Arrays.stream(moons).flatMapToInt(e -> Arrays.stream(e)).max().getAsInt();
				int minVel = Arrays.stream(velocity).flatMapToInt(e -> Arrays.stream(e)).min().getAsInt();
				int maxVel = Arrays.stream(velocity).flatMapToInt(e -> Arrays.stream(e)).max().getAsInt();
				System.out.println(n+". Moon: "+minMoon+", "+maxMoon+", Vel: "+minVel+", "+maxVel);
			}
		}
		return n;
	}
	
	class Numbers{
		int[][][] numbers;
		
		public Numbers(int[][][] nums) {
			this.numbers=nums;
		}

		@Override
		public int hashCode() {
			return Arrays.deepHashCode(numbers);
		}

		@Override
		public boolean equals(Object obj) {
			Numbers other = (Numbers) obj;
			//System.out.println("Check deep equals "+toString()+" between "+other.toString());
			return Arrays.deepEquals(numbers, other.numbers);
		}

		@Override
		public String toString() {
			return "Numbers [numbers=" + Arrays.deepToString(numbers) + "]";
		}
	}
}
