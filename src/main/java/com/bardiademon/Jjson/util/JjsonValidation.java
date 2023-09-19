package com.bardiademon.Jjson.util;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonCharset;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.io.InputStream;
import java.nio.charset.Charset;

public final class JjsonValidation {
    private JjsonValidation() {
    }

    public static boolean ofString(final String json) {
        try {
            JjsonObject.ofString(json);
            return true;
        } catch (Exception e) {
            try {
                JjsonArray.ofString(json);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public static boolean ofFile(final String path) {
        return ofFile(path, JjsonCharset.getCharset());
    }

    public static boolean ofFile(final String path, final Charset charset) {
        try {
            JjsonObject.ofFile(path, charset);
            return true;
        } catch (JjsonException e) {
            try {
                JjsonArray.ofFile(path, charset);
                return true;
            } catch (JjsonException ignored) {
            }
        }
        return false;
    }

    public static boolean ofStream(final InputStream inputStream) {
        return ofStream(inputStream, JjsonCharset.getCharset());
    }

    public static boolean ofStream(final InputStream inputStream, final Charset charset) {
        try {
            JjsonObject.ofStream(inputStream, charset);
            return true;
        } catch (JjsonException e) {
            try {
                JjsonArray.ofStream(inputStream, charset);
                return true;
            } catch (JjsonException ignored) {
            }
        }
        return false;
    }
}
