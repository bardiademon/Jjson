package com.bardiademon.Jjson.converter;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public interface JjsonOf {
    Logger logger = new Logger(JjsonOf.class);

    static <T> T ofFile(final String path, final OnString<T> onString) throws JjsonException {
        return ofFile(path, StandardCharsets.UTF_8, onString);
    }

    static <T> T ofFile(final String path, Charset charset, final OnString<T> onString) throws JjsonException {
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

    static <T> T ofStream(final InputStream inputStream, final OnString<T> onString) throws JjsonException {
        return ofStream(inputStream, StandardCharsets.UTF_8, onString);
    }

    static <T> T ofStream(final InputStream inputStream, final Charset charset, final OnString<T> onString) throws JjsonException {
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

    interface OnString<T> {
        T on(final String value) throws JjsonException;
    }

}
