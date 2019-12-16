package com.sbaars.adventofcode2019.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("preview")
public class OCR {
	
	List<String[]> characters = new ArrayList<>();

	public OCR(String[] image) {
		for(int i = 0; i<image[0].length(); i+=5) {
			String[] character = new String[image.length];
			for(int j = 0; j<character.length; j++) {
				character[j] = image[j].substring(i,i+4);
			}
			characters.add(character);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(String[] character : characters) {
			result.append(switch(character[0]) {
				case "████" -> fullTop(character);
				case "███ " -> threeTopLeft(character);
				case "█  █" -> bothSides(character);
				case " ██ " -> middleTwo(character);
				case "  ██" -> 'J';
				case "█   " -> 'L';
				default -> dontKnow(character);
			});
		}
		return result.toString();
	}

	private char bothSides(String[] character) {
		return switch(character[5]) {
			case " ██ " -> 'U';
			case "█  █" -> 'H';
			default -> dontKnow(character);
		};
	}

	private char middleTwo(String[] character) {
		return switch(character[5]) {
			case " ███" -> 'G';
			case "█  █" -> 'A';
			default -> dontKnow(character);
		};
	}

	private char dontKnow(String[] character) {
		Arrays.stream(character).forEach(System.out::println);
		throw new IllegalArgumentException("I don't know your character yet!");
	}
	
	private char fullTop(String[] character) {
		return switch(character[5]) {
			case "█   " -> 'F';
			case "████" -> 'E';
			default -> dontKnow(character);
		};
	}

	private char threeTopLeft(String[] character) {
		return switch(character[5]) {
			case "█   " -> 'P';
			case "█  █" -> 'R';
			default -> dontKnow(character);
		};
	}
	
}
