package com.bardiademon.Jjson.data.model;

import com.bardiademon.Jjson.converter.string.JjsonStringConverter;

public record JjsonString(String original, String escaped) {

    @Override
    public String escaped() {
        return escaped == null ? original : escaped;
    }

    @Override
    public String toString() {
        return escaped();
    }

    public String toPlainText() {
        return JjsonStringConverter.toPlainText(original());
    }

}
