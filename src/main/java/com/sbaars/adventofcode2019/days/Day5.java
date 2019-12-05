package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day5 implements Day, DoesFileOperations {

	private static int NUM_MODES = 3;
	private int instructionCounter = 0;
	private int part;

	public static void main(String[] args) throws IOException {
		new Day5().printParts();
	}
	
	@Override
	public int part1() throws IOException {
		part = 1;
		return execute();
	}
	
	@Override
	public int part2() throws IOException {
		part = 2;
		return execute();
	}

	private int execute() throws IOException {
		int[] program = Arrays.stream(getFileAsString(new File(Day1.class.getClassLoader().getResource("day5.txt").getFile())).split(",")).mapToInt(Integer::parseInt).toArray();;
		int result;
		for(; (result = executeInstruction(program, program[instructionCounter])) == -1; instructionCounter+=nParams(program[instructionCounter])+1);
		return result;
	}

	private int executeInstruction(int[] program, int instruction) {
		if(instruction>99) 
			return parseComplexInstruction(program, instruction);
		else if(instruction == 99)
			throw new IllegalStateException("Hit terminal code 99 before finding diagnostic code!");
		return execute(program, instruction);
	}
	
	private int execute(int[] program, int instruction) {
		return execute(program, new int[NUM_MODES], instruction);
	}

	private int execute(int[] program, int[] method, int instruction) {
		int nParams = nParams(instruction);
		int[] args = IntStream.range(0, nParams).map(j -> j == 0 ? program[instructionCounter+nParams] : program[instructionCounter+j]).toArray();
		if(args.length>2) Arrays.stream(new int[]{2, 1}).filter(j -> method[j] == 0).map(j -> j == 2 ? 1 : 2).forEach(j -> args[j] = program[args[j]]);
		if(instruction == 4 && method[2] == 0) args[0] = program[args[0]];
		return executeInstruction(program, args, instruction);
	}
	
	private int executeInstruction(int[] program, int[] args, int instruction) {
		switch(instruction) {
			case 1: program[args[0]] = args[1] + args[2]; break;
			case 2: program[args[0]] = args[1] * args[2]; break;
			case 3: program[args[0]] = part == 1 ? 1 : 5; break;
			case 4: if(args[0]!=0) return args[0]; break;
			case 5: if(args[1] != 0) instructionCounter = args[0]; break;
			case 6: if(args[1] == 0) instructionCounter = args[0]; break;
			case 7: program[args[0]] = args[1] < args[2] ? 1 : 0; break;
			case 8: program[args[0]] = args[1] == args[2] ? 1 : 0; break;
			default: throw new IllegalStateException("Something went wrong!");
		}
		
		return -1;
	}
	
	private int parseComplexInstruction(int[] program, int instruction) {
		int[] instructions = getInstructions(instruction);
		int opcode = getOpCode(instructions);
		return execute(program, instructions, opcode);
		
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
	
	private int nParams(int instruction) {
		switch(instruction) {
			case 3: 
			case 4: return 1;
			case 5:
			case 6: return 2;
			case 1: 
			case 2:
			case 7:
			case 8: return 3;
			default: if(instruction>=99) return nParams(getOpCode(instruction));
					 else throw new IllegalStateException("Something went wrong!");
		}
	}
}
