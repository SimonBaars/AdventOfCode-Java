package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sbaars.adventofcode2019.common.Day;

public class Day12 implements Day {

	short[][] moons = {{-5,6,-11},{-8,-4,-2},{1,16,4},{11,11,-4}};
	//int[][] moons = {{-8,-10,0},{5,5,10},{2,-7,3},{9,-8,-3}};
	//int[][] moons = {{-1,0,2},{2,-10,-7},{4,-8,8},{3,5,-1}};
	short[][] velocity = {{0,0,0},{0,0,0},{0,0,0},{0,0,0}};

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
			//res[i] = Arrays.stream(moons[i]).map(Math::abs).sum() * Arrays.stream(velocity[i]).map(Math::abs).sum();
		}
		return Arrays.stream(res).sum();
	}

	@Override
	public Object part2() throws IOException {
		final List<Set<List<Short>>> sets = Arrays.asList(new HashSet<List<Short>>(), new HashSet<List<Short>>(), new HashSet<List<Short>>());
		long[] res = new long[sets.size()];
		for(long n = 0; true; n++) {
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
//			List<Integer> stuff = new ArrayList<>();
//			stuff.addAll(Arrays.stream(moons).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
//			stuff.addAll(Arrays.stream(velocity).flatMapToInt(e -> Arrays.stream(e)).boxed().collect(Collectors.toList()));
//			if(n == 65976L || n == 45167L) {
//				System.out.println(Arrays.toString(stuff.toArray()));
//			}
//			
//			if(!set2.add(stuff)) {
//				System.out.println("Stuff is filled!");
//			}
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
	
	private static long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}
	
	private static long lcm(long a, long b)
	{
	    return a * (b / gcd(a, b));
	}

	private static long lcm(long[] input)
	{
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
	
	class Numbers{
		short[][][] numbers;
		
		public Numbers(short[][][] nums) {
			this.numbers=nums;
		}

		@Override
		public int hashCode() {
			return Arrays.deepHashCode(numbers);
		}

		@Override
		public boolean equals(Object obj) {
			Numbers other = (Numbers) obj;
//			System.out.println(iter+", "+other.iter);
			//System.out.println("Check deep equals "+toString()+" between "+other.toString());
			return Arrays.deepEquals(numbers, other.numbers);
		}

		@Override
		public String toString() {
			return "Numbers [numbers=" + Arrays.deepToString(numbers) + "]";
		}
	}
}
