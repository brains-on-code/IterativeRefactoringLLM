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
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
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

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += currentOctaveNoise[x][y] * amplitude;
                }
            }
        }

        // Normalize the result
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    static float[][] generateSmoothNoise(float[][] baseNoise, int width, int height, int octave) {
        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; // 2^octave
        float sampleFrequency = 1f / samplePeriod; // 1 / 2^octave

        for (int x = 0; x < width; x++) {
            int sampleX0 = (x / samplePeriod) * samplePeriod;
            int sampleX1 = (sampleX0 + samplePeriod) % width;
            float horizontalBlend = (x - sampleX0) * sampleFrequency;

            for (int y = 0; y < height; y++) {
                int sampleY0 = (y / samplePeriod) * samplePeriod;
                int sampleY1 = (sampleY0 + samplePeriod) % height;
                float verticalBlend = (y - sampleY0) * sampleFrequency;

                float topInterpolation = interpolate(
                        baseNoise[sampleX0][sampleY0],
                        baseNoise[sampleX1][sampleY0],
                        horizontalBlend);

                float bottomInterpolation = interpolate(
                        baseNoise[sampleX0][sampleY1],
                        baseNoise[sampleX1][sampleY1],
                        horizontalBlend);

                smoothNoise[x][y] = interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return smoothNoise;
    }

    static float interpolate(float startValue, float endValue, float alpha) {
        return startValue * (1 - alpha) + alpha * endValue;
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

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float threshold = intensityStep;
                float noiseValue = noiseMap[x][y];

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