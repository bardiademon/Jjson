package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.io.JjsonFileWriter;
import com.bardiademon.Jjson.converter.JjsonEncoder;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonArrayConverter;
import com.bardiademon.Jjson.io.JjsonReader;
import com.bardiademon.Jjson.io.JjsonWriteToFile;
import com.bardiademon.Jjson.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public final class JjsonArray implements JjsonEncoder, JjsonArrayPut, JjsonArrayCollection, JjsonArrayGetter, JjsonArrayStream, JjsonFileWriter {
    private static final Logger logger = new Logger(JjsonArray.class);

    private final List<Object> array = new CopyOnWriteArrayList<>();

    public JjsonArray() {
    }

    public static JjsonArray create() {
        return new JjsonArray();
    }

    public static JjsonArray ofString(final String json) throws JjsonException {
        return JjsonArrayConverter.converter().ofString(json);
    }

    public static JjsonArray ofCollection(final Collection<?> collection) {
        return JjsonArrayConverter.converter().ofCollection(collection);
    }

    public static <T> JjsonArray ofArray(final T[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofArray(final int[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofArray(final long[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofArray(final short[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofArray(final double[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofArray(final float[] array) {
        return JjsonArrayConverter.converter().ofArray(array);
    }

    public static JjsonArray ofJjsonArray(final JjsonArray jjsonArray) throws JjsonException {
        if (jjsonArray == null || jjsonArray.isEmpty()) {
            return JjsonArray.create();
        }
        return ofString(jjsonArray.encode());
    }

    public static JjsonArray ofFile(final String path) throws JjsonException {
        return JjsonReader.ofFile(path, JjsonArray::ofString);
    }

    public static JjsonArray ofFile(final String path, final Charset charset) throws JjsonException {
        return JjsonReader.ofFile(path, charset, JjsonArray::ofString);
    }

    public static JjsonArray ofStream(final InputStream inputStream) throws JjsonException {
        return JjsonReader.ofStream(inputStream, JjsonArray::ofString);
    }

    public static JjsonArray ofStream(final InputStream inputStream, Charset charset) throws JjsonException {
        return JjsonReader.ofStream(inputStream, charset, JjsonArray::ofString);
    }

    @Override
    public <T> JjsonArray put(final int index, final T value) {
        if (value instanceof final int[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final long[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final short[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final float[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final double[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final Object[] val) putValue(index, JjsonArray.ofArray(val));
        else if (value instanceof final Collection<?> val) putValue(index, JjsonArray.ofCollection(val));
        else if (value instanceof final Map<?, ?> val) putValue(index, JjsonObject.ofMap(val));
        else putValue(index, value);
        return this;
    }

    @Override
    public <T> JjsonArray put(final T value) {
        if (JjsonArrayConverter.converter().isInvalidValue(value)) {
            if (value instanceof final int[] val) put(JjsonArray.ofArray(val));
            else if (value instanceof final long[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final short[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final float[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final double[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final Object[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final Collection<?> val) putValue(JjsonArray.ofCollection(val));
            else if (value instanceof final Map<?, ?> val) putValue(JjsonObject.ofMap(val));
            else putValue(value);
        }
        return this;
    }

    private <T> void putValue(final T value) {
        array.add(value instanceof final String strValue ? JjsonArrayConverter.converter().stringFormatter(strValue) : value);
    }

    private <T> void putValue(final int index, final T value) {
        if (validIndex(index)) {
            array.set(index, value instanceof final String strValue ? JjsonArrayConverter.converter().stringFormatter(strValue) : value);
        }
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
            return JjsonArrayConverter.converter().stringFormatterReverse(value);
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
                logger.error("Number not an integer");
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
                logger.error("Number not a long");
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
                logger.error("Number not a float");
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
                logger.error("Number not a double");
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
        return JjsonArrayConverter.converter().encode(this);
    }

    @Override
    public String encodeFormatter() {
        return JjsonArrayConverter.converter().encodeFormatter(this);
    }

    @Override
    public String toString() {
        return encode();
    }

    private boolean validIndex(final int index) {
        return index >= 0 && index < size();
    }

    @Override
    public void write(final String path, final boolean replace, final boolean formatter, final Charset charset) throws IOException {
        JjsonWriteToFile.write(this, path, replace, formatter, charset);
    }

    @Override
    public JjsonArray clone() {
        try {
            return JjsonArray.ofJjsonArray(this);
        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }
    }
}
