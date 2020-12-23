package com.sbaars.adventofcode.common;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.IntStream.range;

/**
 * A circular linked list of fixed length where all operations are O(1)
 */
public class CircularLinkedList {
    public class Node{
        public int value;
        public Node prev;
        public Node next;
    }

    private Node current;
    private final Map<Integer, Node> valueMap = new HashMap<>();

    public CircularLinkedList(int[] elements){
        Node prev = null;
        Node first = null;
        for (int element : elements) {
            Node n = new Node();
            n.value = element;
            n.prev = prev;
            valueMap.put(element, n);
            if (prev != null) prev.next = n;
            else first = n;
            prev = n;
        }
        prev.next = first;
        first.prev = prev;
        this.current = first;
    }

    public int current(){
        return current.value;
    }

    public void next(){
        current = current.next;
    }

    public int[] next(int n){
        int[] arr = new int[n];
        Node node = current;
        for(int i = 0; i<n; i++){
            node = node.next;
            arr[i] = node.value;
        }
        return arr;
    }

    public Node currentNode(){
        return current;
    }

    public void insertAfter(Node s1, Node s2, int dest){
        Node d = valueMap.get(dest);
        Node oldNext = d.next;
        s1.prev.next = s2.next;
        s2.next.prev = s1.prev;
        d.next = s1;
        s2.next = oldNext;
        oldNext.prev = s2;
        s1.prev = d;
    }

    public int size(){
        return valueMap.size();
    }

    public int[] toArray(){
        return next(size());
    }

    public void setCurrent(int value){
        current = valueMap.get(value);
    }
}
