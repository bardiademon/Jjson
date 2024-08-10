package com.bardiademon.Jjson;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.bardiademon.Jjson.converter.JjsonCharset.getCharset;

public interface JjsonFileWriter {

    boolean replace = false;
    boolean formatter = false;

    default void write(final String path) throws IOException {
        write(path, replace, formatter, getCharset());
    }

    default void write(final String path, final boolean replace) throws IOException {
        write(path, replace, formatter, getCharset());
    }

    default void write(final String path, final boolean replace, final boolean formatter) throws IOException {
        write(path, replace, formatter, getCharset());
    }

    void write(final String path, final boolean replace, final boolean formatter, final Charset charset) throws IOException;

}
