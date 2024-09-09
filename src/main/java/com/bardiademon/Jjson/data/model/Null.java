package com.bardiademon.Jjson.data.model;

import java.util.Objects;

public final class Null {

    public static final Null NULL = new Null();

    private Null() {
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    protected Null clone() {
        return NULL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == null || obj instanceof Null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(null);
    }
}
