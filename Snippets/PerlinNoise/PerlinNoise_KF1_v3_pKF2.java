package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * Utility class for generating and printing 2D fractal noise.
 */
public final class FractalNoiseGenerator {

    private FractalNoiseGenerator() {
        // Prevent instantiation
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
    static float[][] generateFractalNoise(
            int width,
            int height,
            int octaveCount,
            float persistence,
            long seed
    ) {
        float[][] baseNoise = new float[width][height];
        float[][] resultNoise = new float[width][height];
        float[][][] octaveNoise = new float[octaveCount][][];

        Random random = new Random(seed);

        fillWithWhiteNoise(baseNoise, random, width, height);
        generateAllOctaves(baseNoise, octaveNoise, width, height, octaveCount);
        blendOctaves(resultNoise, octaveNoise, width, height, octaveCount, persistence);
        normalizeNoise(resultNoise, width, height);

        return resultNoise;
    }

    private static void fillWithWhiteNoise(float[][] baseNoise, Random random, int width, int height) {
        for (int x = 0; x < width; x++) {
            float[] row = baseNoise[x];
            for (int y = 0; y < height; y++) {
                row[y] = random.nextFloat();
            }
        }
    }

    private static void generateAllOctaves(
            float[][] baseNoise,
            float[][][] octaveNoise,
            int width,
            int height,
            int octaveCount
    ) {
        for (int octave = 0; octave < octaveCount; octave++) {
            octaveNoise[octave] = generateSmoothNoise(baseNoise, width, height, octave);
        }
    }

    private static void blendOctaves(
            float[][] resultNoise,
            float[][][] octaveNoise,
            int width,
            int height,
            int octaveCount,
            float persistence
    ) {
        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] currentOctave = octaveNoise[octave];
            for (int x = 0; x < width; x++) {
                float[] resultRow = resultNoise[x];
                float[] octaveRow = currentOctave[x];
                for (int y = 0; y < height; y++) {
                    resultRow[y] += octaveRow[y] * amplitude;
                }
            }
        }

        // Store total amplitude in [0][0] as a temporary place to reuse in normalization
        resultNoise[0][0] /= (1f / totalAmplitude);
    }

    private static void normalizeNoise(float[][] noise, int width, int height) {
        float totalAmplitude = noise[0][0];
        for (int x = 0; x < width; x++) {
            float[] row = noise[x];
            for (int y = 0; y < height; y++) {
                row[y] /= totalAmplitude;
            }
        }
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

                float top = lerp(
                        baseNoise[sampleX0][sampleY0],
                        baseNoise[sampleX1][sampleY0],
                        horizontalBlend
                );
                float bottom = lerp(
                        baseNoise[sampleX0][sampleY1],
                        baseNoise[sampleX1][sampleY1],
                        horizontalBlend
                );

                smoothNoise[x][y] = lerp(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a start value
     * @param b end value
     * @param t interpolation factor in [0, 1]
     * @return interpolated value
     */
    static float lerp(float a, float b, float t) {
        return a * (1 - t) + t * b;
    }

    /**
     * CLI entry point: generates a noise map and prints it using a character set
     * as a gradient.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int width = readInt(scanner, "Width (int): ");
            int height = readInt(scanner, "Height (int): ");
            int octaveCount = readInt(scanner, "Octave count (int): ");
            float persistence = readFloat(scanner, "Persistence (float): ");
            long seed = readLong(scanner, "Seed (long): ");
            String charset = readString(scanner, "Charset (String): ");

            float[][] noise = generateFractalNoise(width, height, octaveCount, persistence, seed);
            printNoiseAsAscii(noise, charset);
        }
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

    /**
     * Prints a 2D noise map using the given character set as a gradient.
     *
     * @param noise   2D noise values in [0, 1]
     * @param charset characters ordered from darkest to brightest
     */
    private static void printNoiseAsAscii(float[][] noise, String charset) {
        int width = noise.length;
        int height = noise[0].length;

        char[] chars = charset.toCharArray();
        int charCount = chars.length;
        float step = 1f / charCount;

        for (int x = 0; x < width; x++) {
            float[] row = noise[x];
            for (int y = 0; y < height; y++) {
                float value = row[y];
                float threshold = step;

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
    }
}