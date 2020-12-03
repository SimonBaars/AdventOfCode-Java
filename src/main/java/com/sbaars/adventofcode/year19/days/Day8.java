package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.ProcessesImages;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.util.CountMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.var;

public class Day8 extends Day2019 implements ProcessesImages {
	public Day8(){
		super(8);
	}

	private static final int DIM_X = 6;
	private static final int DIM_Y = 25;
	private static final int SIZE = DIM_X*DIM_Y;

	public static void main(String[] args) throws IOException {
		new Day8().printParts();
	}

	@Override
	public Object part1() throws IOException {
		int[] pixels = readPixels();
		var pixelCounts = countPixels(pixels);
		var cm = pixelCounts.stream().reduce((e1, e2) -> e1.get(0) > e2.get(0) ? e2 : e1).get();
		return cm.get(1) * cm.get(2);
	}

	private int[] readPixels() throws IOException {
		char[] chars = day().toCharArray();
		return IntStream.range(0, chars.length).map(i -> Character.getNumericValue(chars[i])).toArray();
	}

	private List<CountMap<Integer>> countPixels(int[] pixels) {
		List<CountMap<Integer>> pixelCounts = new ArrayList<>();
		for(int i = 0; i<pixels.length; i+=SIZE) {
			CountMap<Integer> cm = new CountMap<>();
			for(int j = i; j<i+SIZE; j++) {
				cm.increment(pixels[j]);
			}
			pixelCounts.add(cm);
		}
		return pixelCounts;
	}

	@Override
	public Object part2() throws IOException {
		int[][] pixelArrays = splitArray(readPixels(), 100, SIZE);
		int[] finalPixels = determineFinalImage(pixelArrays);
		return printAsciiArray(splitArray(finalPixels, DIM_X, DIM_Y));
	}

	private int[] determineFinalImage(int[][] pixelArrays) {
		int[] finalPixels = pixelArrays[0];
		for(int i = 1; i<pixelArrays.length; i++)
			for(int j = 0; j<pixelArrays[i].length; j++)
				if(finalPixels[j] == 2)
					finalPixels[j] = pixelArrays[i][j];
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
