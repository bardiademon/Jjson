package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;

public interface JjsonArrayBuilder {
    <T> JjsonArrayBuilder put(final int index, final T value);

    <T> JjsonArrayBuilder putValue(final T value);

}
