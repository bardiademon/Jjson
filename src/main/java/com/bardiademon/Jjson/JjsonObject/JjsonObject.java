package com.bardiademon.Jjson.JjsonObject;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.io.JjsonFileWriter;
import com.bardiademon.Jjson.converter.JjsonArrayConverter;
import com.bardiademon.Jjson.converter.JjsonEncoder;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.io.JjsonReader;
import com.bardiademon.Jjson.io.JjsonWriteToFile;
import com.bardiademon.Jjson.util.Logger;
import com.bardiademon.Jjson.converter.JjsonObjectConverter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JjsonObject implements JjsonEncoder, JjsonObjectPut, JjsonObjectGetter, JjsonObjectCollection, JjsonObjectStream, JjsonFileWriter {

    private static final Logger logger = new Logger(JjsonObject.class);

    private final LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<>();

    public JjsonObject() {
    }

    public static JjsonObject create() {
        return new JjsonObject();
    }

    public static JjsonObject ofJjsonObject(final JjsonObject jjsonObject) throws JjsonException {
        if (jjsonObject == null || jjsonObject.isEmpty()) {
            return JjsonObject.create();
        }
        return ofString(jjsonObject.encode());
    }

    public static JjsonObject ofString(final String json) throws JjsonException {
        return JjsonObjectConverter.converter().ofString(json);
    }

    public static JjsonObject ofMap(final Map<?, ?> map) {
        return JjsonObjectConverter.converter().ofMap(map);
    }

    public static JjsonObject ofFile(final String path) throws JjsonException {
        return JjsonReader.ofFile(path, JjsonObject::ofString);
    }

    public static JjsonObject ofFile(final String path, final Charset charset) throws JjsonException {
        return JjsonReader.ofFile(path, charset, JjsonObject::ofString);
    }

    public static JjsonObject ofStream(final InputStream inputStream) throws JjsonException {
        return JjsonReader.ofStream(inputStream, JjsonObject::ofString);
    }

    public static JjsonObject ofStream(final InputStream inputStream, final Charset charset) throws JjsonException {
        return JjsonReader.ofStream(inputStream, charset, JjsonObject::ofString);
    }

    private <T> void putValue(final String key, final T value) {
        jsonMap.put(JjsonObjectConverter.converter().stringFormatter(key), value instanceof final String strValue ? JjsonObjectConverter.converter().stringFormatter(strValue) : value);
    }

    @Override
    public <T> JjsonObject put(final String key, final T value) {
        if (JjsonArrayConverter.converter().isInvalidValue(value)) {
            if (value instanceof final int[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final long[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final short[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final float[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final double[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final Object[] val) {
                putValue(key, JjsonArray.ofArray(val));
            } else if (value instanceof final Collection<?> val) {
                putValue(key, JjsonArray.ofCollection(val));
            } else if (value instanceof final Map<?, ?> val) {
                putValue(key, JjsonObject.ofMap(val));
            } else {
                putValue(key, value);
            }
        }
        return this;
    }

    @Override
    public int size() {
        return jsonMap.size();
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
        jsonMap.clear();
    }

    @Override
    public boolean remove(final String key) {
        return jsonMap.remove(key) != null;
    }

    @Override
    public boolean has(final String key) {
        return jsonMap.containsKey(key);
    }

    @Override
    public Stream<Map.Entry<String, Object>> stream() {
        return jsonMap.entrySet().stream();
    }

    @Override
    public Object getObject(final String key) {
        return getObject(key, null);
    }

    @Override
    public String getString(final String key) {
        return getString(key, null);
    }

    @Override
    public String asString(final String key) {
        return asString(key, null);
    }

    @Override
    public Short getShort(final String key) {
        return getShort(key, null);
    }

    @Override
    public Integer getInteger(final String key) {
        return getInteger(key, null);
    }

    @Override
    public Long getLong(final String key) {
        return getLong(key, null);
    }

    @Override
    public Float getFloat(final String key) {
        return getFloat(key, null);
    }

    @Override
    public Double getDouble(final String key) {
        return getDouble(key, null);
    }

    @Override
    public Number getNumber(final String key) {
        return getNumber(key, null);
    }

    @Override
    public Boolean getBoolean(final String key) {
        return getBoolean(key, null);
    }

    @Override
    public JjsonObject getJjsonObject(final String key) {
        return getJjsonObject(key, null);
    }

    @Override
    public JjsonArray getJjsonArray(final String key) {
        return getJjsonArray(key, null);
    }

    @Override
    public Object getObject(final String key, final Object def) {
        if (jsonMap.containsKey(JjsonObjectConverter.converter().stringFormatter(key))) {
            return jsonMap.get(JjsonObjectConverter.converter().stringFormatter(key));
        }
        return def;
    }

    @Override
    public String getString(final String key, final String def) {
        if (getObject(key) instanceof final String value) {
            return JjsonObjectConverter.converter().stringFormatterReverse(value);
        }
        return def;
    }

    @Override
    public String asString(final String key, final String def) {
        final Object object = getObject(key);
        if (object == null) {
            return def;
        } else if (object instanceof String) {
            return getString(key);
        } else return object.toString();
    }

    @Override
    public Short getShort(final String key, final Short def) {
        if (getObject(key) instanceof final Number value) {
            try {
                return value.shortValue();
            } catch (Exception e) {
                logger.error("Number not a short");
            }
        }
        return def;
    }

    @Override
    public Integer getInteger(final String key, final Integer def) {
        if (getObject(key) instanceof final Number value) {
            try {
                return value.intValue();
            } catch (Exception e) {
                logger.error("Number not an integer");
            }
        }
        return def;
    }

    @Override
    public Long getLong(final String key, final Long def) {
        if (getObject(key) instanceof final Number value) {
            try {
                return value.longValue();
            } catch (Exception e) {
                logger.error("Number not a long");
            }
        }
        return def;
    }

    @Override
    public Float getFloat(final String key, final Float def) {
        if (getObject(key) instanceof final Number value) {
            try {
                return value.floatValue();
            } catch (Exception e) {
                logger.error("Number not a float");
            }
        }
        return def;
    }

    @Override
    public Double getDouble(final String key, final Double def) {
        if (getObject(key) instanceof final Number value) {
            try {
                return value.doubleValue();
            } catch (Exception e) {
                logger.error("Number not a double");
            }
        }
        return def;
    }

    @Override
    public Number getNumber(final String key, final Number def) {
        if (getObject(key) instanceof final Number value) {
            return value;
        }
        return def;
    }

    @Override
    public Boolean getBoolean(final String key, final Boolean def) {
        if (getObject(key) instanceof final Boolean value) {
            return value;
        }
        return def;
    }

    @Override
    public JjsonObject getJjsonObject(final String key, final JjsonObject def) {
        if (getObject(key) instanceof final JjsonObject value) {
            return value;
        }
        return def;
    }

    @Override
    public JjsonArray getJjsonArray(final String key, final JjsonArray def) {
        if (getObject(key) instanceof final JjsonArray value) {
            return value;
        }
        return def;
    }

    @Override
    public List<String> keys() {
        return jsonMap.keySet().stream().map(item -> JjsonObjectConverter.converter().stringFormatterReverse(item)).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String encode() {
        return JjsonObjectConverter.converter().encode(this);
    }

    @Override
    public String encodeFormatter() {
        return JjsonObjectConverter.converter().encodeFormatter(this);
    }

    @Override
    public String toString() {
        return encode();
    }

    @Override
    public void write(final String path, final boolean replace, final boolean formatter, final Charset charset) throws IOException {
        JjsonWriteToFile.write(this, path, replace, formatter, charset);
    }

    @Override
    public JjsonObject clone() {
        try {
            return JjsonObject.ofJjsonObject(this);
        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }
    }
}
