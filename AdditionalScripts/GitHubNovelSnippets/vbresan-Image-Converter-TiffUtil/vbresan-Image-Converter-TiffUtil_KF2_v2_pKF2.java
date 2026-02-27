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

public class TiffUtil {

    private static final int DEFAULT_AVAILABLE_MEMORY = 10_000_000;

    private TiffUtil() {
        // Prevent instantiation
    }

    private static TiffBitmapFactory.Options getOptions(File file) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(file, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String getNewFilename(
            String filename,
            int currentIndex,
            int totalCount,
            OutputFormat format
    ) {
        String indexSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                currentIndex,
                totalCount
        );

        int extensionIndex = filename.lastIndexOf(".");
        String baseName = (extensionIndex == -1)
                ? filename + indexSuffix
                : filename.substring(0, extensionIndex) + indexSuffix;

        return baseName + "." + format.getFileExtension();
    }

    private static void convertDirectly(
            OutputFormat format,
            String inputPath,
            String outputPath,
            int directory
    ) throws Exception {

        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        options.readTiffDirectory = directory;
        options.throwExceptions = true;

        switch (format) {
            case BMP:
                TiffConverter.convertTiffBmp(inputPath, outputPath, options, null);
                break;
            case JPG:
                TiffConverter.convertTiffJpg(inputPath, outputPath, options, null);
                break;
            case PNG:
                TiffConverter.convertTiffPng(inputPath, outputPath, options, null);
                break;
            default:
                throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void encodeToTIF(Bitmap bitmap, File file) throws IOException {
        TiffSaver.SaveOptions options = new TiffSaver.SaveOptions();
        options.inThrowException = true;

        try {
            TiffSaver.saveBitmap(file, bitmap, options);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    private static void convertFromTIFSinglePage(
            File inFile,
            OutputFormat format,
            TiffBitmapFactory.Options options,
            File outFile
    ) throws EncodeException {

        Bitmap bitmap = TiffBitmapFactory.decodeFile(inFile, options);
        int directory = options.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, format, outFile);
                bitmap.recycle();
            } else {
                String inputPath = inFile.getAbsolutePath();
                String outputPath = outFile.getAbsolutePath();
                convertDirectly(format, inputPath, outputPath, directory);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertFromTIF(
            File inFile,
            OutputFormat format,
            File outputFolder
    ) throws EncodeException {

        List<FilenameUriTuple> outputFiles = new ArrayList<>();

        TiffBitmapFactory.Options options = getOptions(inFile);
        options.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputFilename = inFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int i = 0; i < directoryCount; i++) {
            String outputFilename = getNewFilename(inputFilename, i + 1, directoryCount, format);
            File outputFile = new File(outputFolder, outputFilename);

            options.inDirectoryNumber = i;

            try {
                convertFromTIFSinglePage(inFile, format, options, outputFile);
            } catch (EncodeException e) {
                outputFile.delete();
                failedDirectories.add(i);
            }

            outputFiles.add(new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return outputFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options options = getOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}