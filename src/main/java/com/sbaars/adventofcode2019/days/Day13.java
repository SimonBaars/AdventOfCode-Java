package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
			int tile = cp.runInt();
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
		AtomicInteger paddlePos = new AtomicInteger(), ballPos = new AtomicInteger();
		while(true) {
			int x = cp.runInt();
			if(x == IntcodeComputer.STOP_CODE)
				return score;
			int y = cp.runInt();
			int tile = cp.runInt();
			score = simulateField(cp, field, score, paddlePos, ballPos, x, y, tile);
		}
	}

	private int simulateField(IntcodeComputer cp, int[][] field, int score, AtomicInteger paddlePos, AtomicInteger ballPos, int x, int y, int tile) {
		if(x == -1)
			return tile;
		else {
			field[y][x] = tile;
			if (tile == 3) {
				paddlePos.set(x);;
			} else if (tile == 4) {
				ballPos.set(x);
				cp.setInput(provideInput(paddlePos, ballPos));
			}
		}
		return score;
	}

	private int provideInput(AtomicInteger paddlePos, AtomicInteger ballPos) {
		int ball = ballPos.get(), paddle = paddlePos.get();
		if(ball>paddle) return 1;
		else if(ball<paddle) return -1;
		else return 0;
	}
}
