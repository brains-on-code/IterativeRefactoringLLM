package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BmpEncoder {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    private final byte[] bmpSignature = {'B', 'M'};
    private int fileSize = 0;
    private int reserved = 0;
    private int dataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    private int infoHeaderSize = INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int planes = 1;
    private int bitsPerPixel = 24;
    private int compression = 0;
    private int imageSize = 0x030000;
    private int xPixelsPerMeter = 0x0;
    private int yPixelsPerMeter = 0x0;
    private int colorsUsed = 0;
    private int importantColors = 0;

    private int[] pixels;

    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void encode(Bitmap bitmap, String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath);
        encode(bitmap);
        outputStream.close();
    }

    private void encode(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        encode(bitmap);
    }

    private void encode(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowPadding = (4 - ((width * 3) % 4)) * height;
        imageSize = (width * height * 3) + rowPadding;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        imageWidth = width;
        imageHeight = height;
    }

    private void writePixelData() {
        int lastPixelIndex = (imageWidth * imageHeight) - 1;
        int rowPadding = 4 - ((imageWidth * 3) % 4);
        if (rowPadding == 4) {
            rowPadding = 0;
        }

        int pixelsInRow = 1;
        int totalPadding = 0;
        int rowStartIndex = lastPixelIndex - imageWidth;
        int currentIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixels[currentIndex];
            rgb[0] = (byte) (color & 0xFF);
            rgb[1] = (byte) ((color >> 8) & 0xFF);
            rgb[2] = (byte) ((color >> 16) & 0xFF);
            buffer.put(rgb);

            if (pixelsInRow == imageWidth) {
                totalPadding += rowPadding;
                for (int p = 0; p < rowPadding; p++) {
                    buffer.put((byte) 0x00);
                }
                pixelsInRow = 1;
                rowStartIndex -= imageWidth;
                currentIndex = rowStartIndex;
            } else {
                pixelsInRow++;
                currentIndex++;
            }
        }

        fileSize += totalPadding - rowPadding;
        imageSize += totalPadding - rowPadding;
    }

    private void writeFileHeader() {
        buffer.put(bmpSignature);
        buffer.put(intToLittleEndian4(fileSize));
        buffer.put(intToLittleEndian2(reserved));
        buffer.put(intToLittleEndian2(reserved));
        buffer.put(intToLittleEndian4(dataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intToLittleEndian4(infoHeaderSize));
        buffer.put(intToLittleEndian4(imageWidth));
        buffer.put(intToLittleEndian4(imageHeight));
        buffer.put(intToLittleEndian2(planes));
        buffer.put(intToLittleEndian2(bitsPerPixel));
        buffer.put(intToLittleEndian4(compression));
        buffer.put(intToLittleEndian4(imageSize));
        buffer.put(intToLittleEndian4(xPixelsPerMeter));
        buffer.put(intToLittleEndian4(yPixelsPerMeter));
        buffer.put(intToLittleEndian4(colorsUsed));
        buffer.put(intToLittleEndian4(importantColors));
    }

    private byte[] intToLittleEndian2(int value) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (value & 0x00FF);
        bytes[1] = (byte) ((value >> 8) & 0x00FF);
        return bytes;
    }

    private byte[] intToLittleEndian4(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0x00FF);
        bytes[1] = (byte) ((value >> 8) & 0x000000FF);
        bytes[2] = (byte) ((value >> 16) & 0x000000FF);
        bytes[3] = (byte) ((value >> 24) & 0x000000FF);
        return bytes;
    }

    static void saveBitmapAsBmp(Bitmap bitmap, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        new BmpEncoder().encode(bitmap, fos);
        fos.close();
    }
}