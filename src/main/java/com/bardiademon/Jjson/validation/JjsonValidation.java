package com.bardiademon.Jjson.validation;

import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.config.JjsonCharset;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.io.InputStream;
import java.nio.charset.Charset;

public final class JjsonValidation {

    private static final Logger logger = new Logger(JjsonValidation.class);

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
            } catch (Exception ex) {
                logger.error("Failed to validation json, JSON: {}", json, ex);
            }
        }
        return false;
    }

    public static boolean ofJsonLString(final String json) {
        try {
            JjsonArray.ofJsonLString(json);
            return true;
        } catch (Exception e) {
            logger.error("Failed to validation jsonl, JSONL: {}", json, e);
        }
        return false;
    }

    public static boolean isJjsonL(final JjsonArray jjsonArray) {
        return jjsonArray.stream().allMatch(item -> item instanceof JjsonObject);
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
            } catch (JjsonException ex) {
                logger.error("Failed to validation json, JSON: {}", path, ex);
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
            } catch (JjsonException ex) {
                logger.error("Failed to validation json", ex);
            }
        }
        return false;
    }
}
