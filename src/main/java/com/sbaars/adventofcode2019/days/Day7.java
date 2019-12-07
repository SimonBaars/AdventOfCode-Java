package com.sbaars.adventofcode2019.days;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day7 implements Day, DoesFileOperations {

	private static final int NUM_MODES = 3;
	private int instructionCounter = 0;
	private int part;
	int phase = 0;
	boolean isFirst = true;

	public static void main(String[] args) throws IOException {
		new Day7().printParts();
	}
	int lastVal = -1;
	
	@Override
	public int part1() throws IOException {
		Set<List<Integer>> shuffles = getShuffles(Arrays.asList(0,1,2,3,4));
		List<Integer> results = new ArrayList<>();
		//List<Integer> shuffle = Arrays.asList(4,3,2,1,0);
		for(List<Integer> shuffle : shuffles) {
			lastVal = -1;
			part = 0;
			for(Integer i : shuffle) {
				isFirst = true;
				phase = i;
				try {
					lastVal = execute();
				} catch(Exception e) {
					e.printStackTrace();
				}
				part++;
			}
			results.add(lastVal);
			
		}
		return results.stream().mapToInt(e -> e).max().getAsInt();
	}
	
	public Set<List<Integer>> getShuffles(List<Integer> ints){
		Set<List<Integer>> sets = new HashSet<List<Integer>>();
		for(int i = 0; i<100000; i++) {
			Collections.shuffle(ints);
			sets.add(new ArrayList<>(ints));
		}
		return sets;
	}
	
	@Override
	public int part2() throws IOException {
		return 0;
	}

	private int execute() throws IOException {
		instructionCounter = 0;
		int[] program = Arrays.stream(getFileAsString(new File(Day1.class.getClassLoader().getResource("day7.txt").getFile())).split(",")).mapToInt(Integer::parseInt).toArray();;
		int result;
		while((result = executeInstruction(program, program[instructionCounter])) == -1);
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
		if(args.length>=2) {
			if(method[2] == 0) args[1] = program[args[1]];
			if(args.length>=3 && method[1] == 0) args[2] = program[args[2]];
		}
		if(instruction == 4 && method[2] == 0) args[0] = program[args[0]];
		if((instruction == 5 || instruction == 6) && method[1] == 0) args[0] = program[args[0]];
		return executeInstruction(program, args, instruction);
	}
	
	private int executeInstruction(int[] program, int[] args, int instruction) {
		switch(instruction) {
			case 1: program[args[0]] = args[1] + args[2]; break;
			case 2: program[args[0]] = args[1] * args[2]; break;
			case 3: program[args[0]] = isFirst ? phase : (part == 0 ? 0 : lastVal); isFirst = false; break;
			case 4: /*if(args[0]!=0)*/ return args[0];/*lastVal=args[0];*/  //break;
			case 5: if(args[1] != 0) { instructionCounter = args[0]; return -1; } break;
			case 6: if(args[1] == 0) { instructionCounter = args[0]; return -1; } break;
			case 7: program[args[0]] = args[1] < args[2] ? 1 : 0; break;
			case 8: program[args[0]] = args[1] == args[2] ? 1 : 0; break;
			default: throw new IllegalStateException("Something went wrong!");
		}
		instructionCounter+=nParams(instruction) + 1;
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
					 else throw new IllegalStateException("Something went wrong! "+instruction);
		}
	}
}
