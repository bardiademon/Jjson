package com.bardiademon.Jjson.util;

import com.bardiademon.Jjson.JjsonFileWriter;
import com.bardiademon.Jjson.converter.JjsonEncoder;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import static com.bardiademon.Jjson.converter.JjsonCharset.getCharset;

public final class JjsonWriteToFile {

    private static final Logger logger = new Logger(JjsonWriteToFile.class);


    public static void write(final String jjson, final String path) throws IOException, JjsonException {
        write(jjson, path, JjsonFileWriter.replace, getCharset());
    }

    public static void write(final String jjson, final String path, final boolean replace) throws IOException, JjsonException {
        write(jjson, path, replace, getCharset());
    }

    public static void write(final String jjson, final String path, final boolean replace, final Charset charset) throws IOException, JjsonException {
        if (JjsonValidation.ofString(jjson)) {

            final File file = new File(path);
            if (!replace && file.exists()) {
                logger.error("File is exists, File: {} , Jjson: {}", file, jjson);
                throw new IOException("File is exists: " + file);
            }

            if (file.exists()) {
                assert replace;
                if (!file.delete()) {
                    logger.error("Cannot delete file, File: {} , Jjson: {}", file, jjson);
                    throw new IOException("Cannot delete file: " + file);
                }

                logger.trace("Deleted file, File: {} , Jjson: {}", file, jjson);
            }

            logger.trace("Writing: {}", file);

            Files.writeString(file.toPath(), jjson, charset);

            if (!file.exists()) {
                logger.error("Cannot write file, File: {} , Jjson: {}", file, jjson);
                throw new IOException("Cannot write file: " + file);
            }

            logger.info("Written file, File: {} , Jjson: {}", file, jjson);


        } else throw new JjsonException("Invalid jjson file", jjson, -1);
    }

    public static void write(final JjsonEncoder encoder, final String path) throws IOException {
        write(encoder, path, JjsonFileWriter.replace, JjsonFileWriter.formatter, getCharset());
    }

    public static void write(final JjsonEncoder encoder, final String path, final boolean replace) throws IOException {
        write(encoder, path, replace, JjsonFileWriter.formatter, getCharset());
    }

    public static void write(final JjsonEncoder encoder, final String path, final boolean replace, final boolean formatter) throws IOException {
        write(encoder, path, replace, formatter, getCharset());
    }

    public static void write(final JjsonEncoder encoder, final String path, final boolean replace, final boolean formatter, final Charset charset) throws IOException {
        try {
            write(formatter ? encoder.encodeFormatter() : encoder.encode(), path, replace, charset);
        } catch (JjsonException e) {
            logger.error("Fail to write file, invalid jjson file, Path: {} , Jjson: {}", path, encoder.encode());
            throw new IOException("Fail to write file");
        }
    }
}
