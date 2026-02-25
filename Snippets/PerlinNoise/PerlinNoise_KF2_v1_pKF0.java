package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

public final class PerlinNoise {

    private PerlinNoise() {
        // Utility class; prevent instantiation
    }

    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        float[][] baseNoise = generateBaseNoise(width, height, seed);
        float[][] perlinNoise = new float[width][height];
        float[][][] noiseLayers = new float[octaveCount][][];

        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(baseNoise, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] layer = noiseLayers[octave];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += layer[x][y] * amplitude;
                }
            }
        }

        normalizeNoise(perlinNoise, width, height, totalAmplitude);
        return perlinNoise;
    }

    private static float[][] generateBaseNoise(int width, int height, long seed) {
        float[][] baseNoise = new float[width][height];
        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }

        return baseNoise;
    }

    private static void normalizeNoise(float[][] noise, int width, int height, float totalAmplitude) {
        if (totalAmplitude == 0f) {
            return;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                noise[x][y] /= totalAmplitude;
            }
        }
    }

    static float[][] generatePerlinNoiseLayer(float[][] base, int width, int height, int octave) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octave;
        float frequency = 1f / period;

        for (int x = 0; x < width; x++) {
            int x0 = (x / period) * period;
            int x1 = (x0 + period) % width;
            float horizontalBlend = (x - x0) * frequency;

            for (int y = 0; y < height; y++) {
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;

                float top = interpolate(base[x0][y0], base[x1][y0], horizontalBlend);
                float bottom = interpolate(base[x0][y1], base[x1][y1], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    static float interpolate(float start, float end, float alpha) {
        return start * (1 - alpha) + end * alpha;
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
            renderNoise(perlinNoise, width, height, charset);
        }
    }

    private static void renderNoise(float[][] perlinNoise, int width, int height, String charset) {
        char[] chars = charset.toCharArray();
        int length = chars.length;
        if (length == 0) {
            return;
        }

        float step = 1f / length;

        for (int x = 0; x < width; x++) {
            StringBuilder line = new StringBuilder(height);
            for (int y = 0; y < height; y++) {
                float valueThreshold = step;
                float noiseValue = perlinNoise[x][y];

                for (char c : chars) {
                    if (noiseValue <= valueThreshold) {
                        line.append(c);
                        break;
                    }
                    valueThreshold += step;
                }
            }
            System.out.println(line);
        }
    }
}