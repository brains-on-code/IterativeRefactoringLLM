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

    static float[][] generateNoiseMap(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] baseNoise = new float[width][height];
        final float[][] finalNoise = new float[width][height];
        final float[][][] smoothNoise = new float[octaveCount][][];

        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }

        for (int octave = 0; octave < octaveCount; octave++) {
            smoothNoise[octave] = generateSmoothNoise(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    finalNoise[x][y] += smoothNoise[octave][x][y] * amplitude;
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                finalNoise[x][y] /= totalAmplitude;
            }
        }

        return finalNoise;
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

                float top = interpolate(baseNoise[sampleX0][sampleY0], baseNoise[sampleX1][sampleY0], horizontalBlend);
                float bottom = interpolate(baseNoise[sampleX0][sampleY1], baseNoise[sampleX1][sampleY1], horizontalBlend);

                smoothNoise[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    static float interpolate(float a, float b, float blend) {
        return a * (1 - blend) + blend * b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int width;
        final int height;
        final int octaveCount;
        final float persistence;
        final long seed;
        final String charset;
        final float[][] noiseMap;

        System.out.println("Width (int): ");
        width = scanner.nextInt();

        System.out.println("Height (int): ");
        height = scanner.nextInt();

        System.out.println("Octave count (int): ");
        octaveCount = scanner.nextInt();

        System.out.println("Persistence (float): ");
        persistence = scanner.nextFloat();

        System.out.println("Seed (long): ");
        seed = scanner.nextLong();

        System.out.println("Charset (String): ");
        charset = scanner.next();

        noiseMap = generateNoiseMap(width, height, octaveCount, persistence, seed);
        final char[] charsetArray = charset.toCharArray();
        final int charsetLength = charsetArray.length;
        final float step = 1f / charsetLength;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float threshold = step;
                float value = noiseMap[x][y];

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

        scanner.close();
    }
}