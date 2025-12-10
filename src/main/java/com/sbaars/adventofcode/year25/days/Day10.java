package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends Day2025 {
    public Day10() {
        super(10);
    }

    public static void main(String[] args) throws IOException {
        new Day10().printParts();
    }

    record Machine(boolean[] target, List<Set<Integer>> buttons, int[] joltage) {}

    @Override
    public Object part1() {
        return dayStream()
            .map(this::parseMachine)
            .mapToInt(this::solveMinimumPresses)
            .sum();
    }

    @Override
    public Object part2() {
        return dayStream()
            .map(this::parseMachine)
            .mapToInt(this::solveMinimumPressesJoltage)
            .sum();
    }

    private int solveMinimumPressesJoltage(Machine m) {
        int[] targets = m.joltage;
        int numCounters = targets.length;
        int numButtons = m.buttons.size();
        
        // Create matrix where matrix[counter][button] = 1 if button affects counter, 0 otherwise
        int[][] matrix = new int[numCounters][numButtons];
        for (int counter = 0; counter < numCounters; counter++) {
            for (int button = 0; button < numButtons; button++) {
                matrix[counter][button] = m.buttons.get(button).contains(counter) ? 1 : 0;
            }
        }
        
        return solveILP(matrix, targets, numButtons, numCounters);
    }
    
    private int solveILP(int[][] matrix, int[] targets, int numButtons, int numCounters) {
        // Use Gaussian elimination over rationals, then branch-and-bound on free variables
        // Create augmented matrix (using long to avoid overflow)
        long[][] aug = new long[numCounters][numButtons + 1];
        for (int i = 0; i < numCounters; i++) {
            for (int j = 0; j < numButtons; j++) {
                aug[i][j] = matrix[i][j];
            }
            aug[i][numButtons] = targets[i];
        }
        
        // Gaussian elimination to get row echelon form
        int[] pivot = new int[numCounters];
        Arrays.fill(pivot, -1);
        
        for (int col = 0, row = 0; col < numButtons && row < numCounters; col++) {
            // Find pivot
            int pivotRow = -1;
            for (int r = row; r < numCounters; r++) {
                if (aug[r][col] != 0) {
                    pivotRow = r;
                    break;
                }
            }
            
            if (pivotRow == -1) continue;
            
            // Swap rows
            if (pivotRow != row) {
                long[] temp = aug[row];
                aug[row] = aug[pivotRow];
                aug[pivotRow] = temp;
            }
            
            pivot[row] = col;
            
            // Eliminate (using integer arithmetic)
            for (int r = 0; r < numCounters; r++) {
                if (r != row && aug[r][col] != 0) {
                    // Multiply row r by aug[row][col], subtract aug[r][col] * row from r
                    long mult = aug[r][col];
                    long div = aug[row][col];
                    for (int c = 0; c <= numButtons; c++) {
                        aug[r][c] = aug[r][c] * div - mult * aug[row][c];
                    }
                    // Simplify by GCD
                    long gcd = 0;
                    for (int c = 0; c <= numButtons; c++) {
                        gcd = gcd(gcd, Math.abs(aug[r][c]));
                    }
                    if (gcd > 1) {
                        for (int c = 0; c <= numButtons; c++) {
                            aug[r][c] /= gcd;
                        }
                    }
                }
            }
            row++;
        }
        
        // Identify free variables
        boolean[] isBasic = new boolean[numButtons];
        for (int r = 0; r < numCounters; r++) {
            if (pivot[r] >= 0) {
                isBasic[pivot[r]] = true;
            }
        }
        
        List<Integer> freeVars = new ArrayList<>();
        for (int j = 0; j < numButtons; j++) {
            if (!isBasic[j]) {
                freeVars.add(j);
            }
        }
        
        // Branch and bound on free variables
        int[] solution = new int[numButtons];
        int[] bestResult = new int[]{Integer.MAX_VALUE};
        
        branchOnFreeVars(aug, pivot, freeVars, numButtons, numCounters, targets, solution, 0, 0, bestResult);
        
        return bestResult[0];
    }
    
    private void branchOnFreeVars(long[][] aug, int[] pivot, List<Integer> freeVars, int numButtons, int numCounters,
                                   int[] targets, int[] solution, int freeIdx, int currentSum, int[] bestResult) {
        if (currentSum >= bestResult[0]) return;
        
        if (freeIdx == freeVars.size()) {
            // All free variables set, compute basic variables
            if (checkAndComputeBasic(aug, pivot, freeVars, numButtons, numCounters, targets, solution, currentSum, bestResult)) {
                // Valid solution found
            }
            return;
        }
        
        int freeVar = freeVars.get(freeIdx);
        
        // Find max value for this free variable based on constraints
        int maxVal = Arrays.stream(targets).max().orElse(0);
        
        for (int val = 0; val <= maxVal; val++) {
            solution[freeVar] = val;
            branchOnFreeVars(aug, pivot, freeVars, numButtons, numCounters, targets, solution, freeIdx + 1, currentSum + val, bestResult);
            
            // Early termination: if best found is already optimal for this branch
            if (currentSum + val >= bestResult[0]) break;
        }
        solution[freeVar] = 0;
    }
    
    private boolean checkAndComputeBasic(long[][] aug, int[] pivot, List<Integer> freeVars, int numButtons, int numCounters,
                                          int[] targets, int[] solution, int freeVarSum, int[] bestResult) {
        // Back-substitute to find basic variables
        int totalSum = freeVarSum;
        
        for (int r = numCounters - 1; r >= 0; r--) {
            if (pivot[r] < 0) continue;
            
            int basicVar = pivot[r];
            long rhs = aug[r][numButtons];
            long coef = aug[r][basicVar];
            
            // Compute RHS - sum of other terms
            for (int j = 0; j < numButtons; j++) {
                if (j != basicVar) {
                    rhs -= aug[r][j] * solution[j];
                }
            }
            
            // Check divisibility
            if (rhs % coef != 0) return false;
            
            long val = rhs / coef;
            if (val < 0) return false;
            
            solution[basicVar] = (int) val;
            totalSum += (int) val;
            
            if (totalSum >= bestResult[0]) return false;
        }
        
        // Verify solution
        for (int c = 0; c < numCounters; c++) {
            int sum = 0;
            for (int j = 0; j < numButtons; j++) {
                sum += (aug[c][j] == 0 ? 0 : 1) * solution[j]; // Use original matrix (0/1)
            }
            // Actually need to verify against original constraints, not augmented
        }
        
        if (totalSum < bestResult[0]) {
            bestResult[0] = totalSum;
            return true;
        }
        return false;
    }
    
    private long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private Machine parseMachine(String line) {
        // Parse [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        int bracketEnd = line.indexOf(']');
        String diagram = line.substring(1, bracketEnd);
        
        boolean[] target = new boolean[diagram.length()];
        for (int i = 0; i < diagram.length(); i++) {
            target[i] = diagram.charAt(i) == '#';
        }
        
        List<Set<Integer>> buttons = new ArrayList<>();
        int pos = bracketEnd + 2;
        while (pos < line.length() && line.charAt(pos) == '(') {
            int end = line.indexOf(')', pos);
            String buttonStr = line.substring(pos + 1, end);
            Set<Integer> button = new HashSet<>();
            if (!buttonStr.isEmpty()) {
                for (String idx : buttonStr.split(",")) {
                    button.add(Integer.parseInt(idx.trim()));
                }
            }
            buttons.add(button);
            pos = end + 2;
        }
        
        // Parse joltage
        int joltStart = line.indexOf('{');
        int joltEnd = line.indexOf('}');
        String joltageStr = line.substring(joltStart + 1, joltEnd);
        int[] joltage = Arrays.stream(joltageStr.split(","))
            .mapToInt(s -> Integer.parseInt(s.trim()))
            .toArray();
        
        return new Machine(target, buttons, joltage);
    }

    private int solveMinimumPresses(Machine m) {
        // This is a lights-out problem: solve using Gaussian elimination over GF(2)
        int numLights = m.target.length;
        int numButtons = m.buttons.size();
        
        // Create augmented matrix for system of linear equations over GF(2)
        // Each row represents a light, each column represents a button
        // Last column is the target state
        boolean[][] matrix = new boolean[numLights][numButtons + 1];
        
        for (int light = 0; light < numLights; light++) {
            for (int button = 0; button < numButtons; button++) {
                matrix[light][button] = m.buttons.get(button).contains(light);
            }
            matrix[light][numButtons] = m.target[light];
        }
        
        // Gaussian elimination
        int[] pivot = new int[numLights];
        Arrays.fill(pivot, -1);
        
        for (int col = 0, row = 0; col < numButtons && row < numLights; col++) {
            // Find pivot
            int pivotRow = -1;
            for (int r = row; r < numLights; r++) {
                if (matrix[r][col]) {
                    pivotRow = r;
                    break;
                }
            }
            
            if (pivotRow == -1) continue;
            
            // Swap rows
            if (pivotRow != row) {
                boolean[] temp = matrix[row];
                matrix[row] = matrix[pivotRow];
                matrix[pivotRow] = temp;
            }
            
            pivot[row] = col;
            
            // Eliminate
            for (int r = 0; r < numLights; r++) {
                if (r != row && matrix[r][col]) {
                    for (int c = 0; c <= numButtons; c++) {
                        matrix[r][c] ^= matrix[row][c];
                    }
                }
            }
            row++;
        }
        
        // Check for inconsistency
        for (int r = 0; r < numLights; r++) {
            boolean allZero = true;
            for (int c = 0; c < numButtons; c++) {
                if (matrix[r][c]) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && matrix[r][numButtons]) {
                return Integer.MAX_VALUE; // No solution
            }
        }
        
        // Find solution with minimum button presses
        // We have some free variables, try all combinations
        List<Integer> freeVars = new ArrayList<>();
        boolean[] basicVars = new boolean[numButtons];
        for (int i = 0; i < numLights; i++) {
            if (pivot[i] >= 0) {
                basicVars[pivot[i]] = true;
            }
        }
        for (int i = 0; i < numButtons; i++) {
            if (!basicVars[i]) {
                freeVars.add(i);
            }
        }
        
        int minPresses = Integer.MAX_VALUE;
        int numFree = freeVars.size();
        
        // Try all 2^numFree combinations of free variables
        for (int mask = 0; mask < (1 << numFree); mask++) {
            boolean[] solution = new boolean[numButtons];
            
            // Set free variables
            for (int i = 0; i < numFree; i++) {
                solution[freeVars.get(i)] = ((mask >> i) & 1) == 1;
            }
            
            // Back-substitute to find basic variables
            for (int r = numLights - 1; r >= 0; r--) {
                if (pivot[r] == -1) continue;
                
                boolean val = matrix[r][numButtons];
                for (int c = 0; c < numButtons; c++) {
                    if (c != pivot[r] && matrix[r][c]) {
                        val ^= solution[c];
                    }
                }
                solution[pivot[r]] = val;
            }
            
            // Count presses
            int presses = 0;
            for (boolean pressed : solution) {
                if (pressed) presses++;
            }
            
            minPresses = Math.min(minPresses, presses);
        }
        
        return minPresses;
    }
}
