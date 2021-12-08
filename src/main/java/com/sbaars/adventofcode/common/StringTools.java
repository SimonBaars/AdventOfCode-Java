package com.sbaars.adventofcode.common;

import java.util.stream.Collectors;

public class StringTools {
  public static String removeAll(String str, String remove) {
    var arr = new SmartArray(str.chars());
    remove.chars().forEach(arr::removeElement);
    return arr.stream().mapToObj(Character::toString).collect(Collectors.joining());
  }
}
