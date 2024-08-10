package com.bardiademon.Jjson.JjsonObject;

public interface JjsonObjectBuilder {
    <T> JjsonObjectBuilder put(final String key, final T value);
}
