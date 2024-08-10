package com.bardiademon.Jjson.io;

import com.bardiademon.Jjson.converter.JjsonCharset;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;

public final class JjsonReader {

    private static final Logger logger = new Logger(JjsonReader.class);

    private JjsonReader() {
    }

    public static <T> T ofFile(final String path, final OnString<T> onString) throws JjsonException {
        return ofFile(path, JjsonCharset.getCharset(), onString);
    }

    public static <T> T ofFile(final String path, Charset charset, final OnString<T> onString) throws JjsonException {
        if (path == null || path.trim().isEmpty()) {
            logger.error("Path is null");
            throw new JjsonException("Path is null");
        }
        final File file = new File(path);
        if (!file.exists()) {
            logger.error("Path not exists: {}", path);
            throw new JjsonException("Path not exists: " + path);
        }
        try {
            return onString.on(Files.readString(file.toPath(), charset));
        } catch (IOException e) {
            logger.error("Fail to read stream file: {}", path, e);
            throw new JjsonException(e);
        }
    }

    public static <T> T ofStream(final InputStream inputStream, final OnString<T> onString) throws JjsonException {
        return ofStream(inputStream, JjsonCharset.getCharset(), onString);
    }

    public static <T> T ofStream(final InputStream inputStream, final Charset charset, final OnString<T> onString) throws JjsonException {
        try {
            if (inputStream == null) {
                logger.error("Invalid stream");
                throw new JjsonException("Invalid stream");
            }
            return onString.on(new String(inputStream.readAllBytes(), charset));
        } catch (IOException e) {
            logger.error("Fail to from stream", e);
            throw new JjsonException(e);
        }
    }

    public interface OnString<T> {
        T on(final String value) throws JjsonException;
    }

}
