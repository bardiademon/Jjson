package com.bardiademon.Jjson.JjsonArray;

public interface JjsonArrayPut {
    <T> JjsonArrayPut put(final int index, final T value);

    <T> JjsonArrayPut put(final T value);

    JjsonArrayPut putBytesArray(final byte[] bytes);

    JjsonArrayPut putBytesArray(final int index, final byte[] bytes);
}
