package com.bardiademon.Jjson.io;

import com.bardiademon.Jjson.exception.JjsonException;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.bardiademon.Jjson.config.JjsonCharset.getCharset;

/**
 * JjsonLFileWriter
 * L => JsonL
 */
public interface JjsonLFileWriter {

    boolean replace = false;

    default void writeJjsonL(final String path) throws IOException, JjsonException {
        writeJjsonL(path, replace, getCharset());
    }

    default void writeJjsonL(final String path, final boolean replace) throws IOException, JjsonException {
        writeJjsonL(path, replace, getCharset());
    }

    void writeJjsonL(final String path, final boolean replace, final Charset charset) throws IOException, JjsonException;

}
