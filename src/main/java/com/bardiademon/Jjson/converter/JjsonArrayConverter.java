package com.bardiademon.Jjson.converter;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.data.enums.JsonValueType;
import com.bardiademon.Jjson.util.Logger;
import com.bardiademon.Jjson.util.Null;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class JjsonArrayConverter extends JjsonConverter {

    private static final Logger logger = new Logger(JjsonArrayConverter.class);

    private static JjsonArrayConverter converter;

    private JjsonArrayConverter() {
    }

    public static JjsonArrayConverter converter() {
        if (converter == null) {
            converter = new JjsonArrayConverter();
        }
        return converter;
    }

    public JjsonArray ofString(String json) throws JjsonException {
        try {

            if (json == null || json.isEmpty()) {
                throw new JjsonException("Json is null");
            }

            logger.trace("from string: {}", json);

            json = json.trim();

            if (!json.startsWith("[")) {
                throw new JjsonException("Json array must with '[' start", 0);
            }
            if (!json.endsWith("]")) {
                throw new JjsonException("Json array must with ']' end", json.length() - 1);
            }

            if (isEmpty(json, '[', ']')) {
                return JjsonArray.create();
            }

            final char[] jsonChars = json.toCharArray();

            int index = 1;

            final JjsonArray jjsonArray = new JjsonArray();

            do {
                final Object[] findFirst = findCharWithoutSpace(jsonChars, index);

                final Object[] valueIndex = getValue(findFirst, json, jsonChars, index);
                index = (int) valueIndex[0];

                final JsonValueType valueType = (JsonValueType) valueIndex[2];
                final Object value = valueIndex[1];

                switch (valueType) {
                    case NULL -> jjsonArray.put(Null.NULL);
                    case NUMBER -> jjsonArray.put((Number) value);
                    case STRING -> jjsonArray.put((String) value);
                    case BOOLEAN -> jjsonArray.put((boolean) value);
                    case JSON_OBJECT -> jjsonArray.put(JjsonObjectConverter.converter().ofString((String) value));
                    case JSON_ARRAY -> jjsonArray.put(ofString((String) value));
                }

                index = eoj(jsonChars, index, ']');
            } while (index >= 0);

            logger.trace("Successfully mapped json array: {}", jjsonArray.encode());

            return jjsonArray;

        } catch (Exception e) {
            logger.error("Fail to validation json: {}", json, e);
            if (e instanceof JjsonException) throw e;
            else throw new JjsonException(e);
        }
    }

    public JjsonArray ofJsonLString(String jsonL) throws JjsonException {
        try {

            if (jsonL == null || jsonL.isEmpty()) {
                throw new JjsonException("Json is null");
            }

            logger.trace("from string: {}", jsonL);

            jsonL = jsonL.trim();

            if (jsonL.startsWith("[")) {
                throw new JjsonException("Json array must not with '[' start", 0);
            }
            if (jsonL.endsWith("]")) {
                throw new JjsonException("Json array must not with ']' end", jsonL.length() - 1);
            }

            final JjsonArray jsonArrayL = JjsonArray.create();

            logger.trace("Starting converting JSONL to JjsonArray, JSONL: {}", jsonL);
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(jsonL));
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.startsWith("{")) {
                        throw new JjsonException("Each line should start with a '{'", line, lineNumber);
                    }
                    if (!line.endsWith("}")) {
                        throw new JjsonException("Each line should end with a '}'", line, lineNumber);
                    }
                    final JjsonObject item = JjsonObjectConverter.converter().ofString(line);
                    logger.trace("Successfully converted a line of JSONL. , LineNumber: {} , LineJsonString: {} , JjsonObject: {}", lineNumber, line, item);
                    jsonArrayL.put(item);
                }
            } catch (IOException e) {
                logger.error("Fail to converting JSONL to JjsonArray, JSONL: {}", jsonL, e);
                throw new JjsonException(e);
            }

            logger.trace("Successfully converted JSONL to JjsonArray, JSONL: {} , JjsonArray: {}", jsonL, jsonArrayL);
            return jsonArrayL;

        } catch (Exception e) {
            logger.error("Fail to validation json: {}", jsonL, e);
            if (e instanceof JjsonException) throw e;
            else throw new JjsonException(e);
        }
    }

    public String encode(final JjsonArray jjsonArray) {
        if (jjsonArray == null || jjsonArray.isEmpty()) {
            return "[]";
        }

        final StringBuilder jsonString = new StringBuilder("[");

        final AtomicInteger i = new AtomicInteger(0);
        jjsonArray.stream().forEach(item -> {
            item = item instanceof final JjsonClass<?> jjsonClass ? jjsonClass.jsonValue() : item;
            if (item instanceof final String value) {
                jsonString.append('"').append(value).append('"');
            } else if (item instanceof final JjsonObject value) {
                jsonString.append(JjsonObjectConverter.converter().encode(value));
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

    public String encodeJsonL(final JjsonArray jjsonArray) throws JjsonException {
        logger.trace("Starting JSONL encoding, JjsonArray: {}", jjsonArray);
        if (jjsonArray == null || jjsonArray.isEmpty()) {
            return "";
        }
        final StringBuilder jsonL = new StringBuilder();
        for (int i = 0, len = jjsonArray.size(); i < len; i++) {
            final Object item = jjsonArray.getObject(i);
            if (!(item instanceof JjsonObject)) {
                throw new JjsonException("Each item in JjsonArray must be a JsonObject.", String.valueOf(item), i);
            }
            jsonL.append(JjsonObjectConverter.converter().encode((JjsonObject) item));
            if ((i + 1) < len) {
                jsonL.append('\n');
            }
        }
        logger.trace("Successfully encoded JjsonArray to JSONL string, JjsonArray: {} , JSONLString: {}", jjsonArray, jsonL);
        return jsonL.toString();
    }


    public String encodeFormatter(final JjsonArray jjsonArray) {
        return encodeFormatter(jjsonArray, 1);
    }

    String encodeFormatter(final JjsonArray jjsonArray, int numberOfSpace) {
        if (jjsonArray == null || jjsonArray.isEmpty()) {
            return JjsonArray.create().encode();
        }

        final StringBuilder jsonString = new StringBuilder("[").append('\n').append(space(numberOfSpace));

        final AtomicInteger i = new AtomicInteger(0);
        jjsonArray.stream().forEach(item -> {
            item = item instanceof final JjsonClass jjsonClass ? jjsonClass.jsonValue() : item;
            if (item instanceof final String value) {
                jsonString.append('"').append(value).append('"');
            } else if (item instanceof final JjsonObject value) {
                jsonString.append(JjsonObjectConverter.converter().encodeFormatter(value, numberOfSpace + 1));
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
        collection.forEach(item -> jjsonArray.put(Objects.requireNonNullElse(item, Null.NULL)));
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
