package com.bardiademon.Jjson.converter;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class JjsonCharset {
    private static Charset charset;

    static {
        setCharset(StandardCharsets.UTF_8);
    }

    private JjsonCharset() {
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        JjsonCharset.charset = charset;
    }
}
