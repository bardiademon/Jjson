package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;

public interface JjsonArrayGetter {
    Object getObject(final int index);

    String getString(final int index);

    String asString(final int index);

    Short getShort(final int index);

    Integer getInteger(final int index);

    Long getLong(final int index);

    Float getFloat(final int index);

    Double getDouble(final int index);

    Number getNumber(final int index);

    JjsonObject getJjsonObject(final int index);

    JjsonArray getJjsonArray(final int index);

    Boolean getBoolean(final int index);

    Object getObject(final int index, final Object def);

    String getString(final int index, final String def);

    String asString(final int index, final String def);

    Short getShort(final int index, final Short def);

    Integer getInteger(final int index, final Integer def);

    Long getLong(final int index, final Long def);

    Float getFloat(final int index, final Float def);

    Double getDouble(final int index, final Double def);

    Number getNumber(final int index, final Number def);

    Boolean getBoolean(final int index, final Boolean def);

    JjsonObject getJjsonObject(final int index, final JjsonObject def);

    JjsonArray getJjsonArray(final int index, final JjsonArray def);
}
