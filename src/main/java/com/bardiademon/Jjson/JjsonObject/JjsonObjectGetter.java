package com.bardiademon.Jjson.JjsonObject;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;

import java.util.List;

public interface JjsonObjectGetter {
    Object getObject(final String key);

    String getString(final String key);

    String asString(final String key);

    Short getShort(final String key);

    Integer getInteger(final String key);

    Long getLong(final String key);

    Float getFloat(final String key);

    Double getDouble(final String key);

    Number getNumber(final String key);

    Boolean getBoolean(final String key);

    JjsonObject getJjsonObject(final String key);

    JjsonArray getJjsonArray(final String key);

    Object getObject(final String key, final Object def);

    String getString(final String key, final String def);

    String asString(final String key, final String def);

    Short getShort(final String key, final Short def);

    Integer getInteger(final String key, final Integer def);

    Long getLong(final String key, final Long def);

    Float getFloat(final String key, final Float def);

    Double getDouble(final String key, final Double def);

    Number getNumber(final String key, final Number def);

    Boolean getBoolean(final String key, final Boolean def);

    JjsonObject getJjsonObject(final String key, final JjsonObject def);

    JjsonArray getJjsonArray(final String key, final JjsonArray def);

    List<String> keys();
}
