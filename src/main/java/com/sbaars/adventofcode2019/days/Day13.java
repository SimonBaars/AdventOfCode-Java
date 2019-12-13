package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day13 implements Day {

	public static void main(String[] args) throws IOException {
		new Day13().printParts();
	}

	@Override
	public Object part1() throws IOException {
		IntcodeComputer cp = new IntcodeComputer(13);
		Set<Point> n = new HashSet<Point>();
		while(true) {
			int x = cp.runInt();
			if(x == IntcodeComputer.STOP_CODE) return n.size();
			int y = cp.runInt();
			if(y == IntcodeComputer.STOP_CODE) return n.size();
			int tile = cp.runInt();
			if(tile == IntcodeComputer.STOP_CODE) return n.size();
			if(tile == 2)
				n.add(new Point(x,y));
		}
	}
	
	@Override
	public Object part2() throws IOException {
		IntcodeComputer cp = new IntcodeComputer(13, 1);
		cp.setElement(0, 2);
		int[][] field = new int[21][38];
		int score = 0;
		int n = 0;
		int paddlePos = 0, ballPos = 0;
		while(true) {
			int x = cp.runInt();
			if(x == IntcodeComputer.STOP_CODE)
				return score;
			int y = cp.runInt();
			if(x == IntcodeComputer.STOP_CODE)
				return score;
			int tile = cp.runInt();
			if(x == IntcodeComputer.STOP_CODE)
				return score;
			if(x == -1)
				score = tile;
			else {
				field[y][x] = tile;
				System.out.println("Set "+y+", "+x+" to "+tile);
				if(tile == 2)
					n++;
				else if (tile == 3)
					paddlePos = x;
				else if (tile == 4) {
					ballPos = x;
					Arrays.stream(field).map(e -> Arrays.stream(e).mapToObj(Integer::toString).map(f -> f.replace("4", "█")).collect(Collectors.joining())).forEach(System.out::println);
				}
			}
			if(ballPos>paddlePos) cp.setInput(1);
			else if(ballPos<paddlePos) cp.setInput(-1);
			else cp.setInput(0);
			/*if(x == 37 && y == 20) {
				if(n == 0) {
					return score;
				}
				n = 0;
				Arrays.stream(field).map(e -> Arrays.stream(e).mapToObj(Integer::toString).map(f -> f.replace("4", "█")).collect(Collectors.joining())).forEach(System.out::println);
				System.out.println("enter input");
			}*/
		}
	}
	
	public class Tile{
		Point pos;
		int element;
	}
}
