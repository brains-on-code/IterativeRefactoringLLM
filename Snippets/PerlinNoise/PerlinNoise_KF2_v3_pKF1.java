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

        for (int octave = 0; octave < octaveCount; octave++) {
            octaveNoiseLayers[octave] = generatePerlinNoiseLayer(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += octaveNoiseLayers[octave][x][y] * amplitude;
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

    static float[][] generatePerlinNoiseLayer(float[][] baseNoise, int width, int height, int octave) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octave;
        float frequency = 1f / period;

        for (int x = 0; x < width; x++) {
            int xStart = (x / period) * period;
            int xEnd = (xStart + period) % width;
            float horizontalBlend = (x - xStart) * frequency;

            for (int y = 0; y < height; y++) {
                int yStart = (y / period) * period;
                int yEnd = (yStart + period) % height;
                float verticalBlend = (y - yStart) * frequency;

                float topInterpolation =
                    interpolate(baseNoise[xStart][yStart], baseNoise[xEnd][yStart], horizontalBlend);
                float bottomInterpolation =
                    interpolate(baseNoise[xStart][yEnd], baseNoise[xEnd][yEnd], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    static float interpolate(float startValue, float endValue, float factor) {
        return startValue * (1 - factor) + factor * endValue;
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
                float threshold = valueStep;
                float noiseValue = perlinNoise[x][y];

                for (char c : charsetChars) {
                    if (noiseValue <= threshold) {
                        System.out.print(c);
                        break;
                    }

                    threshold += valueStep;
                }
            }

            System.out.println();
        }
        scanner.close();
    }
}