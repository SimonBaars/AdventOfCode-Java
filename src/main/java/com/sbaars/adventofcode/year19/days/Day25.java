package com.sbaars.adventofcode.year19.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import com.sbaars.adventofcode.year19.intcode.RetentionPolicy;

public class Day25 extends Day2019 {

	public static void main(String[] args)  {
		new Day25().play();
	}

	public Day25(){super(25);}

	private void play()  {
		IntcodeComputer ic = new IntcodeComputer(RetentionPolicy.EXIT_ON_EMPTY_INPUT, dayNumbers());
		while(true) {
			long res;
			while((res = ic.run()) != IntcodeComputer.STOP_CODE) System.out.print((char)res);
			try {
				ic.setInput(new BufferedReader(new InputStreamReader(System.in)).readLine() + "\n");
			} catch (Exception e){}
		}
	}

	@Override
	public Object part1()  {
		IntcodeComputer ic = new IntcodeComputer(RetentionPolicy.EXIT_ON_EMPTY_INPUT, dayNumbers());
		String[] inputs = new String [] {"west", "take semiconductor", "west", "take planetoid", "west", "take food ration", "west", "take fixed point", "east", "east", "south", "east", "east", "north", "east", "north"};
		String numbers = "";
		for(int i = 0; i<=inputs.length; i++) {
			long res;
			while((res = ic.run()) != IntcodeComputer.STOP_CODE) {
				if(Character.isDigit((char)res)) numbers += (char)res;
			}
			if(i == inputs.length) return numbers.substring(1);
			ic.setInput(inputs[i]+"\n");
		}
		return 0;
	}

	@Override
	public Object part2()  {
		return "MERRY CHRISTMAS!!";
	}
}
