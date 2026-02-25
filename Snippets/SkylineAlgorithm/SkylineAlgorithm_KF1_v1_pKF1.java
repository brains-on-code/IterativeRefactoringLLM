package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

public class Class1 {

    private ArrayList<Class2> points;

    public Class1() {
        points = new ArrayList<>();
    }

    public ArrayList<Class2> getPoints() {
        return points;
    }

    public ArrayList<Class2> mergeDominatedPoints(ArrayList<Class2> inputPoints) {
        int size = inputPoints.size();
        if (size == 1) {
            return inputPoints;
        } else if (size == 2) {
            if (inputPoints.get(0).dominates(inputPoints.get(1))) {
                inputPoints.remove(1);
            } else if (inputPoints.get(1).dominates(inputPoints.get(0))) {
                inputPoints.remove(0);
            }
            return inputPoints;
        }

        ArrayList<Class2> leftHalf = new ArrayList<>();
        ArrayList<Class2> rightHalf = new ArrayList<>();
        for (int index = 0; index < inputPoints.size(); index++) {
            if (index < inputPoints.size() / 2) {
                leftHalf.add(inputPoints.get(index));
            } else {
                rightHalf.add(inputPoints.get(index));
            }
        }
        ArrayList<Class2> mergedLeft = mergeDominatedPoints(leftHalf);
        ArrayList<Class2> mergedRight = mergeDominatedPoints(rightHalf);

        return mergeFrontiers(mergedLeft, mergedRight);
    }

    public ArrayList<Class2> mergeFrontiers(ArrayList<Class2> leftFrontier, ArrayList<Class2> rightFrontier) {
        for (int index = 0; index < leftFrontier.size() - 1; index++) {
            if (leftFrontier.get(index).x == leftFrontier.get(index + 1).x
                    && leftFrontier.get(index).y > leftFrontier.get(index + 1).y) {
                leftFrontier.remove(index);
                index--;
            }
        }

        int minY = leftFrontier.get(0).y;
        for (int index = 1; index < leftFrontier.size(); index++) {
            if (minY > leftFrontier.get(index).y) {
                minY = leftFrontier.get(index).y;
                if (minY == 1) {
                    index = leftFrontier.size();
                }
            }
        }

        for (int index = 0; index < rightFrontier.size(); index++) {
            if (rightFrontier.get(index).y >= minY) {
                rightFrontier.remove(index);
                index--;
            }
        }

        leftFrontier.addAll(rightFrontier);
        return leftFrontier;
    }

    public static class Class2 {

        private int x;
        private int y;

        public Class2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean dominates(Class2 other) {
            return ((this.x < other.x && this.y <= other.y) || (this.x <= other.x && this.y < other.y));
        }
    }

    class Class3 implements Comparator<Class2> {

        @Override
        public int compare(Class2 first, Class2 second) {
            return Integer.compare(first.x, second.x);
        }
    }
}