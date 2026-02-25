package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class CRCAlgorithm {

    private int correctMess;

    private int wrongMess;

    private int wrongMessCaught;

    private int wrongMessNotCaught;

    private int messSize;

    private double ber;

    private boolean messageChanged;

    private ArrayList<Integer> message;

    private ArrayList<Integer> p;

    private Random randomGenerator;


    public CRCAlgorithm(String str, int size, double ber) {
        messageChanged = false;
        message = new ArrayList<>();
        messSize = size;
        p = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            p.add(Character.getNumericValue(str.charAt(i)));
        }
        randomGenerator = new Random();
        correctMess = 0;
        wrongMess = 0;
        wrongMessCaught = 0;
        wrongMessNotCaught = 0;
        this.ber = ber;
    }


    public int getWrongMess() {
        return wrongMess;
    }


    public int getWrongMessCaught() {
        return wrongMessCaught;
    }


    public int getWrongMessNotCaught() {
        return wrongMessNotCaught;
    }


    public int getCorrectMess() {
        return correctMess;
    }


    public void refactor() {
        messageChanged = false;
        message = new ArrayList<>();
    }


    public void generateRandomMess() {
        for (int i = 0; i < messSize; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 2);
            message.add(x);
        }
    }


    public void divideMessageWithP(boolean check) {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> k = (ArrayList<Integer>) message.clone();
        if (!check) {
            for (int i = 0; i < p.size() - 1; i++) {
                k.add(0);
            }
        }
        while (!k.isEmpty()) {
            while (x.size() < p.size() && !k.isEmpty()) {
                x.add(k.get(0));
                k.remove(0);
            }
            if (x.size() == p.size()) {
                for (int i = 0; i < p.size(); i++) {
                    if (x.get(i) == p.get(i)) {
                        x.set(i, 0);
                    } else {
                        x.set(i, 1);
                    }
                }
                for (int i = 0; i < x.size() && x.get(i) != 1; i++) {
                    x.remove(0);
                }
            }
        }
        ArrayList<Integer> dividedMessage = (ArrayList<Integer>) x.clone();
        if (!check) {
            message.addAll(dividedMessage);
        } else {
            if (dividedMessage.contains(1) && messageChanged) {
                wrongMessCaught++;
            } else if (!dividedMessage.contains(1) && messageChanged) {
                wrongMessNotCaught++;
            } else if (!messageChanged) {
                correctMess++;
            }
        }
    }


    public void changeMess() {
        for (int y : message) {
            double x = randomGenerator.nextDouble();
            while (x < 0.0000 || x > 1.00000) {
                x = randomGenerator.nextDouble();
            }
            if (x < ber) {
                messageChanged = true;
                if (y == 1) {
                    message.set(message.indexOf(y), 0);
                } else {
                    message.set(message.indexOf(y), 1);
                }
            }
        }
        if (messageChanged) {
            wrongMess++;
        }
    }
}
