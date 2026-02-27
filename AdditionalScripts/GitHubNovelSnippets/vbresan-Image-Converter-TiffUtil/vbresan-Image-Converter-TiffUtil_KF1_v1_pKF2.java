package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;
import android.net.Uri;

import org.beyka.tiffbitmapfactory.TiffBitmapFactory;
import org.beyka.tiffbitmapfactory.TiffConverter;
import org.beyka.tiffbitmapfactory.TiffSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import biz.binarysolutions.imageconverter.data.FilenameUriTuple;
import biz.binarysolutions.imageconverter.data.OutputFormat;
import biz.binarysolutions.imageconverter.exceptions.EncodeException;

public class TiffImageConverter {

    /**
     * Reads only TIFF bounds/metadata for the given file.
     */
    private static TiffBitmapFactory.Options getTiffOptions(File tiffFile) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * Builds an output filename for a specific page of a multi-page TIFF.
     */
    private static String buildOutputFilename(
            String       baseName,
            int          pageIndex,
            int          pageCount,
            OutputFormat format
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                pageIndex,
                pageCount
        );

        String nameWithoutExtension;
        int dotIndex = baseName.lastIndexOf(".");
        if (dotIndex == -1) {
            nameWithoutExtension = baseName + pageSuffix;
        } else {
            nameWithoutExtension = baseName.substring(0, dotIndex) + pageSuffix;
        }

        return nameWithoutExtension + "." + format.getFileExtension();
    }

    /**
     * Converts a single TIFF directory (page) directly to the requested format.
     */
    private static void convertTiffPageDirectly(
            OutputFormat format,
            String       inputPath,
            String       outputPath,
            int          directoryNumber
    ) throws Exception {

        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        options.readTiffDirectory = directoryNumber;
        options.throwExceptions   = true;

        if (format == OutputFormat.BMP) {
            TiffConverter.convertTiffBmp(inputPath, outputPath, options, null);
        } else if (format == OutputFormat.JPG) {
            TiffConverter.convertTiffJpg(inputPath, outputPath, options, null);
        } else if (format == OutputFormat.PNG) {
            TiffConverter.convertTiffPng(inputPath, outputPath, options, null);
        } else {
            throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    /**
     * Saves a bitmap as a TIFF file.
     */
    static void saveBitmapAsTiff(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions options = new TiffSaver.SaveOptions();
        options.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, options);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    /**
     * Encodes a single TIFF directory (page) to the requested format.
     */
    private static void encodeTiffPage(
            File                      inputFile,
            OutputFormat              format,
            TiffBitmapFactory.Options options,
            File                      outputFile
    ) throws EncodeException {

        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputFile, options);
        int directoryNumber = options.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, format, outputFile);
                bitmap.recycle();
            } else {
                String inputPath  = inputFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertTiffPageDirectly(format, inputPath, outputPath, directoryNumber);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    /**
     * Converts all pages of a TIFF file to the requested format.
     */
    public static List<FilenameUriTuple> convertTiff(
            File         inputFile,
            OutputFormat format,
            File         outputDirectory
    ) throws EncodeException {

        List<FilenameUriTuple> result = new ArrayList<>();

        TiffBitmapFactory.Options options = getTiffOptions(inputFile);
        options.inAvailableMemory = 10_000_000;

        List<Integer> failedDirectories = new ArrayList<>();
        String baseName = inputFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputName = buildOutputFilename(
                    baseName,
                    directoryIndex + 1,
                    directoryCount,
                    format
            );
            File outputFile = new File(outputDirectory, outputName);

            options.inDirectoryNumber = directoryIndex;

            try {
                encodeTiffPage(inputFile, format, options, outputFile);
            } catch (EncodeException e) {
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            result.add(new FilenameUriTuple(outputName, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return result;
    }

    /**
     * Checks whether the given file is a valid TIFF image.
     */
    public static boolean isTiff(File file) {
        TiffBitmapFactory.Options options = getTiffOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}