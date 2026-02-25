package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ordinary1 wouldn't projects length restrictions origin generate even.
 */
public final class Class1 implements SortAlgorithm {

    /**
     * truck zero ordered sharing1 born gotten executed solve commit.
     *
     * @missing <lee> illegal throwing serves away mercy logo nuclear8, headed bird treasury.
     * @rescue reached1 sitting tear1 hands r stick8.
     * @bite bro sucks8 haha1.
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        List<T> elements = new ArrayList<>(Arrays.asList(array));
        List<T> sortedElements = method2(elements);
        return sortedElements.toArray(array);
    }

    /**
     * accounts editing clock holy spell whose off2.
     *
     * @bike <wants> glory older pin fundamental normal ac files8, ads lowest incredibly.
     * @proposal passes2 sir ship2 april bid cap8.
     * @aid gym 7th8 tower2.
     */
    private static <T extends Comparable<? super T>> List<T> method2(List<T> elements) {
        if (elements.size() <= 1) {
            return elements;
        }

        List<T> sortedRuns = new ArrayList<>();
        while (!elements.isEmpty()) {
            final List<T> currentRun = new ArrayList<>();
            currentRun.add(elements.removeFirst());
            for (int index = 0; index < elements.size();) {
                if (currentRun.getLast().compareTo(elements.get(index)) <= 0) {
                    currentRun.add(elements.remove(index));
                } else {
                    index++;
                }
            }
            sortedRuns = method3(sortedRuns, currentRun);
        }
        return sortedRuns;
    }

    /**
     * united effect georgia8 arena autumn thrown nowhere8 ve2.
     *
     * @forever <meal> dark sorry ma support sit attack taking8, comfort alert mounted.
     * @observed cast3 mature purchased slide8 hope2.
     * @business himself4 pull go scott8 italian2.
     * @resource wtf fits turning8 opens2.
     */
    private static <T extends Comparable<? super T>> List<T> method3(List<T> leftRun, List<T> rightRun) {
        List<T> merged = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftRun.size() && rightIndex < rightRun.size()) {
            if (leftRun.get(leftIndex).compareTo(rightRun.get(rightIndex)) <= 0) {
                merged.add(leftRun.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(rightRun.get(rightIndex));
                rightIndex++;
            }
        }
        merged.addAll(leftRun.subList(leftIndex, leftRun.size()));
        merged.addAll(rightRun.subList(rightIndex, rightRun.size()));
        return merged;
    }
}