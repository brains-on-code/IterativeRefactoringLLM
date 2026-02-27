package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class BmpWriter {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;
    private static final int HEADER_SIZE = INFO_HEADER_SIZE;

    private static final int BITS_PER_PIXEL = 24;
    private static final int PLANES = 1;
    private static final int COMPRESSION_NONE = 0;

    private static final byte[] SIGNATURE = {'B', 'M'};
    private static final int DATA_OFFSET = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    private static final int DEFAULT_IMAGE_SIZE = 0x030000;

    private int fileSize;
    private int imageSize = DEFAULT_IMAGE_SIZE;
    private int width;
    private int height;

    private int[] pixels;
    private ByteBuffer buffer;
    private OutputStream outputStream;

    static void write(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            new BmpWriter().write(bitmap, fos);
        }
    }

    private void write(Bitmap bitmap, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            write(bitmap, fos);
        }
    }

    private void write(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        write(bitmap);
    }

    private void write(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSize = width * 3;
        int rowPadding = calculateRowPadding(rowSize);
        imageSize = (rowSize + rowPadding) * height;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
    }

    private int calculateRowPadding(int rowSize) {
        return (4 - (rowSize % 4)) % 4;
    }

    private void writePixelData() {
        int rowSize = width * 3;
        int rowPadding = calculateRowPadding(rowSize);
        byte[] rgb = new byte[3];

        for (int y = height - 1; y >= 0; y--) {
            int rowStartIndex = y * width;

            for (int x = 0; x < width; x++) {
                int color = pixels[rowStartIndex + x];

                rgb[0] = (byte) (color & 0xFF);         // Blue
                rgb[1] = (byte) ((color >> 8) & 0xFF);  // Green
                rgb[2] = (byte) ((color >> 16) & 0xFF); // Red

                buffer.put(rgb);
            }

            for (int p = 0; p < rowPadding; p++) {
                buffer.put((byte) 0x00);
            }
        }
    }

    private void writeFileHeader() {
        buffer.put(SIGNATURE);
        putIntLE(fileSize);
        putShortLE((short) 0); // reserved1
        putShortLE((short) 0); // reserved2
        putIntLE(DATA_OFFSET);
    }

    private void writeInfoHeader() {
        putIntLE(HEADER_SIZE);
        putIntLE(width);
        putIntLE(height);
        putShortLE((short) PLANES);
        putShortLE((short) BITS_PER_PIXEL);
        putIntLE(COMPRESSION_NONE);
        putIntLE(imageSize);
        putIntLE(0); // xPixelsPerMeter
        putIntLE(0); // yPixelsPerMeter
        putIntLE(0); // colorsUsed
        putIntLE(0); // importantColors
    }

    private void putShortLE(short value) {
        buffer.put((byte) (value & 0xFF));
        buffer.put((byte) ((value >> 8) & 0xFF));
    }

    private void putIntLE(int value) {
        buffer.put((byte) (value & 0xFF));
        buffer.put((byte) ((value >> 8) & 0xFF));
        buffer.put((byte) ((value >> 16) & 0xFF));
        buffer.put((byte) ((value >> 24) & 0xFF));
    }
}