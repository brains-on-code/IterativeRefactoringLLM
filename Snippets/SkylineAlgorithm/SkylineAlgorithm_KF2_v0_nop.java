package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;


public class SkylineAlgorithm {

    private ArrayList<Point> points;


    public SkylineAlgorithm() {
        points = new ArrayList<>();
    }


    public ArrayList<Point> getPoints() {
        return points;
    }


    public ArrayList<Point> produceSubSkyLines(ArrayList<Point> list) {
        int size = list.size();
        if (size == 1) {
            return list;
        } else if (size == 2) {
            if (list.get(0).dominates(list.get(1))) {
                list.remove(1);
            } else {
                if (list.get(1).dominates(list.get(0))) {
                    list.remove(0);
                }
            }
            return list;
        }

        ArrayList<Point> leftHalf = new ArrayList<>();
        ArrayList<Point> rightHalf = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() / 2) {
                leftHalf.add(list.get(i));
            } else {
                rightHalf.add(list.get(i));
            }
        }
        ArrayList<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        ArrayList<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }


    public ArrayList<Point> produceFinalSkyLine(ArrayList<Point> left, ArrayList<Point> right) {
        for (int i = 0; i < left.size() - 1; i++) {
            if (left.get(i).x == left.get(i + 1).x && left.get(i).y > left.get(i + 1).y) {
                left.remove(i);
                i--;
            }
        }

        int min = left.get(0).y;
        for (int i = 1; i < left.size(); i++) {
            if (min > left.get(i).y) {
                min = left.get(i).y;
                if (min == 1) {
                    i = left.size();
                }
            }
        }

        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).y >= min) {
                right.remove(i);
                i--;
            }
        }

        left.addAll(right);
        return left;
    }

    public static class Point {

        private int x;
        private int y;


        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public int getX() {
            return x;
        }


        public int getY() {
            return y;
        }


        public boolean dominates(Point p1) {
            return ((this.x < p1.x && this.y <= p1.y) || (this.x <= p1.x && this.y < p1.y));
        }
    }


    class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.x, b.x);
        }
    }
}
