package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    // BITMAPFILEHEADER fields
    private final byte[] bmpSignature = {'B', 'M'};
    private int bmpFileSize = 0;
    private int reservedField1 = 0;
    private int reservedField2 = 0;
    private int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private int infoHeaderSize = INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int colorPlanes = 1;
    private int bitsPerPixel = 24;
    private int compressionMethod = 0;
    private int pixelDataSize = 0x030000;
    private int horizontalResolution = 0x0;
    private int verticalResolution = 0x0;
    private int totalColors = 0;
    private int importantColors = 0;

    private int[] pixelArray;

    private ByteBuffer byteBuffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(byteBuffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        pixelArray = new int[bitmapWidth * bitmapHeight];
        bitmap.getPixels(pixelArray, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);

        int rowPaddingBytes = (4 - ((bitmapWidth * 3) % 4)) * bitmapHeight;
        pixelDataSize = (bitmapWidth * bitmapHeight * 3) + rowPaddingBytes;
        bmpFileSize = pixelDataSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        byteBuffer = ByteBuffer.allocate(bmpFileSize);
        imageWidth = bitmapWidth;
        imageHeight = bitmapHeight;
    }

    private void writePixelData() {
        int lastPixelIndex = (imageWidth * imageHeight) - 1;
        int paddingBytesPerRow = 4 - ((imageWidth * 3) % 4);
        if (paddingBytesPerRow == 4) {
            paddingBytesPerRow = 0;
        }

        int pixelsWrittenInRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - imageWidth;
        int previousRowStartIndex = currentRowStartIndex;

        byte[] bgr = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int pixel = pixelArray[currentRowStartIndex];

            bgr[0] = (byte) (pixel & 0xFF);         // Blue
            bgr[1] = (byte) ((pixel >> 8) & 0xFF);  // Green
            bgr[2] = (byte) ((pixel >> 16) & 0xFF); // Red

            byteBuffer.put(bgr);

            if (pixelsWrittenInRow == imageWidth) {
                totalPaddingBytes += paddingBytesPerRow;
                for (int padIndex = 0; padIndex < paddingBytesPerRow; padIndex++) {
                    byteBuffer.put((byte) 0x00);
                }
                pixelsWrittenInRow = 1;
                currentRowStartIndex = previousRowStartIndex - imageWidth;
                previousRowStartIndex = currentRowStartIndex;
            } else {
                pixelsWrittenInRow++;
            }
            currentRowStartIndex++;
        }

        bmpFileSize += totalPaddingBytes - paddingBytesPerRow;
        pixelDataSize += totalPaddingBytes - paddingBytesPerRow;
    }

    private void writeFileHeader() {
        byteBuffer.put(bmpSignature);
        byteBuffer.put(intToDWord(bmpFileSize));
        byteBuffer.put(intToWord(reservedField1));
        byteBuffer.put(intToWord(reservedField2));
        byteBuffer.put(intToDWord(pixelDataOffset));
    }

    private void writeInfoHeader() {
        byteBuffer.put(intToDWord(infoHeaderSize));
        byteBuffer.put(intToDWord(imageWidth));
        byteBuffer.put(intToDWord(imageHeight));
        byteBuffer.put(intToWord(colorPlanes));
        byteBuffer.put(intToWord(bitsPerPixel));
        byteBuffer.put(intToDWord(compressionMethod));
        byteBuffer.put(intToDWord(pixelDataSize));
        byteBuffer.put(intToDWord(horizontalResolution));
        byteBuffer.put(intToDWord(verticalResolution));
        byteBuffer.put(intToDWord(totalColors));
        byteBuffer.put(intToDWord(importantColors));
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