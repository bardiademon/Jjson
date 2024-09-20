package com.bardiademon.Jjson.object;

public interface JjsonObjectPut {
    <T> JjsonObjectPut put(final String key, final T value);

    JjsonObjectPut putBytesArray(final String key, final byte[] bytes);
}
