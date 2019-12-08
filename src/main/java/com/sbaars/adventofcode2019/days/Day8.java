package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.CountMap;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day8 implements Day, DoesFileOperations {

	public static void main(String[] args) throws IOException {
		new Day8().printParts();
	}

	@Override
	public int part1() throws IOException {
		char[] chars = readDay(8).toCharArray();
		int[] pixels = IntStream.range(0, chars.length).map(i -> Character.getNumericValue(chars[i])).toArray();
		List<CountMap<Integer>> pixelCounts = new ArrayList<>();
		for(int i = 0; i<pixels.length; i+=25*6) {
			CountMap<Integer> cm = new CountMap<>();
			for(int j = i; j<i+(25*6); j++) {
				cm.increment(pixels[j]);
			}
			pixelCounts.add(cm);
		}
		System.out.println(pixelCounts.size());
		CountMap<Integer> cm = pixelCounts.stream().reduce((e1, e2) -> e1.get(0) > e2.get(0) ? e2 : e1).get();
		return cm.get(1) * cm.get(2);
	}

	@Override
	public int part2() throws IOException {
		char[] chars = readDay(8).toCharArray();
		int[] pixels = IntStream.range(0, chars.length).map(i -> Character.getNumericValue(chars[i])).toArray();
		int[][] pixelArrays = splitArray(pixels, 100, 25*6);
		int[] finalPixels = pixelArrays[0];
		for(int i = 1; i<pixelArrays.length; i++) {
			for(int j = 0; j<pixelArrays[i].length; j++) {
				if(finalPixels[j] == 2) {
					finalPixels[j] = pixelArrays[i][j];
				}
			}
		}
		System.out.println(Arrays.deepToString(pixelArrays));
		System.out.println(Arrays.toString(finalPixels));
		System.out.println(Arrays.deepToString(splitArray(finalPixels, 6, 25)));
		return 0;
	}
	
	int[][] splitArray(int[] arr, int x, int y){
		int[][] pixelArrays = new int[x][y];
		int cor = 0;
		for(int i = 0; i<arr.length; i+=y) {
			for(int j = i; j<i+y; j++) {
				pixelArrays[cor][j-i] = arr[j];
			}
			cor++;
		}
		return pixelArrays;
	}
}
