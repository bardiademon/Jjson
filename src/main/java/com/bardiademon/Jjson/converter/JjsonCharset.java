package com.bardiademon.Jjson.converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class JjsonCharset {
    private static Charset charset;

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    static {
        setDefaultCharset();
    }

    private JjsonCharset() {
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setDefaultCharset() {
        JjsonCharset.charset = DEFAULT_CHARSET;
    }

    public static void setCharset(Charset charset) {
        JjsonCharset.charset = charset;
    }
}
