package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
		int[] pixels = readPixels();
		List<CountMap<Integer>> pixelCounts = countPixels(pixels);
		CountMap<Integer> cm = pixelCounts.stream().reduce((e1, e2) -> e1.get(0) > e2.get(0) ? e2 : e1).get();
		return cm.get(1) * cm.get(2);
	}

	private int[] readPixels() throws IOException {
		char[] chars = readDay(8).toCharArray();
		int[] pixels = IntStream.range(0, chars.length).map(i -> Character.getNumericValue(chars[i])).toArray();
		return pixels;
	}

	private List<CountMap<Integer>> countPixels(int[] pixels) {
		List<CountMap<Integer>> pixelCounts = new ArrayList<>();
		for(int i = 0; i<pixels.length; i+=25*6) {
			CountMap<Integer> cm = new CountMap<>();
			for(int j = i; j<i+(25*6); j++) {
				cm.increment(pixels[j]);
			}
			pixelCounts.add(cm);
		}
		return pixelCounts;
	}

	@Override
	public int part2() throws IOException {
		int[] pixels = readPixels();
		int[][] pixelArrays = splitArray(pixels, 100, 25*6);
		int[] finalPixels = determineFinalImage(pixelArrays);
		Arrays.stream(splitArray(finalPixels, 6, 25)).map(a -> Arrays.stream(a).boxed().map(x -> x == 0 ? " " : "█").collect(Collectors.joining())).forEach(System.out::println);
		return 0; // Answer: FPUAR
	}

	private int[] determineFinalImage(int[][] pixelArrays) {
		int[] finalPixels = pixelArrays[0];
		for(int i = 1; i<pixelArrays.length; i++) {
			for(int j = 0; j<pixelArrays[i].length; j++) {
				if(finalPixels[j] == 2) {
					finalPixels[j] = pixelArrays[i][j];
				}
			}
		}
		return finalPixels;
	}
	
	int[][] splitArray(int[] arr, int x, int y){
		int[][] pixelArrays = new int[x][y];
		for(int i = 0; i<arr.length; i+=y)
			for(int j = i; j<i+y; j++)
				pixelArrays[i/y][j-i] = arr[j];
		return pixelArrays;
	}
}
