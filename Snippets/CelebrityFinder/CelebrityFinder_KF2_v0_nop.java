package com.thealgorithms.stacks;

import java.util.Stack;


public final class CelebrityFinder {
    private CelebrityFinder() {
    }


    public static int findCelebrity(int[][] party) {

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < party.length; i++) {
            stack.push(i);
        }

        while (stack.size() > 1) {
            int person1 = stack.pop();
            int person2 = stack.pop();

            if (party[person1][person2] == 1) {
                stack.push(person2);
            } else {
                stack.push(person1);
            }
        }

        int candidate = stack.pop();
        for (int i = 0; i < party.length; i++) {
            if (i != candidate && (party[candidate][i] == 1 || party[i][candidate] == 0)) {
                return -1;
            }
        }
        return candidate;
    }
}
