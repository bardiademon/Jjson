package com.bardiademon.Jjson.JjsonArray;

public interface JjsonArrayPut {
    <T> JjsonArrayPut put(final int index, final T value);

    <T> JjsonArrayPut put(final T value);
}
