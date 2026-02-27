package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;
    private static final int BMP_HEADER_SIZE = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    private static final byte[] BMP_SIGNATURE = {'B', 'M'};

    private static final int PLANES = 1;
    private static final int BIT_COUNT = 24;
    private static final int COMPRESSION_NONE = 0;
    private static final int DEFAULT_IMAGE_SIZE = 0x030000;
    private static final int DEFAULT_PELS_PER_METER = 0;
    private static final int DEFAULT_COLORS_USED = 0;
    private static final int DEFAULT_COLORS_IMPORTANT = 0;

    private int fileSize;
    private int imageSize = DEFAULT_IMAGE_SIZE;
    private int width;
    private int height;

    private int[] pixels;
    private ByteBuffer buffer;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        try (OutputStream out = new FileOutputStream(filename)) {
            saveBitmap(bitmap, out);
        }
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmapPixels();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSize = width * 3;
        int rowPadding = calculateRowPadding(rowSize);
        int totalPadding = rowPadding * height;

        imageSize = (width * height * 3) + totalPadding;
        fileSize = imageSize + BMP_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
    }

    private void writeBitmapPixels() {
        int totalPixels = width * height;
        int lastIndex = totalPixels - 1;

        int rowSize = width * 3;
        int rowPadding = calculateRowPadding(rowSize);

        byte[] rgb = new byte[3];

        int rowPixelCount = 0;
        int paddingBytesWritten = 0;
        int rowStartIndex = lastIndex - width + 1;
        int currentIndex = rowStartIndex;

        for (int j = 0; j < totalPixels; j++) {
            int value = pixels[currentIndex];

            rgb[0] = (byte) (value & 0xFF);
            rgb[1] = (byte) ((value >> 8) & 0xFF);
            rgb[2] = (byte) ((value >> 16) & 0xFF);
            buffer.put(rgb);

            rowPixelCount++;

            if (rowPixelCount == width) {
                writeRowPadding(rowPadding);
                paddingBytesWritten += rowPadding;

                rowPixelCount = 0;
                rowStartIndex -= width;
                currentIndex = rowStartIndex;
            } else {
                currentIndex++;
            }
        }

        int pixelDataSize = (width * height * 3) + paddingBytesWritten;
        fileSize = BMP_HEADER_SIZE + pixelDataSize;
        imageSize = pixelDataSize;
    }

    private void writeBitmapFileHeader() {
        buffer.put(BMP_SIGNATURE);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(0)); // reserved1
        buffer.put(intToWord(0)); // reserved2
        buffer.put(intToDWord(BMP_HEADER_SIZE));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(BITMAP_INFO_HEADER_SIZE));
        buffer.put(intToDWord(width));
        buffer.put(intToDWord(height));
        buffer.put(intToWord(PLANES));
        buffer.put(intToWord(BIT_COUNT));
        buffer.put(intToDWord(COMPRESSION_NONE));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(DEFAULT_PELS_PER_METER));
        buffer.put(intToDWord(DEFAULT_PELS_PER_METER));
        buffer.put(intToDWord(DEFAULT_COLORS_USED));
        buffer.put(intToDWord(DEFAULT_COLORS_IMPORTANT));
    }

    private static int calculateRowPadding(int rowSize) {
        return (4 - (rowSize % 4)) % 4;
    }

    private void writeRowPadding(int rowPadding) {
        for (int i = 0; i < rowPadding; i++) {
            buffer.put((byte) 0x00);
        }
    }

    private static byte[] intToWord(int value) {
        return new byte[]{
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF)
        };
    }

    private static byte[] intToDWord(int value) {
        return new byte[]{
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            new BMPUtil().saveBitmap(bitmap, out);
        }
    }
}