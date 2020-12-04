package com.sbaars.adventofcode.year20.days;

import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.Value;

public class Day4 extends Day2020 implements ReadsFormattedString {
    public static void main(String[] args)  {
        new Day4().printParts();
    }

    public Day4(){super(4);}

    @Override
    public Object part1()  {
        String[][] s = Arrays.stream(day().split("\n\n")).map(str -> str.replace("\n", " ")).map(str -> str.split(" ")).toArray(String[][]::new);
        String[] expected = {"byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid"};
        int valid = 0;
        for(String[] pass : s){
            List<String> mustSee = new ArrayList<>(Arrays.asList(expected));
            for(String str : pass){
                for(int i = 0; i<mustSee.size(); i++){
                    if(str.startsWith(mustSee.get(i))){
                        mustSee.remove(i);
                        break;
                    }
                }
            }
            if(mustSee.isEmpty()){
                valid++;
            }
        }
        return valid;
    }

    @Override
    public Object part2()  {
        String[][] s = Arrays.stream(day().split("\n\n")).map(str -> str.replace("\n", " ")).map(str -> str.split(" ")).toArray(String[][]::new);
        String[] expected = {"byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid"};
        int valid = 0;
        for(String[] pass : s){
            List<String> mustSee = new ArrayList<>(Arrays.asList(expected));
            outerloop:
            for(String str : pass){
                for(int i = 0; i<mustSee.size(); i++){
                    final String key = mustSee.get(i);
                    if(str.startsWith(key)){
                        String validate = str.split(":")[1];
                        switch (key) {
                            case "byr": {
                                Long l = Long.parseLong(validate);
                                if (l < 1920L || l > 2002L) break outerloop;
                                break;
                            }
                            case "iyr": {
                                Long l = Long.parseLong(validate);
                                if (l < 2010 || l > 2020) break outerloop;
                                break;
                            }
                            case "eyr": {
                                Long l = Long.parseLong(validate);
                                if (l < 2020 || l > 2030) break outerloop;
                                break;
                            }
                            case "hgt":
                                if (str.endsWith("in")) {
                                    Long l = Long.parseLong(validate.split("i")[0]);
                                    if (l < 59 || l > 76) break outerloop;
                                } else if (str.endsWith("cm")) {
                                    Long l = Long.parseLong(validate.split("c")[0]);
                                    if (l < 150 || l > 193) break outerloop;
                                } else break outerloop;
                                break;
                            case "hcl":
                                if (validate.startsWith("#")) {
                                    final Pattern pattern = Pattern.compile("^([0-9a-f])*");
                                    if (!pattern.matcher(validate.substring(1)).matches()) break outerloop;
                                } else break outerloop;
                                break;
                            case "ecl":
                                if (!Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(validate))
                                    break outerloop;
                                break;
                            case "pid":
                                if (validate.length() == 9) {
                                    final Pattern pattern = Pattern.compile("^([0-9])*");
                                    if (!pattern.matcher(validate).matches()) break outerloop;
                                } else break outerloop;
                                break;
                        }
                        mustSee.remove(i);
                        break;
                    }
                }
            }
            if(mustSee.isEmpty()){
                valid++;
            }
        }
        return valid;
    }
}
