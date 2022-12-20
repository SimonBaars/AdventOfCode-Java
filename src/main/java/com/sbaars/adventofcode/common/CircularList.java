package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

/**
 * A circular linked list of fixed length where all operations are O(1)
 */
public class CircularList {
  public final List<Node> valueMap = new ArrayList<>();
  private Node current;
  public CircularList(long[] elements) {
    Node prev = null;
    Node first = null;
    for (long element : elements) {
      Node n = new Node();
      n.value = element;
      n.prev = prev;
      valueMap.add(n);
      if (prev != null) prev.next = n;
      else first = n;
      prev = n;
    }
    prev.next = first;
    first.prev = prev;
    this.current = first;
  }

  public long current() {
    return current.value;
  }

  public void next() {
    current = current.next;
  }

  public long[] next(int n) {
    long[] arr = new long[n];
    Node node = current;
    for (int i = 0; i < n; i++) {
      node = node.next;
      arr[i] = node.value;
    }
    return arr;
  }

  public long[] prev(int n) {
    long[] arr = new long[n];
    Node node = current;
    for (int i = 0; i < n; i++) {
      node = node.prev;
      arr[i] = node.value;
    }
    return arr;
  }

  public Node currentNode() {
    return current;
  }

  public void insertAfter(Node s1, Node s2) {
    s1.prev.next = s1.next;
    s1.next.prev = s1.prev;
    s2.next.prev = s1;
    s1.next = s2.next;
    s2.next = s1;
    s1.prev = s2;
  }

  public int size() {
    return valueMap.size();
  }

  public long[] toArray() {
    return next(size());
  }

  public void setCurrent(int value) {
    current = valueMap.get(value);
  }
  public void setCurrent(Node n) {
    current = n;
  }

  public class Node {
    public long value;
    public Node prev;
    public Node next;

    public Node move(long value, int size) {
      Node n = this;
      if(value < 0) value--;
      int movesNeeded = Math.floorMod(value, size);
      for(long i = 0; i<movesNeeded; i++) {
        n = n.next;
      }
      return n;
    }
  }
}
