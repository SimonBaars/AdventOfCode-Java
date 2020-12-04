package com.sbaars.adventofcode.year19.intcode;

import com.sbaars.adventofcode.year19.Day2019;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.IntStream;

public class IntcodeComputer extends Day2019 {
	private long[] program;
	private int instructionCounter = 0;
	private final Queue<Long> input = new ArrayDeque<>(2);
	private long lastInput;
	private long relativeBase = 0;
	private static final int[] DO_NOT_TRANSFORM_FINAL_ARGUMENT = {1, 2, 3, 7, 8};
	public static final long STOP_CODE = Long.MIN_VALUE+1;
	private static final long CONTINUE_CODE = Long.MIN_VALUE;
	public RetentionPolicy policy;
	
	public IntcodeComputer(RetentionPolicy policy, long[] program, long...input) {
		super(0);
		this.program = Arrays.copyOf(program, 10000);
		setInput(input);
		this.policy = policy;
	}
	
	public IntcodeComputer(int day, long...input)  {
		super(day);
		this.program = dayNumbers(",");
		this.program = Arrays.copyOf(this.program, 10000);
		if(day == 2) {
			this.program[1] = input[0];
			this.program[2] = input[1];
		} else if(day == 17) {
			this.program[0] = input[0];
		}
		setInput(input);
		this.policy = RetentionPolicy.EXIT_ON_OUTPUT;
	}
	
	public long run(long...input) {
		setInput(input);
		return run();
	}
	
	public long run() {
		long result;
		while((result = executeInstruction(Math.toIntExact(program[instructionCounter]))) == CONTINUE_CODE);
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
		long[] args = IntStream.range(1, nParams+1).mapToLong(j -> program[instructionCounter+j]).toArray();
		transformParameters(method, args, instruction);
		return executeInstruction(args, instruction);
	}
	
	private void transformParameters(int[] method, long[] args, int instruction) {	
		IntStream.range(0, args.length).filter(i -> method[i] != 1).filter(i -> i+1 != args.length || !Arrays.stream(DO_NOT_TRANSFORM_FINAL_ARGUMENT).anyMatch(j -> j==instruction))
			.forEach(i -> args[i] = program[Math.toIntExact((method[i] == 2 ? relativeBase : 0) + args[i])]);
		if(Arrays.stream(DO_NOT_TRANSFORM_FINAL_ARGUMENT).anyMatch(j -> j==instruction) && method[args.length-1] == 2) {
			args[args.length-1] += relativeBase;
		}
	}
	
	private long readInput() {
		if(input.isEmpty())
			return lastInput;
		lastInput = input.poll();
		return lastInput;
	}
	
	public void addInput(long...num) {
		for(long n : num)
			input.add(n);
	}
	
	public long firstElement() {
		return program[0];
	}
	
	public void setInput(long...input) {
		this.input.clear();
		addInput(input);
	}
	
	private long executeInstruction(long[] args, int instruction) {
		if(instruction == 3 && policy == RetentionPolicy.EXIT_ON_EMPTY_INPUT && input.size() == 0)
			return STOP_CODE;
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
			case 99: return STOP_CODE;
			default: throw new IllegalStateException("Something went wrong!");
		}
		return CONTINUE_CODE;
	}
	
	private long parseComplexInstruction(int instruction) {
		int[] instructions = getInstructions(instruction);
		int opcode = getOpCode(instructions);
		return execute(new int[] {instructions[2], instructions[1], instructions[0]}, opcode);
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
	
	public void setElement(int i, long j) {
		program[i] = j;
	}

	public void setInput(String patterns) {
		setInput(patterns.chars().mapToLong(e -> e).toArray());
	}

	@Override
	public Object part1()  {
		return null;
	}

	@Override
	public Object part2()  {
		return null;
	}
}
