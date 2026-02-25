package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * Utility class for generating and printing 2D fractal noise.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates a 2D fractal noise map using multiple octaves of interpolated
     * random noise.
     *
     * @param width       width of the noise map
     * @param height      height of the noise map
     * @param octaveCount number of octaves
     * @param persistence amplitude multiplier between octaves
     * @param seed        random seed
     * @return 2D array of noise values in [0, 1]
     */
    static float[][] method1(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] baseNoise = new float[width][height];
        final float[][] resultNoise = new float[width][height];
        final float[][][] octaveNoise = new float[octaveCount][][];

        Random random = new Random(seed);

        // Generate base white noise
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }

        // Generate smooth noise for each octave
        for (int octave = 0; octave < octaveCount; octave++) {
            octaveNoise[octave] = method2(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        // Blend octaves together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    resultNoise[x][y] += octaveNoise[octave][x][y] * amplitude;
                }
            }
        }

        // Normalize result to [0, 1]
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resultNoise[x][y] /= totalAmplitude;
            }
        }

        return resultNoise;
    }

    /**
     * Generates smooth noise for a given octave from base noise.
     *
     * @param baseNoise base white noise
     * @param width     width of the noise map
     * @param height    height of the noise map
     * @param octave    octave index (0-based)
     * @return 2D array of smooth noise
     */
    static float[][] method2(float[][] baseNoise, int width, int height, int octave) {
        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; // 2^octave
        float sampleFrequency = 1f / samplePeriod;

        for (int x = 0; x < width; x++) {
            int sampleX0 = (x / samplePeriod) * samplePeriod;
            int sampleX1 = (sampleX0 + samplePeriod) % width;
            float horizontalBlend = (x - sampleX0) * sampleFrequency;

            for (int y = 0; y < height; y++) {
                int sampleY0 = (y / samplePeriod) * samplePeriod;
                int sampleY1 = (sampleY0 + samplePeriod) % height;
                float verticalBlend = (y - sampleY0) * sampleFrequency;

                float top = method3(baseNoise[sampleX0][sampleY0], baseNoise[sampleX1][sampleY0], horizontalBlend);
                float bottom = method3(baseNoise[sampleX0][sampleY1], baseNoise[sampleX1][sampleY1], horizontalBlend);

                smoothNoise[x][y] = method3(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a   start value
     * @param b   end value
     * @param t   interpolation factor in [0, 1]
     * @return interpolated value
     */
    static float method3(float a, float b, float t) {
        return a * (1 - t) + t * b;
    }

    /**
     * CLI entry point: generates a noise map and prints it using a character set
     * as a gradient.
     *
     * @param args ignored
     */
    public static void method4(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int width;
        final int height;
        final int octaveCount;
        final float persistence;
        final long seed;
        final String charset;
        final float[][] noise;

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

        noise = method1(width, height, octaveCount, persistence, seed);
        final char[] chars = charset.toCharArray();
        final int charCount = chars.length;
        final float step = 1f / charCount;

        // Map noise values to characters
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float threshold = step;
                float value = noise[x][y];

                for (char c : chars) {
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