package com.sbaars.adventofcode.common;

import java.util.ArrayList;
import java.util.List;



public class Tree<T> {
    public enum TreeReversal {CONTINUE, DOWN, UP}
    private final Node<T> rootNode;

    public Tree(T rootNodeData) {
        this.rootNode = new Node<T>(rootNodeData);
    }

    private class Node<T> {
        final T data;
        final List<Node> children;
        final Node parent;

        public Node(T data, List<Node> children, Node parent) {
            this.data = data;
            this.children = children;
            this.parent = parent;
        }

        public Node(T data, List<Node> children) {
            this(data, children, null);
        }

        public Node(T data) {
            this(data, new ArrayList<>());
        }
    }
}
