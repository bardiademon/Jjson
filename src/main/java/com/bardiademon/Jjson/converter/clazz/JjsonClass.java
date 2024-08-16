package com.bardiademon.Jjson.converter.clazz;

public interface JjsonClass<T> {

    String METHOD_NAME = "jsonValue";

    default T jsonValue() {
        return null;
    }

}
