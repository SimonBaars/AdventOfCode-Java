package com.sbaars.adventofcode2019.util;

import java.util.ArrayList;
import java.util.List;

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
	
	// I will expand this as more characters present themselves throughout these challenges.
	public String getString() {
		StringBuilder result = new StringBuilder();
		for(String[] character : characters) {
			if(character[0].equals("████"))
				result.append('F');
			else if(character[0].equals("███ ")) {
				if(character[5].equals("█   "))
					result.append('P');
				else if(character[5].equals("█  █")) {
					result.append('R');
				}
			} else if (character[0].equals("█  █")) {
				result.append('U');
			} else if(character[0].equals(" ██ ")) {
				result.append('A');
			}
		}
		return result.toString();
	}
	
}
