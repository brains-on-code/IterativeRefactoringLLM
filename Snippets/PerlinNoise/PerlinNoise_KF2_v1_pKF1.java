package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

public final class PerlinNoise {

    private PerlinNoise() {
    }

    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] baseNoise = new float[width][height];
        final float[][] perlinNoise = new float[width][height];
        final float[][][] octaveNoiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }

        for (int octaveIndex = 0; octaveIndex < octaveCount; octaveIndex++) {
            octaveNoiseLayers[octaveIndex] = generatePerlinNoiseLayer(baseNoise, width, height, octaveIndex);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octaveIndex = octaveCount - 1; octaveIndex >= 0; octaveIndex--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += octaveNoiseLayers[octaveIndex][x][y] * amplitude;
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    static float[][] generatePerlinNoiseLayer(float[][] baseNoise, int width, int height, int octaveIndex) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octaveIndex;
        float frequency = 1f / period;

        for (int x = 0; x < width; x++) {
            int x0 = (x / period) * period;
            int x1 = (x0 + period) % width;
            float horizontalBlend = (x - x0) * frequency;

            for (int y = 0; y < height; y++) {
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;

                float topInterpolation = interpolate(baseNoise[x0][y0], baseNoise[x1][y0], horizontalBlend);
                float bottomInterpolation = interpolate(baseNoise[x0][y1], baseNoise[x1][y1], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return perlinNoiseLayer;
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
        final float[][] perlinNoise;

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

        perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
        final char[] charsetChars = charset.toCharArray();
        final int charsetLength = charsetChars.length;
        final float valueStep = 1f / charsetLength;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float thresholdValue = valueStep;
                float noiseValue = perlinNoise[x][y];

                for (char character : charsetChars) {
                    if (noiseValue <= thresholdValue) {
                        System.out.print(character);
                        break;
                    }

                    thresholdValue += valueStep;
                }
            }

            System.out.println();
        }
        scanner.close();
    }
}