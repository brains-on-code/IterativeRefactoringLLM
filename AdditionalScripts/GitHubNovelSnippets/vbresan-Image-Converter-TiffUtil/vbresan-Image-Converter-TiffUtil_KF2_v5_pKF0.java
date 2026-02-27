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
        // Utility class
    }

    private static TiffBitmapFactory.Options createOptions(File file) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(file, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String buildOutputFilename(
            String originalFilename,
            int currentPage,
            int totalPages,
            OutputFormat format
    ) {
        String pageInfo = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                currentPage,
                totalPages
        );

        int extensionIndex = originalFilename.lastIndexOf('.');
        String baseName = (extensionIndex == -1)
                ? originalFilename
                : originalFilename.substring(0, extensionIndex);

        return baseName + pageInfo + "." + format.getFileExtension();
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
                try {
                    Converter.encodeBitmap(bitmap, format, outFile);
                } finally {
                    bitmap.recycle();
                }
            } else {
                convertDirectly(
                        format,
                        inFile.getAbsolutePath(),
                        outFile.getAbsolutePath(),
                        directory
                );
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

        TiffBitmapFactory.Options options = createOptions(inFile);
        options.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputFilename = inFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {
            String outputFilename = buildOutputFilename(
                    inputFilename,
                    directoryIndex + 1,
                    directoryCount,
                    format
            );
            File outputFile = new File(outputFolder, outputFilename);

            options.inDirectoryNumber = directoryIndex;

            try {
                convertFromTIFSinglePage(inFile, format, options, outputFile);
            } catch (EncodeException e) {
                // Delete partially created file and record failed directory
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            outputFiles.add(new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return outputFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options options = createOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}