package com.bardiademon.Jjson.JjsonObject;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;

public interface JjsonObjectBuilder {
    JjsonObjectBuilder putValue(final String key, final Object value);

    JjsonObjectBuilder put(final String key, final Object value);

    JjsonObjectBuilder put(final String key, final String value);

    JjsonObjectBuilder put(final String key, final Number value);

    JjsonObjectBuilder put(final String key, final Long value);

    JjsonObjectBuilder put(final String key, final Integer value);

    JjsonObjectBuilder put(final String key, final Short value);

    JjsonObjectBuilder put(final String key, final Float value);

    JjsonObjectBuilder put(final String key, final Double value);

    JjsonObjectBuilder put(final String key, final Boolean value);

    JjsonObjectBuilder put(final String key, final JjsonObject jjsonObject);

    JjsonObjectBuilder put(final String key, final JjsonArray jjsonArray);

}
