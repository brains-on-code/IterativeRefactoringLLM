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
        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                baseNoise[xCoordinate][yCoordinate] = random.nextFloat();
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

            for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
                for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                    perlinNoise[xCoordinate][yCoordinate] +=
                        octaveNoiseLayers[octaveIndex][xCoordinate][yCoordinate] * amplitude;
                }
            }
        }

        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                perlinNoise[xCoordinate][yCoordinate] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    static float[][] generatePerlinNoiseLayer(float[][] baseNoise, int width, int height, int octaveIndex) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octaveIndex;
        float frequency = 1f / period;

        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            int xStart = (xCoordinate / period) * period;
            int xEnd = (xStart + period) % width;
            float horizontalBlend = (xCoordinate - xStart) * frequency;

            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                int yStart = (yCoordinate / period) * period;
                int yEnd = (yStart + period) % height;
                float verticalBlend = (yCoordinate - yStart) * frequency;

                float topInterpolation =
                    interpolate(baseNoise[xStart][yStart], baseNoise[xEnd][yStart], horizontalBlend);
                float bottomInterpolation =
                    interpolate(baseNoise[xStart][yEnd], baseNoise[xEnd][yEnd], horizontalBlend);

                perlinNoiseLayer[xCoordinate][yCoordinate] =
                    interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return perlinNoiseLayer;
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
        final char[] charsetCharacters = charset.toCharArray();
        final int charsetLength = charsetCharacters.length;
        final float valueStep = 1f / charsetLength;

        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                float threshold = valueStep;
                float noiseValue = perlinNoise[xCoordinate][yCoordinate];

                for (char character : charsetCharacters) {
                    if (noiseValue <= threshold) {
                        System.out.print(character);
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