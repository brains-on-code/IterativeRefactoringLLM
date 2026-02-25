package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * Generates and prints a noise-based character map.
 */
public final class Class1 {

    private Class1() {
        // Utility class
    }

    static float[][] generateNoiseMap(
            int width,
            int height,
            int octaveCount,
            float persistence,
            long seed
    ) {
        float[][] baseNoise = generateBaseNoise(width, height, seed);
        float[][] finalNoise = new float[width][height];
        float[][][] smoothNoise = new float[octaveCount][][];

        for (int octave = 0; octave < octaveCount; octave++) {
            smoothNoise[octave] = generateSmoothNoise(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] octaveNoise = smoothNoise[octave];
            for (int x = 0; x < width; x++) {
                float[] finalNoiseColumn = finalNoise[x];
                float[] octaveNoiseColumn = octaveNoise[x];
                for (int y = 0; y < height; y++) {
                    finalNoiseColumn[y] += octaveNoiseColumn[y] * amplitude;
                }
            }
        }

        normalizeNoise(finalNoise, width, height, totalAmplitude);
        return finalNoise;
    }

    private static float[][] generateBaseNoise(int width, int height, long seed) {
        float[][] baseNoise = new float[width][height];
        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            float[] column = baseNoise[x];
            for (int y = 0; y < height; y++) {
                column[y] = random.nextFloat();
            }
        }
        return baseNoise;
    }

    private static void normalizeNoise(float[][] noise, int width, int height, float totalAmplitude) {
        for (int x = 0; x < width; x++) {
            float[] column = noise[x];
            for (int y = 0; y < height; y++) {
                column[y] /= totalAmplitude;
            }
        }
    }

    static float[][] generateSmoothNoise(float[][] baseNoise, int width, int height, int octave) {
        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave;
        float sampleFrequency = 1f / samplePeriod;

        for (int x = 0; x < width; x++) {
            int sampleX0 = (x / samplePeriod) * samplePeriod;
            int sampleX1 = (sampleX0 + samplePeriod) % width;
            float horizontalBlend = (x - sampleX0) * sampleFrequency;

            for (int y = 0; y < height; y++) {
                int sampleY0 = (y / samplePeriod) * samplePeriod;
                int sampleY1 = (sampleY0 + samplePeriod) % height;
                float verticalBlend = (y - sampleY0) * sampleFrequency;

                float top = interpolate(
                        baseNoise[sampleX0][sampleY0],
                        baseNoise[sampleX1][sampleY0],
                        horizontalBlend
                );
                float bottom = interpolate(
                        baseNoise[sampleX0][sampleY1],
                        baseNoise[sampleX1][sampleY1],
                        horizontalBlend
                );

                smoothNoise[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    static float interpolate(float a, float b, float blend) {
        return a * (1 - blend) + blend * b;
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    private static float readFloat(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextFloat();
    }

    private static long readLong(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLong();
    }

    private static String readString(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.next();
    }

    private static void printNoiseMap(float[][] noiseMap, int width, int height, String charset) {
        char[] charsetArray = charset.toCharArray();
        int charsetLength = charsetArray.length;
        float step = 1f / charsetLength;

        for (int x = 0; x < width; x++) {
            float[] column = noiseMap[x];
            for (int y = 0; y < height; y++) {
                float value = column[y];
                float threshold = step;

                for (char c : charsetArray) {
                    if (value <= threshold) {
                        System.out.print(c);
                        break;
                    }
                    threshold += step;
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int width = readInt(scanner, "Width (int): ");
            int height = readInt(scanner, "Height (int): ");
            int octaveCount = readInt(scanner, "Octave count (int): ");
            float persistence = readFloat(scanner, "Persistence (float): ");
            long seed = readLong(scanner, "Seed (long): ");
            String charset = readString(scanner, "Charset (String): ");

            float[][] noiseMap = generateNoiseMap(width, height, octaveCount, persistence, seed);
            printNoiseMap(noiseMap, width, height, charset);
        }
    }
}