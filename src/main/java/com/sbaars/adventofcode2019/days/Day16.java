package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;

import com.sbaars.adventofcode2019.common.Day;

public class Day16 implements Day {
	
	int targetPos = 5977341;

	public static void main(String[] args) throws IOException {
		new Day16().part2();
	}

	@Override
	public Object part1() throws IOException {
		int[] nums = readDay(16).chars().map(e -> Character.getNumericValue(e)).toArray();
		//System.out.println(Arrays.toString(nums));
		int[] pattern = {0, 1, 0, -1};
		for(int phase = 0; phase<100; phase++) {
		int[] newNums = new int[nums.length];
		int timesEachElement = 1;
		for(int j = 0; j<nums.length; j++) {
			int[] sumArray = new int[nums.length];
	 		for(int i = 0, patternIndex = 0, elementTimes = 1; i<nums.length; i++) {
	 			if(elementTimes == timesEachElement) {
	 				patternIndex++;
	 				elementTimes = 0;
	 				if(patternIndex == pattern.length)
						patternIndex=0;
	 			}
	 			
	 			sumArray[i] = nums[i] * pattern[patternIndex];
	 			//System.out.print(nums[i] +"*" +pattern[patternIndex]+" ");
	 			
	 			elementTimes++;
			}
	 		//System.out.println();
	 		newNums[j] = lastDigit(Arrays.stream(sumArray).sum());
	 		timesEachElement++;
		}
		//System.out.println(arrayToInt(newNums));
		System.out.println(Arrays.toString(newNums));
		nums = newNums;
		}
		return 0;
	}
	
	int arrayToInt(int[] arr)
	{
	    int result = 0;

	    //iterate backwards through the array so we start with least significant digits
	    for (int n = arr.length - 1, i = 1; n >= 0; n --, i *= 10) 
	    {
	         result += Math.abs(arr[n]) * i;
	    }

	    if (arr[0] < 0) //if there's a negative sign in the beginning, flip the sign
	    {
	        result = - result;
	    }

	    return result;
	}

	
	private int lastDigit(int number) {
		return Math.abs(number) % 10;
	}
	
	@Override
	public Object part2() throws IOException {
		int[] nums = readDay(16).chars().map(e -> Character.getNumericValue(e)).toArray();
		nums = repeat(nums, 10000);
		//System.out.println(nums.length);
		//int[] nums = repeat(actNums, 10000);
		//System.out.println(Arrays.toString(nums));
		int[] pattern = {0, 1, 0, -1};
		
		int[] res = new int[nums.length];
		for(int phase = 0; phase<100; phase++) {
			System.out.println("Phase "+phase);
			
			int[] newNums = new int[nums.length+1];
			for(int i=0;i<nums.length;i++) {
				newNums[i+1]=newNums[i]+nums[i];
			}
		
			for(int i = 0; i<nums.length; i++) {
				int sum = 0, loc = 0;
				for(int j=0; true;j++) {
					int k=((j+1)*(i+1))-1;
					sum += (newNums[Math.min(k, res.length)] - newNums[loc]) * pattern[j%4];
					if(k >= res.length) break;
					loc=k;
				}
				res[i]=Math.abs(sum)%10;
			}
			
			System.arraycopy(res, 0, nums, 0, res.length);
			//nums=Arrays.copyOf(res,res.length);
		//int timesEachElement = 1;
		//for(int j = 0; j<totalTimes; j++) {
			//System.out.println("Num "+j);
			//int[] sumArray = new int[nums.length];
			//Map<Point, int[]> numsForIndices = new HashMap<>();
			//CountMap<Point> timesEachPoint = new CountMap<>();
			/*for(int o = 0; o<totalTimes; o+=actNums.length) {
				int patternIndex = (o / timesEachElement) % pattern.length;
				int elementTimes = o % timesEachElement + 1;
				//System.out.println(patternIndex+", "+elementTimes);
				Point p = new Point(patternIndex, elementTimes);
				if(numsForIndices.containsKey(p)) {
					//int[] arr = numsForIndices.get(p);
					//System.arraycopy(sumArray, o, arr, 0, arr.length);
					timesEachPoint.increment(p);
				} else {
					int[] res = new int[actNums.length];
					for(int i = 0; i<actNums.length; i++) {
			 			if(elementTimes == timesEachElement) {
			 				patternIndex++;
			 				elementTimes = 0;
			 				if(patternIndex == pattern.length)
								patternIndex=0;
			 			}
			 			
			 			res[i] = actNums[i] * pattern[patternIndex];
			 			//System.out.print(nums[i] +"*" +pattern[patternIndex]+" ");
			 			
			 			elementTimes++;
					}
					//System.arraycopy(sumArray, o, res, 0, res.length);
					numsForIndices.put(p, res);
					timesEachPoint.increment(p);
				}
			}*/
	 		//System.out.println();
			/*int sum = 0;
			for(Entry<Point, int[]> arr : numsForIndices.entrySet()) {
				sum+=Arrays.stream(arr.getValue()).sum() * timesEachPoint.get(arr.getKey());
			}
	 		newNums[j] = lastDigit(sum);
	 		timesEachElement++;*/
		}
		//System.out.println(arrayToInt(newNums));
		//System.out.println(Arrays.toString(newNums));
		//if(phase == 99) {
			for(int i = 5977341; i<5977341+8; i++) {
				System.out.println(res[i]);
			}
		//}
		//actNums = newNums;
		//}
		return 0;
	}
	
	public static int[] repeat(int[] arr, int newLength) {
		newLength = newLength * arr.length;
	    int[] dup = Arrays.copyOf(arr, newLength);
	    for (int last = arr.length; last != 0 && last < newLength; last <<= 1) {
	        System.arraycopy(dup, 0, dup, last, Math.min(last << 1, newLength) - last);
	    }
	    return dup;
	}

}
