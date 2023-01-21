package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * A circular list of fixed length where all operations are O(1)
 */
public class CircularList {
  public final List<Node> values;
  private Node current;

  public CircularList(long[] elements) {
    this(elements, elements.length);
  }

  public CircularList(long[] elements, int maxSize) {
    this.values = new ArrayList<>(maxSize);
    Node prev = null;
    Node first = null;
    for (long element : elements) {
      Node n = new Node(element);
      n.prev = prev;
      values.add(n);
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

  public void insertAfter(Node insertThis, Node afterThis) {
    if (insertThis == afterThis || insertThis.prev == afterThis) return;
    if (insertThis.next == null || insertThis.prev == null) { // insert new
      insertThis.prev = afterThis;
      insertThis.next = afterThis.next;
      values.add(insertThis);
    } else { // move existing
      insertThis.prev.next = insertThis.next;
      insertThis.next.prev = insertThis.prev;
      insertThis.next = afterThis.next;
      insertThis.prev = afterThis;
    }
    afterThis.next.prev = insertThis;
    afterThis.next = insertThis;
  }

  public int size() {
    return values.size();
  }

  public long[] toArray() {
    return next(size());
  }

  public void setCurrent(int value) {
    current = values.get(value);
  }

  public void setCurrent(Node n) {
    current = n;
  }

  public Node move(Node n, long howMuch) {
    return n.move(howMuch, size());
  }

  public Node remove(Node node) {
    values.remove(node);
    node.prev.next = node.next;
    node.next.prev = node.prev;
    return node;
  }

  public static class Node {
    public final long value;
    public Node prev;
    public Node next;

    public Node(long value) {
      this.value = value;
    }

    public Node move(long howMuch, int size) {
      Node n = this;
      int movesNeeded = Math.floorMod(howMuch, size - 1);
      for (long i = 0; i < abs(movesNeeded); i++) {
        if (movesNeeded > 0) {
          n = n.next;
        } else {
          n = n.prev;
        }
      }
      return n;
    }

    public Node move(int howMuch) {
      Node n = this;
      for (long i = 0; i < abs(howMuch); i++) {
        if (howMuch > 0) {
          n = n.next;
        } else {
          n = n.prev;
        }
      }
      return n;
    }
  }
}
