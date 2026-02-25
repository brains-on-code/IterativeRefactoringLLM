package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * Perlin noise generator.
 */
public final class PerlinNoiseGenerator {

    private PerlinNoiseGenerator() {}

    static float[][] generateNoiseMap(
            int width, int height, int octaveCount, float persistence, long seed) {

        final float[][] baseNoise = new float[width][height];
        final float[][] perlinNoise = new float[width][height];
        final float[][][] octaveNoiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        // Initialize base noise
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                baseNoise[column][row] = random.nextFloat();
            }
        }

        // Generate smooth noise for each octave
        for (int octaveIndex = 0; octaveIndex < octaveCount; octaveIndex++) {
            octaveNoiseLayers[octaveIndex] =
                    generateSmoothNoise(baseNoise, width, height, octaveIndex);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        // Blend noise together
        for (int octaveIndex = octaveCount - 1; octaveIndex >= 0; octaveIndex--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] currentOctaveNoise = octaveNoiseLayers[octaveIndex];

            for (int column = 0; column < width; column++) {
                for (int row = 0; row < height; row++) {
                    perlinNoise[column][row] += currentOctaveNoise[column][row] * amplitude;
                }
            }
        }

        // Normalize the result
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                perlinNoise[column][row] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    static float[][] generateSmoothNoise(float[][] baseNoise, int width, int height, int octaveIndex) {
        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octaveIndex; // 2^octaveIndex
        float sampleFrequency = 1f / samplePeriod; // 1 / 2^octaveIndex

        for (int column = 0; column < width; column++) {
            int sampleX0 = (column / samplePeriod) * samplePeriod;
            int sampleX1 = (sampleX0 + samplePeriod) % width;
            float horizontalBlend = (column - sampleX0) * sampleFrequency;

            for (int row = 0; row < height; row++) {
                int sampleY0 = (row / samplePeriod) * samplePeriod;
                int sampleY1 = (sampleY0 + samplePeriod) % height;
                float verticalBlend = (row - sampleY0) * sampleFrequency;

                float topInterpolation = interpolate(
                        baseNoise[sampleX0][sampleY0],
                        baseNoise[sampleX1][sampleY0],
                        horizontalBlend);

                float bottomInterpolation = interpolate(
                        baseNoise[sampleX0][sampleY1],
                        baseNoise[sampleX1][sampleY1],
                        horizontalBlend);

                smoothNoise[column][row] = interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return smoothNoise;
    }

    static float interpolate(float startValue, float endValue, float interpolationFactor) {
        return startValue * (1 - interpolationFactor) + interpolationFactor * endValue;
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
        final char[] charsetCharacters = charset.toCharArray();
        final int charsetLength = charsetCharacters.length;
        final float intensityStep = 1f / charsetLength;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                float threshold = intensityStep;
                float noiseValue = noiseMap[column][row];

                for (char character : charsetCharacters) {
                    if (noiseValue <= threshold) {
                        System.out.print(character);
                        break;
                    }
                    threshold += intensityStep;
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}