package com.bardiademon.Jjson.data.exception;

public final class JjsonException extends Throwable {

    public final int index;
    public final String aChar;

    public JjsonException(final String message) {
        this(message, -1);
    }

    public JjsonException(final String message, final int index) {
        this(message, null, index);
    }

    public JjsonException(final String message, final String str, final int index) {
        super(index >= 0 ? String.format("%s, Index: %d", message, index) + (str != null ? String.format(", Char -> %s", str) : "") : message);
        this.index = index;
        this.aChar = str;
    }
}
