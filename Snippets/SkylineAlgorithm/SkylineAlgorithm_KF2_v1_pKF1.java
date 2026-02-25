package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

public class SkylineAlgorithm {

    private ArrayList<Point> skylinePoints;

    public SkylineAlgorithm() {
        skylinePoints = new ArrayList<>();
    }

    public ArrayList<Point> getSkylinePoints() {
        return skylinePoints;
    }

    public ArrayList<Point> computeSkyline(ArrayList<Point> points) {
        int size = points.size();
        if (size == 1) {
            return points;
        } else if (size == 2) {
            if (points.get(0).dominates(points.get(1))) {
                points.remove(1);
            } else if (points.get(1).dominates(points.get(0))) {
                points.remove(0);
            }
            return points;
        }

        ArrayList<Point> leftPoints = new ArrayList<>();
        ArrayList<Point> rightPoints = new ArrayList<>();
        int midIndex = points.size() / 2;

        for (int i = 0; i < points.size(); i++) {
            if (i < midIndex) {
                leftPoints.add(points.get(i));
            } else {
                rightPoints.add(points.get(i));
            }
        }

        ArrayList<Point> leftSkyline = computeSkyline(leftPoints);
        ArrayList<Point> rightSkyline = computeSkyline(rightPoints);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public ArrayList<Point> mergeSkylines(ArrayList<Point> leftSkyline, ArrayList<Point> rightSkyline) {
        for (int i = 0; i < leftSkyline.size() - 1; i++) {
            Point current = leftSkyline.get(i);
            Point next = leftSkyline.get(i + 1);
            if (current.x == next.x && current.y > next.y) {
                leftSkyline.remove(i);
                i--;
            }
        }

        int minY = leftSkyline.get(0).y;
        for (int i = 1; i < leftSkyline.size(); i++) {
            int currentY = leftSkyline.get(i).y;
            if (minY > currentY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        for (int i = 0; i < rightSkyline.size(); i++) {
            if (rightSkyline.get(i).y >= minY) {
                rightSkyline.remove(i);
                i--;
            }
        }

        leftSkyline.addAll(rightSkyline);
        return leftSkyline;
    }

    public static class Point {

        private int x;
        private int y;

        public Point(int xCoordinate, int yCoordinate) {
            this.x = xCoordinate;
            this.y = yCoordinate;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean dominates(Point other) {
            return ((this.x < other.x && this.y <= other.y) || (this.x <= other.x && this.y < other.y));
        }
    }

    class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point first, Point second) {
            return Integer.compare(first.x, second.x);
        }
    }
}