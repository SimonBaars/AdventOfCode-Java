package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 extends Day2016 {
    public Day21() {
        super(21);
    }

    public static void main(String[] args) {
        new Day21().printParts();
    }

    @Override
    public Object part1() {
        return scramble("abcdefgh", dayStream().toList());
    }

    @Override
    public Object part2() {
        List<String> instructions = new ArrayList<>(dayStream().toList());
        Collections.reverse(instructions);
        return unscramble("fbgdceah", instructions);
    }

    private String scramble(String password, List<String> instructions) {
        char[] chars = password.toCharArray();
        for (String instruction : instructions) {
            if (instruction.startsWith("swap position")) {
                Pattern pattern = Pattern.compile("swap position (\\d+) with position (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    char temp = chars[x];
                    chars[x] = chars[y];
                    chars[y] = temp;
                }
            } else if (instruction.startsWith("swap letter")) {
                Pattern pattern = Pattern.compile("swap letter (\\w) with letter (\\w)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    char x = matcher.group(1).charAt(0);
                    char y = matcher.group(2).charAt(0);
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == x) chars[i] = y;
                        else if (chars[i] == y) chars[i] = x;
                    }
                }
            } else if (instruction.startsWith("rotate left")) {
                Pattern pattern = Pattern.compile("rotate left (\\d+) step");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int steps = Integer.parseInt(matcher.group(1));
                    rotateLeft(chars, steps);
                }
            } else if (instruction.startsWith("rotate right")) {
                Pattern pattern = Pattern.compile("rotate right (\\d+) step");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int steps = Integer.parseInt(matcher.group(1));
                    rotateRight(chars, steps);
                }
            } else if (instruction.startsWith("rotate based")) {
                Pattern pattern = Pattern.compile("rotate based on position of letter (\\w)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    char letter = matcher.group(1).charAt(0);
                    int index = new String(chars).indexOf(letter);
                    int rotations = 1 + index + (index >= 4 ? 1 : 0);
                    rotateRight(chars, rotations);
                }
            } else if (instruction.startsWith("reverse")) {
                Pattern pattern = Pattern.compile("reverse positions (\\d+) through (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    reverse(chars, x, y);
                }
            } else if (instruction.startsWith("move")) {
                Pattern pattern = Pattern.compile("move position (\\d+) to position (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    move(chars, x, y);
                }
            }
        }
        return new String(chars);
    }

    private String unscramble(String scrambled, List<String> instructions) {
        char[] chars = scrambled.toCharArray();
        for (String instruction : instructions) {
            if (instruction.startsWith("swap position")) {
                Pattern pattern = Pattern.compile("swap position (\\d+) with position (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    char temp = chars[x];
                    chars[x] = chars[y];
                    chars[y] = temp;
                }
            } else if (instruction.startsWith("swap letter")) {
                Pattern pattern = Pattern.compile("swap letter (\\w) with letter (\\w)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    char x = matcher.group(1).charAt(0);
                    char y = matcher.group(2).charAt(0);
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == x) chars[i] = y;
                        else if (chars[i] == y) chars[i] = x;
                    }
                }
            } else if (instruction.startsWith("rotate left")) {
                Pattern pattern = Pattern.compile("rotate left (\\d+) step");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int steps = Integer.parseInt(matcher.group(1));
                    rotateRight(chars, steps); // Reverse of left is right
                }
            } else if (instruction.startsWith("rotate right")) {
                Pattern pattern = Pattern.compile("rotate right (\\d+) step");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int steps = Integer.parseInt(matcher.group(1));
                    rotateLeft(chars, steps); // Reverse of right is left
                }
            } else if (instruction.startsWith("rotate based")) {
                Pattern pattern = Pattern.compile("rotate based on position of letter (\\w)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    char letter = matcher.group(1).charAt(0);
                    int index = new String(chars).indexOf(letter);
                    // Find the original position that would have resulted in this position
                    int originalIndex = 0;
                    for (int i = 0; i < chars.length; i++) {
                        int rotations = 1 + i + (i >= 4 ? 1 : 0);
                        int newPos = (i + rotations) % chars.length;
                        if (newPos == index) {
                            originalIndex = i;
                            break;
                        }
                    }
                    int currentIndex = new String(chars).indexOf(letter);
                    int stepsToRotate = (currentIndex - originalIndex + chars.length) % chars.length;
                    rotateLeft(chars, stepsToRotate);
                }
            } else if (instruction.startsWith("reverse")) {
                Pattern pattern = Pattern.compile("reverse positions (\\d+) through (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    reverse(chars, x, y);
                }
            } else if (instruction.startsWith("move")) {
                Pattern pattern = Pattern.compile("move position (\\d+) to position (\\d+)");
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    move(chars, y, x); // Reverse the move by swapping x and y
                }
            }
        }
        return new String(chars);
    }

    private void rotateLeft(char[] chars, int steps) {
        steps = steps % chars.length;
        reverse(chars, 0, steps - 1);
        reverse(chars, steps, chars.length - 1);
        reverse(chars, 0, chars.length - 1);
    }

    private void rotateRight(char[] chars, int steps) {
        rotateLeft(chars, chars.length - (steps % chars.length));
    }

    private void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }

    private void move(char[] chars, int from, int to) {
        char c = chars[from];
        if (from < to) {
            System.arraycopy(chars, from + 1, chars, from, to - from);
        } else {
            System.arraycopy(chars, to, chars, to + 1, from - to);
        }
        chars[to] = c;
    }
}
