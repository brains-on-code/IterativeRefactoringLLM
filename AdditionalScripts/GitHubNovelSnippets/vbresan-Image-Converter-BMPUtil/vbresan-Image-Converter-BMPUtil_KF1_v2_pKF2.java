package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Utility for writing a {@link Bitmap} as a 24-bit BMP image.
 */
class BmpWriter {

    // BMP header sizes (in bytes)
    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    // BMP signature ("BM")
    private static final byte[] BMP_SIGNATURE = {'B', 'M'};

    // File header fields
    private int fileSize;
    private int reserved1;
    private int reserved2;
    private final int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // Info header fields
    private final int infoHeaderSize = INFO_HEADER_SIZE;
    private int width;
    private int height;
    private final int planes = 1;
    private final int bitsPerPixel = 24;
    private final int compression = 0;
    private int imageSize = 0x030000;
    private final int xPixelsPerMeter = 0;
    private final int yPixelsPerMeter = 0;
    private final int colorsUsed = 0;
    private final int importantColors = 0;

    // Pixel data
    private int[] pixels;

    // Output buffer and stream
    private ByteBuffer buffer;
    private OutputStream outputStream;

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
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        pixels = new int[bmpWidth * bmpHeight];
        bitmap.getPixels(pixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        int rowSize = bmpWidth * 3; // 3 bytes per pixel (B, G, R)
        int rowPadding = (4 - (rowSize % 4)) % 4;
        imageSize = (rowSize + rowPadding) * bmpHeight;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        width = bmpWidth;
        height = bmpHeight;
    }

    private void writePixelData() {
        int lastPixelIndex = (width * height) - 1;
        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;

        int pixelsInRow = 1;
        int totalPaddingWritten = 0;
        int rowStartIndex = lastPixelIndex - width;
        int currentIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixels[currentIndex];

            rgb[0] = (byte) (color & 0xFF);         // Blue
            rgb[1] = (byte) ((color >> 8) & 0xFF);  // Green
            rgb[2] = (byte) ((color >> 16) & 0xFF); // Red
            buffer.put(rgb);

            if (pixelsInRow == width) {
                totalPaddingWritten += rowPadding;
                for (int p = 0; p < rowPadding; p++) {
                    buffer.put((byte) 0x00);
                }
                pixelsInRow = 1;
                rowStartIndex -= width;
                currentIndex = rowStartIndex;
            } else {
                pixelsInRow++;
                currentIndex++;
            }
        }

        fileSize += totalPaddingWritten - rowPadding;
        imageSize += totalPaddingWritten - rowPadding;
    }

    private void writeFileHeader() {
        buffer.put(BMP_SIGNATURE);
        buffer.put(intTo4Bytes(fileSize));
        buffer.put(intTo2Bytes(reserved1));
        buffer.put(intTo2Bytes(reserved2));
        buffer.put(intTo4Bytes(pixelDataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intTo4Bytes(infoHeaderSize));
        buffer.put(intTo4Bytes(width));
        buffer.put(intTo4Bytes(height));
        buffer.put(intTo2Bytes(planes));
        buffer.put(intTo2Bytes(bitsPerPixel));
        buffer.put(intTo4Bytes(compression));
        buffer.put(intTo4Bytes(imageSize));
        buffer.put(intTo4Bytes(xPixelsPerMeter));
        buffer.put(intTo4Bytes(yPixelsPerMeter));
        buffer.put(intTo4Bytes(colorsUsed));
        buffer.put(intTo4Bytes(importantColors));
    }

    private byte[] intTo2Bytes(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF)
        };
    }

    private byte[] intTo4Bytes(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    static void write(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            new BmpWriter().write(bitmap, fos);
        }
    }
}