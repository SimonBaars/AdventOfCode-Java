package com.sbaars.adventofcode2019.intcode;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class IntcodeComputer implements DoesFileOperations {
	final long[] program;
	private int instructionCounter = 0;
	private final Queue<Integer> input = new ArrayDeque<>(2);
	private int lastInput;
	private long relativeBase = 0;
	private static final int[] DO_NOT_TRANSFORM_FINAL_ARGUMENT = {1, 2, 3, 7, 8};
	
	public IntcodeComputer(int day, int...input) throws IOException {
		this.program = Arrays.stream(readDay(day).split(",")).mapToLong(Long::parseLong).toArray();
		setInput(input);
		if(day == 2) {
			program[1] = input[0];
			program[2] = input[1];
		}
	}
	
	public long run() {
		long result;
		while((result = executeInstruction(Math.toIntExact(program[instructionCounter]))) == 0);
		return result;
	}

	private long executeInstruction(int instruction) {
		if(instruction>99) 
			return parseComplexInstruction(instruction);
		return execute(instruction);
	}

	private long execute(int instruction) {
		return execute(new int[3], instruction);
	}

	private long execute(int[] method, int instruction) {
		int nParams = nParams(instruction);
		long[] args = IntStream.range(0, nParams).mapToLong(j -> program[instructionCounter+j]).toArray();
		//transformParam2(method, args);
		//transformDataForMode(method, instruction, args);
		transformParameters(method, args, instruction);
		return executeInstruction(args, instruction);
	}
	
	private void transformParameters(int[] method, long[] args, int instruction) {
		IntStream.range(0, args.length).filter(i -> method[i] != 1).filter(i -> i+1 != args.length || !Arrays.stream(DO_NOT_TRANSFORM_FINAL_ARGUMENT)
				.anyMatch(j -> j==instruction)).forEach(i -> args[i] = program[Math.toIntExact((method[i] == 2 ? relativeBase : 0) + args[i])]);
	}

	/*private void transformParam2(int[] method, long[] args) {
		if(args.length>=2) {
			if(method[2] == 0 || method[2] == 2) args[1] = method[2] == 0 ? program[Math.toIntExact(args[1])] : program[Math.toIntExact(relativeBase+args[1])];
			if(args.length>=3 && (method[1] == 0 || method[1] == 2)) args[2] = method[1] == 0 ? program[Math.toIntExact(args[2])] : program[Math.toIntExact(relativeBase+args[2])];
		}
	}

	private void transformDataForMode(int[] method, int instruction, long[] args) {
		if(instruction == 4 && (method[2] == 0 || method[2] == 2)) {
			args[0] = method[2] == 0 ? program[Math.toIntExact(args[0])] : program[Math.toIntExact(relativeBase+args[0])];
		}
		if((instruction == 5 || instruction == 6) && (method[1] == 0 || method[1] == 2)) args[0] = method[1] == 0 ? program[Math.toIntExact(args[0])] : program[Math.toIntExact(relativeBase+args[0])];
	}*/

	private int readInput() {
		if(input.isEmpty())
			return lastInput;
		lastInput = input.poll();
		return lastInput;
	}
	
	public boolean addInput(int num) {
		return input.add(num);
	}
	
	public long firstElement() {
		return program[0];
	}
	
	public boolean setInput(int...input) {
		this.input.clear();
		return this.input.addAll(Arrays.stream(input).boxed().collect(Collectors.toList()));
	}
	
	private long executeInstruction(long[] args, int instruction) {
		instructionCounter+=nParams(instruction) + 1;
		switch(instruction) {
			case 1: program[Math.toIntExact(args[2])] = args[0] + args[1]; break;
			case 2: program[Math.toIntExact(args[2])] = args[0] * args[1]; break;
			case 3: program[Math.toIntExact(args[0])] = readInput(); break;
			case 4: return args[0];
			case 5: if(args[0] != 0) instructionCounter = Math.toIntExact(args[1]); break;
			case 6: if(args[0] == 0) instructionCounter = Math.toIntExact(args[1]); break;
			case 7: program[Math.toIntExact(args[2])] = args[0] < args[1] ? 1 : 0; break;
			case 8: program[Math.toIntExact(args[2])] = args[0] == args[1] ? 1 : 0; break;
			case 9: relativeBase += Math.toIntExact(args[0]); break;
			case 99: return -1;
			default: throw new IllegalStateException("Something went wrong!");
		}
		return 0;
	}
	
	private long parseComplexInstruction(int instruction) {
		int[] instructions = getInstructions(instruction);
		int opcode = getOpCode(instructions);
		return execute(instructions, opcode);
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
			case 99: return -1;
			case 3: 
			case 4: 
			case 9: return 1;
			case 5:
			case 6: return 2;
			case 1: 
			case 2:
			case 7:
			case 8: return 3;
			default: if(instruction>99) return nParams(getOpCode(instruction));
			else throw new IllegalStateException("Something went wrong! "+instruction);
		}
	}

	public int runInt() {
		return Math.toIntExact(run());
	}
}
