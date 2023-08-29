package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.converter.JjsonEncoder;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonArrayConverter;
import com.bardiademon.Jjson.util.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class JjsonArray implements JjsonEncoder, JjsonArrayBuilder, JjsonArrayCollection, JjsonArrayGetter, JjsonArrayStream {
    private static final Logger logger = new Logger(JjsonArray.class);

    private final List<Object> array = new LinkedList<>();

    private final static JjsonArrayConverter converter = new JjsonArrayConverter();

    public JjsonArray() {
    }

    public static JjsonArray fromString(final String json) throws JjsonException {
        return converter.fromString(json);
    }

    public static JjsonArray fromJjsonArray(final JjsonArray jjsonArray) throws JjsonException {
        if (jjsonArray == null || jjsonArray.isEmpty()) {
            return new JjsonArray();
        }
        return JjsonArray.fromString(jjsonArray.encode());
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Object value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final String value) {
        if (validIndex(index) && has(converter.stringFormatter(value)) == -1) {
            array.set(index, converter.stringFormatter(value));
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Number value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Long value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Integer value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Short value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Float value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Double value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final Boolean value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final JjsonObject value) {
        if (validIndex(index) && has(value) == -1) {
            array.set(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final int index, final JjsonArray value) {
        if (has(value) == -1) {
            array.add(index, value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Object value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final String value) {
        if (has(value) == -1) {
            array.add(converter.stringFormatter(value));
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Number value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Long value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Integer value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Short value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Float value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Double value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final Boolean value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final JjsonObject value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public JjsonArrayBuilder put(final JjsonArray value) {
        if (has(value) == -1) {
            array.add(value);
        }
        return this;
    }

    @Override
    public boolean remove(final int index) {
        if (notEmpty()) {
            if (index >= 0 && index <= size()) {
                array.remove(index);
            }
        }
        return false;
    }

    @Override
    public int has(final Object obj) {
        if (notEmpty()) {
            for (int i = 0; i < array.size(); i++) {
                if (Objects.equals(obj, array.get(i))) return i;
            }
        }
        return -1;
    }

    @Override
    public Object getObject(final int index) {
        return getObject(index, null);
    }

    @Override
    public String getString(final int index) {
        return getString(index, null);
    }

    @Override
    public String asString(final int index) {
        return asString(index, null);
    }

    @Override
    public Short getShort(final int index) {
        return getShort(index, null);
    }

    @Override
    public Integer getInteger(final int index) {
        return getInteger(index, null);
    }

    @Override
    public Long getLong(final int index) {
        return getLong(index, null);
    }

    @Override
    public Float getFloat(final int index) {
        return getFloat(index, null);
    }

    @Override
    public Double getDouble(final int index) {
        return getDouble(index, null);
    }

    @Override
    public Number getNumber(final int index) {
        return getNumber(index, null);
    }

    public Boolean getBoolean(final int index) {
        return getBoolean(index, null);
    }

    @Override
    public JjsonObject getJjsonObject(final int index) {
        return getJjsonObject(index, null);
    }

    @Override
    public JjsonArray getJjsonArray(final int index) {
        return getJjsonArray(index, null);
    }

    @Override
    public Object getObject(final int index, final Object def) {
        if (validIndex(index)) {
            return array.get(index);
        }
        return def;
    }

    @Override
    public String getString(final int index, final String def) {
        if (validIndex(index) && getObject(index) instanceof final String value) {
            return converter.stringFormatterReverse(value);
        }
        return def;
    }

    @Override
    public String asString(final int index, final String def) {
        if (validIndex(index)) {
            final Object objectValue = getObject(index);
            if (objectValue == null) {
                return def;
            } else if (objectValue instanceof String) {
                return getString(index);
            } else {
                return objectValue.toString();
            }
        }
        return def;
    }

    @Override
    public Short getShort(final int index, final Short def) {
        final Number number = getNumber(index);
        if (number != null) {
            try {
                return number.shortValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Integer getInteger(final int index, final Integer def) {
        final Number number = getNumber(index);
        if (number != null) {
            try {
                return number.intValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Long getLong(final int index, final Long def) {
        final Number number = getNumber(index);
        if (number != null) {
            try {
                return number.longValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Float getFloat(final int index, final Float def) {
        final Number number = getNumber(index);
        if (number != null) {
            try {
                return number.floatValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Double getDouble(final int index, final Double def) {
        final Number number = getNumber(index);
        if (number != null) {
            try {
                return number.doubleValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Number getNumber(final int index, final Number def) {
        if (validIndex(index) && getObject(index) instanceof final Number value) {
            return value;
        }
        return def;
    }

    @Override
    public Boolean getBoolean(final int index, final Boolean def) {
        if (validIndex(index) && getObject(index) instanceof final Boolean value) {
            return value;
        }
        return def;
    }

    @Override
    public JjsonObject getJjsonObject(final int index, final JjsonObject def) {
        if (validIndex(index) && getObject(index) instanceof final JjsonObject value) {
            return value;
        }
        return def;
    }

    @Override
    public JjsonArray getJjsonArray(final int index, final JjsonArray def) {
        if (validIndex(index) && getObject(index) instanceof final JjsonArray value) {
            return value;
        }
        return def;
    }

    @Override
    public Stream<Object> stream() {
        return array.stream();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean notEmpty() {
        return size() > 0;
    }

    @Override
    public void clear() {
        array.clear();
    }

    @Override
    public String encode() {
        return converter.encode(this);
    }

    @Override
    public String encodeFormatter() {
        return converter.encodeFormatter(this);
    }

    @Override
    public String toString() {
        return encode();
    }

    private boolean validIndex(final int index) {
        return index >= 0 && index < size();
    }
}
