package com.sbaars.adventofcode2019.intcode;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class IntcodeComputer implements DoesFileOperations {
	final int[] program;
	private int instructionCounter = 0;
	private final Queue<Integer> input = new ArrayDeque<>(2);
	private int lastInput;
	
	public IntcodeComputer(int day, int...input) throws IOException {
		this.program = Arrays.stream(readDay(day).split(",")).mapToInt(Integer::parseInt).toArray();
		setInput(input);
		if(day == 2) {
			program[1] = input[0];
			program[2] = input[1];
		}
	}
	
	public int run() {
		int result;
		while((result = executeInstruction(program, program[instructionCounter])) == 0);
		return result;
	}

	private int executeInstruction(int[] program, int instruction) {
		if(instruction>99) 
			return parseComplexInstruction(program, instruction);
		return execute(program, instruction);
	}

	private int execute(int[] program, int instruction) {
		return execute(program, new int[3], instruction);
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

	private int readInput() {
		if(input.isEmpty())
			return lastInput;
		lastInput = input.poll();
		return lastInput;
	}
	
	public boolean addInput(int num) {
		return input.add(num);
	}
	
	public int firstElement() {
		return program[0];
	}
	
	public boolean setInput(int...input) {
		this.input.clear();
		return this.input.addAll(Arrays.stream(input).boxed().collect(Collectors.toList()));
	}
	
	private int executeInstruction(int[] program, int[] args, int instruction) {
		instructionCounter+=nParams(instruction) + 1;
		switch(instruction) {
			case 1: program[args[0]] = args[1] + args[2]; break;
			case 2: program[args[0]] = args[1] * args[2]; break;
			case 3: program[args[0]] = readInput(); break;
			case 4: return args[0];
			case 5: if(args[1] != 0) instructionCounter = args[0]; break;
			case 6: if(args[1] == 0) instructionCounter = args[0]; break;
			case 7: program[args[0]] = args[1] < args[2] ? 1 : 0; break;
			case 8: program[args[0]] = args[1] == args[2] ? 1 : 0; break;
			case 99: return -1;
			default: throw new IllegalStateException("Something went wrong!");
		}
		return 0;
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
			case 99: return -1;
			case 3: 
			case 4: return 1;
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
}
