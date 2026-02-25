package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

public final class PerlinNoise {

    private PerlinNoise() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates a 2D Perlin noise map.
     *
     * @param width       width of the noise map
     * @param height      height of the noise map
     * @param octaveCount number of octaves (layers of detail)
     * @param persistence amplitude falloff between octaves (0–1)
     * @param seed        random seed for reproducibility
     * @return 2D array of Perlin noise values in [0, 1]
     */
    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        float[][] baseNoise = new float[width][height];
        float[][] perlinNoise = new float[width][height];
        float[][][] noiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        // Generate base noise
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }

        // Generate noise layers for each octave
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        // Combine layers from highest to lowest octave
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] octaveLayer = noiseLayers[octave];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += octaveLayer[x][y] * amplitude;
                }
            }
        }

        // Normalize by total amplitude to keep values in [0, 1]
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    /**
     * Generates a single octave (layer) of Perlin noise from base noise.
     *
     * @param baseNoise base noise array
     * @param width     width of the noise map
     * @param height    height of the noise map
     * @param octave    octave index (0 = highest frequency)
     * @return 2D array representing one Perlin noise layer
     */
    static float[][] generatePerlinNoiseLayer(float[][] baseNoise, int width, int height, int octave) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octave; // 2^octave
        float frequency = 1f / period;

        for (int x = 0; x < width; x++) {
            int x0 = (x / period) * period;
            int x1 = (x0 + period) % width;
            float horizontalBlend = (x - x0) * frequency;

            for (int y = 0; y < height; y++) {
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;

                float top = interpolate(baseNoise[x0][y0], baseNoise[x1][y0], horizontalBlend);
                float bottom = interpolate(baseNoise[x0][y1], baseNoise[x1][y1], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a     start value
     * @param b     end value
     * @param alpha interpolation factor in [0, 1]
     * @return interpolated value
     */
    static float interpolate(float a, float b, float alpha) {
        return a * (1 - alpha) + alpha * b;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Width (int): ");
            int width = in.nextInt();

            System.out.println("Height (int): ");
            int height = in.nextInt();

            System.out.println("Octave count (int): ");
            int octaveCount = in.nextInt();

            System.out.println("Persistence (float): ");
            float persistence = in.nextFloat();

            System.out.println("Seed (long): ");
            long seed = in.nextLong();

            System.out.println("Charset (String): ");
            String charset = in.next();

            float[][] perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);

            char[] chars = charset.toCharArray();
            int charsetLength = chars.length;
            float step = 1f / charsetLength;

            // Map noise values to characters and print
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    float valueThreshold = step;
                    float noiseValue = perlinNoise[x][y];

                    for (char c : chars) {
                        if (noiseValue <= valueThreshold) {
                            System.out.print(c);
                            break;
                        }
                        valueThreshold += step;
                    }
                }
                System.out.println();
            }
        }
    }
}