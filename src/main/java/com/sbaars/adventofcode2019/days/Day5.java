package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day5 implements Day, DoesFileOperations {
	
	boolean parameterMode = true;
	private static int NUM_MODES = 3;
	private int instructionCounter = 0;

	public static void main(String[] args) throws IOException {
		new Day5().printParts();
	}
	
	public int part1() throws IOException {
		return execute(12, 2);
	}

	private int execute(int x, int y) throws IOException {
		int[] program = Arrays.stream(getFileAsString(new File(Day1.class.getClassLoader().getResource("day5.txt").getFile())).split(",")).mapToInt(Integer::parseInt).toArray();;
		//program[1] = x;
		//program[2] = y;
		for(; executeInstruction(program, program[instructionCounter]); instructionCounter+=nParams(program[instructionCounter])+1);
		return program[0];
	}

	/*private boolean executeInstruction(int[] program, int i, int instruction) {
		switch(instruction) {
			case 1: program[program[i+3]] = program[program[i+1]] + program[program[i+2]];
			case 2: program[program[i+3]] = program[program[i+1]] * program[program[i+2]]; break;
			case 99: return false;
			default: throw new IllegalStateException("Something went wrong!");
		}
		
		return true;
	}*/
	
	private boolean executeInstruction(int[] program, int i, int instruction) {
		System.out.println("Executing instruction "+instruction+" at i "+i);
		if(instruction>99) 
			return parseComplexInstruction(program, i, instruction);
		else if(instruction == 99)
			return false;
		return execute(program, i, instruction);
	}
	
	private boolean execute(int[] program, int i, int instruction) {
		return execute(program, new int[NUM_MODES], i, instruction);
	}

	private boolean execute(int[] program, int[] method, int i, int instruction) {
		int nParams = nParams(instruction);
		int[] args = IntStream.range(0, nParams).map(j -> j == 0 ? program[i+nParams] : program[i+j]).toArray();
		if(args.length>2) Arrays.stream(new int[]{2, 1}).filter(j -> method[j] == 0).map(j -> j == 2 ? 1 : 2).forEach(j -> args[j] = program[args[j]]);
		if(instruction == 4 && method[2] == 0) args[0] = program[args[0]];
		return executeInstruction(program, args, instruction);
	}
	
	/*static int reverseRange(int num, int from, int to) {
	    return to - num + from - 1;
	}*/
	
	private boolean executeInstruction(int[] program, int[] args, int instruction) {
		switch(instruction) {
			case 1: program[args[0]] = args[1] + args[2]; break;
			case 2: program[args[0]] = args[1] * args[2]; break;
			case 3: program[args[0]] = 1; break;
			case 4: if(args[0]!=0) System.out.println(args[0]); break;
			default: throw new IllegalStateException("Something went wrong!");
		}
		
		return true;
	}
	
	private boolean parseComplexInstruction(int[] program, int i, int instruction) {
		int[] instructions = getInstructions(instruction);
		int opcode = getOpCode(instructions);
		System.out.println("Parsed as "+Arrays.toString(instructions)+" opcode "+opcode);
		return execute(program, instructions, i, opcode);
		
	}
	
	private int getOpCode(int instruction) {
		return getOpCode(getInstructions(instruction));
	}

	private int getOpCode(int[] instructions) {
		return (instructions[3] * 10) + instructions[4];
	}

	private int[] getInstructions(int instruction) {
		int[] instructions = new int[5];
		for(int j = instructions.length-1; instruction>0; j--) {
			instructions[j] = instruction % 10;
			instruction /= 10;
		}
		return instructions;
	}
	
	/*private int[] getParams(int[] program, int instruction, boolean parameterMode) {
		int nParams = nParams(instruction);
		IntStream params = IntStream.range(1,nParams+1).map(i -> program[i]);
		if(parameterMode) params = 
	}
	
	*/private int nParams(int instruction) {
		switch(instruction) {
			case 1: 
			case 2: return 3;
			case 3: 
			case 4: return 1;
			default: if(instruction>=99) return nParams(getOpCode(instruction));
					 else throw new IllegalStateException("Something went wrong!");
		}
	}

	@Override
	public int part2() throws IOException {
		return -1;
	}

}
