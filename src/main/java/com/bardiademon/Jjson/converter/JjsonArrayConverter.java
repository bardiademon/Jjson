package com.bardiademon.Jjson.converter;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.data.enums.JsonValueType;
import com.bardiademon.Jjson.util.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class JjsonArrayConverter extends JjsonConverter {

    private static final Logger logger = new Logger(JjsonArrayConverter.class);

    public JjsonArrayConverter() {
    }

    public JjsonArray ofString(String json) throws JjsonException {

        logger.trace("from string: {}", json);

        json = json.trim();

        if (!json.startsWith("[")) {
            throw new JjsonException("Json array must with [ start", 0);
        }
        if (!json.endsWith("]")) {
            throw new JjsonException("Json array must with ] end", json.length() - 1);
        }

        if (isEmpty(json, '[', ']')) {
            return JjsonArray.create();
        }

        final char[] jsonChars = json.toCharArray();

        int index = 1;

        final JjsonArray jjsonArray = new JjsonArray();
        final JjsonObjectConverter jjsonObjectConverter = new JjsonObjectConverter();

        do {
            final Object[] findFirst = findCharWithoutSpace(jsonChars, index);

            final Object[] valueIndex = getValue(findFirst, json, jsonChars, index);
            index = (int) valueIndex[0];

            final JsonValueType valueType = (JsonValueType) valueIndex[2];
            final Object value = valueIndex[1];

            switch (valueType) {
                case NULL -> jjsonArray.put((Object) null);
                case NUMBER -> jjsonArray.put((Number) value);
                case STRING -> jjsonArray.put((String) value);
                case BOOLEAN -> jjsonArray.put((boolean) value);
                case JSON_OBJECT -> jjsonArray.put(jjsonObjectConverter.ofString((String) value));
                case JSON_ARRAY -> jjsonArray.put(ofString((String) value));
            }

            index = eoj(jsonChars, index, ']');
        } while (index >= 0);

        logger.trace("Successfully mapped json array: {}", jjsonArray.encode());

        return jjsonArray;
    }


    public String encode(final JjsonArray jjsonArray) {

        final StringBuilder jsonString = new StringBuilder("[");
        final JjsonObjectConverter jjsonConverter = new JjsonObjectConverter();

        final AtomicInteger i = new AtomicInteger(0);
        jjsonArray.stream().forEach(item -> {
            if (item instanceof final String value) {
                jsonString.append('"').append(stringFormatterReverse(value)).append('"');
            } else if (item instanceof final JjsonObject value) {
                jsonString.append(jjsonConverter.encode(value));
            } else if (item instanceof final JjsonArray value) {
                jsonString.append(encode(value));
            } else {
                jsonString.append(item);
            }

            if (i.incrementAndGet() < jjsonArray.size()) {
                jsonString.append(",");
            }
        });
        jsonString.append("]");

        return jsonString.toString();
    }


    public String encodeFormatter(final JjsonArray jjsonArray) {
        return encodeFormatter(jjsonArray, 1);
    }

    String encodeFormatter(final JjsonArray jjsonArray, int numberOfSpace) {

        final StringBuilder jsonString = new StringBuilder("[").append('\n').append(space(numberOfSpace));

        final JjsonObjectConverter jjsonConverter = new JjsonObjectConverter();

        final AtomicInteger i = new AtomicInteger(0);
        jjsonArray.stream().forEach(item -> {
            if (item instanceof final String value) {
                jsonString.append('"').append(stringFormatterReverse(value)).append('"');
            } else if (item instanceof final JjsonObject value) {
                jsonString.append(jjsonConverter.encodeFormatter(value, numberOfSpace + 1));
            } else if (item instanceof final JjsonArray value) {
                jsonString.append(encodeFormatter(value, numberOfSpace + 1));
            } else {
                jsonString.append(item);
            }

            if (i.incrementAndGet() < jjsonArray.size()) {
                jsonString.append(", ").append('\n').append(space(numberOfSpace));
            }
        });
        jsonString.append('\n').append(space(numberOfSpace - 1)).append(']');

        return jsonString.toString();

    }

    public JjsonArray ofCollection(final Collection<?> collection) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (collection == null || collection.isEmpty()) {
            return jjsonArray;
        }

        final JjsonObjectConverter jjsonConverter = new JjsonObjectConverter();

        for (final Object itemObj : collection) {
            if (itemObj instanceof final Map<?, ?> value) {
                jjsonArray.put(jjsonConverter.ofMap(value));
            } else if (itemObj instanceof final Collection<?> value) {
                jjsonArray.put(ofCollection(value));
            } else if (itemObj instanceof final Object[] value) {
                jjsonArray.put(ofArray(value));
            } else if (itemObj instanceof final int[] value) {
                jjsonArray.put(ofArray(value));
            } else if (itemObj instanceof final long[] value) {
                jjsonArray.put(ofArray(value));
            } else if (itemObj instanceof final short[] value) {
                jjsonArray.put(ofArray(value));
            } else if (itemObj instanceof final double[] value) {
                jjsonArray.put(ofArray(value));
            } else if (itemObj instanceof final float[] value) {
                jjsonArray.put(ofArray(value));
            } else {
                jjsonArray.putValue(itemObj);
            }
        }
        return jjsonArray;
    }

    public <T> JjsonArray ofArray(final T[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        return ofCollection(Arrays.stream(array).toList());
    }

    public JjsonArray ofArray(final int[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        return ofCollection(Arrays.stream(array).boxed().toList());
    }

    public JjsonArray ofArray(final long[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        return ofCollection(Arrays.stream(array).boxed().toList());
    }

    public JjsonArray ofArray(final short[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        final List<Short> shorts = new ArrayList<>();
        for (final short item : array) shorts.add(item);
        return ofCollection(shorts);
    }

    public JjsonArray ofArray(final double[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        return ofCollection(Arrays.stream(array).boxed().toList());
    }

    public JjsonArray ofArray(final float[] array) {
        final JjsonArray jjsonArray = JjsonArray.create();
        if (array == null || array.length == 0) {
            return jjsonArray;
        }
        final List<Float> shorts = new ArrayList<>();
        for (final float item : array) shorts.add(item);
        return ofCollection(shorts);
    }


}
