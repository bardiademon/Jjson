package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;

public interface JjsonArrayBuilder {
    JjsonArrayBuilder putValue(final int index, final Object value);

    JjsonArrayBuilder put(final int index, final Object value);

    JjsonArrayBuilder put(final int index, final String value);

    JjsonArrayBuilder put(final int index, final Number value);

    JjsonArrayBuilder put(final int index, final Long value);

    JjsonArrayBuilder put(final int index, final Integer value);

    JjsonArrayBuilder put(final int index, final Short value);

    JjsonArrayBuilder put(final int index, final Float value);

    JjsonArrayBuilder put(final int index, final Double value);

    JjsonArrayBuilder put(final int index, final Boolean value);

    JjsonArrayBuilder put(final int index, final JjsonObject jjsonObject);

    JjsonArrayBuilder put(final int index, final JjsonArray jjsonArray);

    JjsonArrayBuilder putValue(final Object value);

    JjsonArrayBuilder put(final Object value);

    JjsonArrayBuilder put(final String value);

    JjsonArrayBuilder put(final Number value);

    JjsonArrayBuilder put(final Long value);

    JjsonArrayBuilder put(final Integer value);

    JjsonArrayBuilder put(final Short value);

    JjsonArrayBuilder put(final Float value);

    JjsonArrayBuilder put(final Double value);

    JjsonArrayBuilder put(final Boolean value);

    JjsonArrayBuilder put(final JjsonObject jjsonObject);

    JjsonArrayBuilder put(final JjsonArray jjsonArray);
}
