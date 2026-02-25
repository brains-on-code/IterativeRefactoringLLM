package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

public final class PerlinNoise {

    private PerlinNoise() {
        // Utility class; prevent instantiation
    }

    static float[][] generatePerlinNoise(
            int width,
            int height,
            int octaveCount,
            float persistence,
            long seed
    ) {
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
                float[] perlinColumn = perlinNoise[x];
                float[] layerColumn = layer[x];
                for (int y = 0; y < height; y++) {
                    perlinColumn[y] += layerColumn[y] * amplitude;
                }
            }
        }

        normalizeNoise(perlinNoise, totalAmplitude);
        return perlinNoise;
    }

    private static float[][] generateBaseNoise(int width, int height, long seed) {
        float[][] baseNoise = new float[width][height];
        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            float[] column = baseNoise[x];
            for (int y = 0; y < height; y++) {
                column[y] = random.nextFloat();
            }
        }

        return baseNoise;
    }

    private static void normalizeNoise(float[][] noise, float totalAmplitude) {
        if (totalAmplitude == 0f) {
            return;
        }

        int width = noise.length;
        int height = noise[0].length;

        for (int x = 0; x < width; x++) {
            float[] column = noise[x];
            for (int y = 0; y < height; y++) {
                column[y] /= totalAmplitude;
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
            int width = readInt(in, "Width (int): ");
            int height = readInt(in, "Height (int): ");
            int octaveCount = readInt(in, "Octave count (int): ");
            float persistence = readFloat(in, "Persistence (float): ");
            long seed = readLong(in, "Seed (long): ");
            String charset = readString(in, "Charset (String): ");

            float[][] perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
            renderNoise(perlinNoise, charset);
        }
    }

    private static int readInt(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextInt();
    }

    private static float readFloat(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextFloat();
    }

    private static long readLong(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextLong();
    }

    private static String readString(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.next();
    }

    private static void renderNoise(float[][] perlinNoise, String charset) {
        char[] chars = charset.toCharArray();
        int charsetLength = chars.length;
        if (charsetLength == 0) {
            return;
        }

        int width = perlinNoise.length;
        int height = perlinNoise[0].length;
        float step = 1f / charsetLength;

        for (int x = 0; x < width; x++) {
            StringBuilder line = new StringBuilder(height);
            float[] column = perlinNoise[x];

            for (int y = 0; y < height; y++) {
                float noiseValue = column[y];
                float valueThreshold = step;

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