package com.bardiademon.Jjson.JjsonObject;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.converter.JjsonEncoder;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;
import com.bardiademon.Jjson.converter.JjsonObjectConverter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class JjsonObject implements JjsonEncoder, JjsonObjectBuilder, JjsonObjectGetter, JjsonObjectCollection, JjsonObjectStream {

    private static final Logger logger = new Logger(JjsonObject.class);

    private final LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<>();

    private final static JjsonObjectConverter converter = new JjsonObjectConverter();

    public JjsonObject() {
    }

    public static JjsonObject fromString(final String json) throws JjsonException {
        return converter.fromString(json);
    }

    public static JjsonObject fromJjsonObject(final JjsonObject jjsonObject) throws JjsonException {
        if (jjsonObject == null || jjsonObject.isEmpty()) {
            return new JjsonObject();
        }
        return JjsonObject.fromString(jjsonObject.encode());
    }

    @Override
    public JjsonObject put(final String key, final Object value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final String value) {
        jsonMap.put(converter.stringFormatter(key), converter.stringFormatter(value));
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Number value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Long value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Integer value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Short value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Float value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Double value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final Boolean value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, final JjsonObject value) {
        jsonMap.put(converter.stringFormatter(key), value);
        return this;
    }

    @Override
    public JjsonObject put(final String key, JjsonArray value) {
        jsonMap.put(converter.stringFormatter(key), value);
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
        if (jsonMap.containsKey(converter.stringFormatter(key))) {
            return jsonMap.get(converter.stringFormatter(key));
        }
        return def;
    }

    @Override
    public String getString(final String key, final String def) {
        if (getObject(key) instanceof final String value) {
            return converter.stringFormatterReverse(value);
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
                logger.error("Number not an long");
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
        final List<String> keys = new LinkedList<>();
        for (final Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            keys.add(converter.stringFormatterReverse(entry.getKey()));
        }
        return keys;
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
}
