package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BmpWriter {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;
    private static final int BITS_PER_PIXEL = 24;
    private static final int PLANES = 1;
    private static final int COMPRESSION_NONE = 0;

    // BMP file header
    private static final byte[] SIGNATURE = {'B', 'M'};
    private static final int DATA_OFFSET = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // DIB header (BITMAPINFOHEADER)
    private static final int HEADER_SIZE = INFO_HEADER_SIZE;
    private static final int DEFAULT_IMAGE_SIZE = 0x030000;

    private int fileSize = 0;
    private int imageSize = DEFAULT_IMAGE_SIZE;
    private int width = 0;
    private int height = 0;

    private int[] pixels;
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
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;
        imageSize = (rowSize + rowPadding) * height;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
    }

    private void writePixelData() {
        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;
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
        buffer.put(intTo4Bytes(fileSize));
        buffer.put(shortTo2Bytes((short) 0)); // reserved1
        buffer.put(shortTo2Bytes((short) 0)); // reserved2
        buffer.put(intTo4Bytes(DATA_OFFSET));
    }

    private void writeInfoHeader() {
        buffer.put(intTo4Bytes(HEADER_SIZE));
        buffer.put(intTo4Bytes(width));
        buffer.put(intTo4Bytes(height));
        buffer.put(shortTo2Bytes((short) PLANES));
        buffer.put(shortTo2Bytes((short) BITS_PER_PIXEL));
        buffer.put(intTo4Bytes(COMPRESSION_NONE));
        buffer.put(intTo4Bytes(imageSize));
        buffer.put(intTo4Bytes(0)); // xPixelsPerMeter
        buffer.put(intTo4Bytes(0)); // yPixelsPerMeter
        buffer.put(intTo4Bytes(0)); // colorsUsed
        buffer.put(intTo4Bytes(0)); // importantColors
    }

    private byte[] shortTo2Bytes(short value) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        return bytes;
    }

    private byte[] intTo4Bytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        bytes[2] = (byte) ((value >> 16) & 0xFF);
        bytes[3] = (byte) ((value >> 24) & 0xFF);
        return bytes;
    }

    static void write(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            new BmpWriter().write(bitmap, fos);
        }
    }
}