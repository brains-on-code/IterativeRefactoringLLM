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

    // Bitmap file header
    private final byte[] fileType = {'B', 'M'};
    private int fileSize = 0;
    private final int reserved1 = 0;
    private final int reserved2 = 0;
    private final int pixelDataOffset = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // Bitmap info header
    private final int infoHeaderSize = BITMAP_INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private final int colorPlanes = 1;
    private final int bitsPerPixel = 24;
    private final int compression = 0;
    private int imageSize = 0x030000;
    private final int horizontalResolution = 0x0;
    private final int verticalResolution = 0x0;
    private final int colorsUsed = 0;
    private final int importantColors = 0;

    // Bitmap raw data
    private int[] pixelData;

    // File section
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        outputStream = new FileOutputStream(filename);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        convertImage(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmapPixelData();
        outputStream.write(buffer.array());
    }

    private void convertImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixelData = new int[width * height];
        bitmap.getPixels(pixelData, 0, width, 0, 0, width, height);

        int rowPadding = (4 - ((width * 3) % 4)) * height;
        imageSize = (width * height * 3) + rowPadding;
        fileSize = imageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        imageWidth = width;
        imageHeight = height;
    }

    private void writeBitmapPixelData() {
        int totalPixels = (imageWidth * imageHeight) - 1;
        int paddingPerRow = 4 - ((imageWidth * 3) % 4);
        if (paddingPerRow == 4) {
            paddingPerRow = 0;
        }

        int currentRowPixelCount = 1;
        int totalPaddingBytes = 0;
        int rowStartIndex = totalPixels - imageWidth;
        int previousRowStartIndex = rowStartIndex;

        byte[] bgr = new byte[3];

        for (int pixelIndex = 0; pixelIndex < totalPixels; pixelIndex++) {
            int pixel = pixelData[rowStartIndex];

            bgr[0] = (byte) (pixel & 0xFF);         // Blue
            bgr[1] = (byte) ((pixel >> 8) & 0xFF);  // Green
            bgr[2] = (byte) ((pixel >> 16) & 0xFF); // Red

            buffer.put(bgr);

            if (currentRowPixelCount == imageWidth) {
                totalPaddingBytes += paddingPerRow;
                for (int padIndex = 0; padIndex < paddingPerRow; padIndex++) {
                    buffer.put((byte) 0x00);
                }
                currentRowPixelCount = 1;
                rowStartIndex = previousRowStartIndex - imageWidth;
                previousRowStartIndex = rowStartIndex;
            } else {
                currentRowPixelCount++;
            }
            rowStartIndex++;
        }

        fileSize += totalPaddingBytes - paddingPerRow;
        imageSize += totalPaddingBytes - paddingPerRow;
    }

    private void writeBitmapFileHeader() {
        buffer.put(fileType);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(reserved1));
        buffer.put(intToWord(reserved2));
        buffer.put(intToDWord(pixelDataOffset));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(imageWidth));
        buffer.put(intToDWord(imageHeight));
        buffer.put(intToWord(colorPlanes));
        buffer.put(intToWord(bitsPerPixel));
        buffer.put(intToDWord(compression));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(horizontalResolution));
        buffer.put(intToDWord(verticalResolution));
        buffer.put(intToDWord(colorsUsed));
        buffer.put(intToDWord(importantColors));
    }

    private byte[] intToWord(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x00FF);
        return result;
    }

    private byte[] intToDWord(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x000000FF);
        result[2] = (byte) ((value >> 16) & 0x000000FF);
        result[3] = (byte) ((value >> 24) & 0x000000FF);
        return result;
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}