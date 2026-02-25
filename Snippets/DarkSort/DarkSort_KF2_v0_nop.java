package com.thealgorithms.sorts;


class DarkSort {


    public Integer[] sort(Integer[] unsorted) {
        if (unsorted == null || unsorted.length <= 1) {
            return unsorted;
        }

        int max = findMax(unsorted);

        int[] temp = new int[max + 1];

        for (int value : unsorted) {
            temp[value]++;
        }

        int index = 0;
        for (int i = 0; i < temp.length; i++) {
            while (temp[i] > 0) {
                unsorted[index++] = i;
                temp[i]--;
            }
        }

        return unsorted;
    }


    private int findMax(Integer[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
