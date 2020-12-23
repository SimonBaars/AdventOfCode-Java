package com.sbaars.adventofcode.common;

import java.util.*;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.IntStream.range;

/**
 * A set of fixed length where all operations are O(1)
 */
public class SmartArray {
    private final int[] order;
    private final int[] elements;
    private final Map<Integer, Integer> elementIndex = new HashMap<>();

    public SmartArray(int[] elements){
        this.elements = elements;
        this.order = range(0, elements.length).toArray();
        for(int i = 0; i<elements.length; i++){
            elementIndex.put(elements[i], order[i]);
        }
    }

    public void move(int oldIndex, int newIndex){
        elementIndex.put(elements[order[oldIndex]], order[newIndex]);
        elementIndex.put(elements[order[newIndex]], order[oldIndex]);
        int old = order[oldIndex];
        order[oldIndex] = order[newIndex];
        order[newIndex] = old;
    }

    public int get(int i){
        return elements[order[i]];
    }

    public int size(){
        return order.length;
    }

    public int indexOf(int element){
        return elementIndex.get(element);
    }

    public int[] toArray(){
        int[] arr = new int[order.length];
        for(int i = 0; i<arr.length; i++){
            arr[i] = elements[order[i]];
        }
        return arr;
    }
}
