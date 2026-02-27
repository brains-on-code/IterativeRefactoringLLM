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
        // Utility class; prevent instantiation
    }

    private static TiffBitmapFactory.Options getOptions(File file) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(file, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String getNewFilename(
            String       filename,
            int          current,
            int          total,
            OutputFormat format
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(), " (%04d of %04d)", current, total);

        int dotIndex = filename.lastIndexOf(".");
        String baseName = (dotIndex == -1)
                ? filename + pageSuffix
                : filename.substring(0, dotIndex) + pageSuffix;

        return baseName + "." + format.getFileExtension();
    }

    private static void convertDirectly(
            OutputFormat format,
            String       in,
            String       out,
            int          directory
    ) throws Exception {

        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        // Limiting memory can cause a failure in conversion.
        // options.availableMemory = DEFAULT_AVAILABLE_MEMORY;
        options.readTiffDirectory = directory;
        options.throwExceptions   = true;

        switch (format) {
            case BMP:
                TiffConverter.convertTiffBmp(in, out, options, null);
                break;
            case JPG:
                TiffConverter.convertTiffJpg(in, out, options, null);
                break;
            case PNG:
                TiffConverter.convertTiffPng(in, out, options, null);
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
            IOException exception = new IOException(e.toString());
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    private static void convertFromTIFSinglePage(
            File                      inFile,
            OutputFormat              format,
            TiffBitmapFactory.Options options,
            File                      outFile
    ) throws EncodeException {

        Bitmap bitmap    = TiffBitmapFactory.decodeFile(inFile, options);
        int    directory = options.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, format, outFile);
                bitmap.recycle();
            } else {
                String in  = inFile.getAbsolutePath();
                String out = outFile.getAbsolutePath();
                convertDirectly(format, in, out, directory);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertFromTIF(
            File         inFile,
            OutputFormat format,
            File         outputFolder
    ) throws EncodeException {

        List<FilenameUriTuple> outFiles = new ArrayList<>();

        TiffBitmapFactory.Options options = getOptions(inFile);
        options.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectories = new ArrayList<>();
        String inFilename = inFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int i = 0; i < directoryCount; i++) {
            String outFilename = getNewFilename(inFilename, i + 1, directoryCount, format);
            File   outFile     = new File(outputFolder, outFilename);

            options.inDirectoryNumber = i;

            try {
                convertFromTIFSinglePage(inFile, format, options, outFile);
            } catch (EncodeException e) {
                //noinspection ResultOfMethodCallIgnored
                outFile.delete();
                failedDirectories.add(i);
            }

            outFiles.add(new FilenameUriTuple(outFilename, Uri.fromFile(outFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return outFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options options = getOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}