package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;


public class SortedLinkedList {
    private Node head;
    private Node tail;


    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }


    public void insert(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else if (value < head.value) {
            newNode.next = this.head;
            this.head = newNode;
        } else if (value > tail.value) {
            this.tail.next = newNode;
            this.tail = newNode;
        } else {
            Node temp = head;
            while (temp.next != null && temp.next.value < value) {
                temp = temp.next;
            }
            newNode.next = temp.next;
            temp.next = newNode;
            if (newNode.next == null) {
                this.tail = newNode;
            }
        }
    }


    public boolean delete(int value) {
        if (this.head == null) {
            return false;
        } else if (this.head.value == value) {
            if (this.head.next == null) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = this.head.next;
            }
            return true;
        } else {
            Node temp = this.head;
            while (temp.next != null) {
                if (temp.next.value == value) {
                    if (temp.next == this.tail) {
                        this.tail = temp;
                    }
                    temp.next = temp.next.next;
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }
    }


    public boolean search(int value) {
        Node temp = this.head;
        while (temp != null) {
            if (temp.value == value) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }


    public boolean isEmpty() {
        return head == null;
    }


    @Override
    public String toString() {
        if (this.head != null) {
            List<String> elements = new ArrayList<>();
            Node temp = this.head;
            while (temp != null) {
                elements.add(String.valueOf(temp.value));
                temp = temp.next;
            }
            return "[" + String.join(", ", elements) + "]";
        } else {
            return "[]";
        }
    }


    public final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
