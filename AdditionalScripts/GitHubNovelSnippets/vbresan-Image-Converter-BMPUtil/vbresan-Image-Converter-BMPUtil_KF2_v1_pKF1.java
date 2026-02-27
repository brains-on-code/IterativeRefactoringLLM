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

    // BITMAPFILEHEADER fields
    private final byte[] fileType = {'B', 'M'};
    private int fileSize = 0;
    private int fileReserved1 = 0;
    private int fileReserved2 = 0;
    private int fileOffsetToPixelArray = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private int infoHeaderSize = BITMAP_INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int colorPlanes = 1;
    private int bitsPerPixel = 24;
    private int compression = 0;
    private int imageSize = 0x030000;
    private int xPixelsPerMeter = 0x0;
    private int yPixelsPerMeter = 0x0;
    private int colorsUsed = 0;
    private int importantColors = 0;

    private int[] pixelArray;

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
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void convertImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixelArray = new int[width * height];
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        imageSize = (width * height * 3) + rowPaddingBytes;
        fileSize = imageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        imageWidth = width;
        imageHeight = height;
    }

    private void writePixelData() {
        int totalPixels = (imageWidth * imageHeight) - 1;
        int paddingPerRow = 4 - ((imageWidth * 3) % 4);
        if (paddingPerRow == 4) {
            paddingPerRow = 0;
        }

        int rowPixelCount = 1;
        int paddingTotal = 0;
        int rowStartIndex = totalPixels - imageWidth;
        int lastRowStartIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int pixelIndex = 0; pixelIndex < totalPixels; pixelIndex++) {
            int pixelValue = pixelArray[rowStartIndex];

            rgb[0] = (byte) (pixelValue & 0xFF);         // Blue
            rgb[1] = (byte) ((pixelValue >> 8) & 0xFF);  // Green
            rgb[2] = (byte) ((pixelValue >> 16) & 0xFF); // Red

            buffer.put(rgb);

            if (rowPixelCount == imageWidth) {
                paddingTotal += paddingPerRow;
                for (int padIndex = 0; padIndex < paddingPerRow; padIndex++) {
                    buffer.put((byte) 0x00);
                }
                rowPixelCount = 1;
                rowStartIndex = lastRowStartIndex - imageWidth;
                lastRowStartIndex = rowStartIndex;
            } else {
                rowPixelCount++;
            }
            rowStartIndex++;
        }

        fileSize += paddingTotal - paddingPerRow;
        imageSize += paddingTotal - paddingPerRow;
    }

    private void writeBitmapFileHeader() {
        buffer.put(fileType);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(fileReserved1));
        buffer.put(intToWord(fileReserved2));
        buffer.put(intToDWord(fileOffsetToPixelArray));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(imageWidth));
        buffer.put(intToDWord(imageHeight));
        buffer.put(intToWord(colorPlanes));
        buffer.put(intToWord(bitsPerPixel));
        buffer.put(intToDWord(compression));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(xPixelsPerMeter));
        buffer.put(intToDWord(yPixelsPerMeter));
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