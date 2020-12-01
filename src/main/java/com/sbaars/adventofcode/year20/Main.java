package com.sbaars.adventofcode.year20;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year19.Day2019;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException {
        for(int day = 1; day<=1; day++) {
            System.out.println("Day "+day+":");
            Day2019 instance = (Day2019)Class.forName("com.sbaars.adventofcode.year20.days.Day"+day).getDeclaredConstructor().newInstance();
            instance.printParts();
            System.out.println();
        }
    }
}
