package com.bardiademon.Jjson.converter;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.data.enums.JsonValueType;
import com.bardiademon.Jjson.util.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class JjsonObjectConverter extends JjsonConverter {

    private static final Logger logger = new Logger(JjsonObjectConverter.class);

    public JjsonObjectConverter() {
    }

    public JjsonObject ofString(String json) throws JjsonException {
        try {

            if (json == null || json.isEmpty()) {
                throw new JjsonException("Json is null");
            }

            logger.trace("from string: {}", json);

            json = json.trim();

            if (!json.startsWith("{")) {
                throw new JjsonException("Json object must with { start", 0);
            }
            if (!json.endsWith("}")) {
                throw new JjsonException("Json object must with } end", json.length() - 1);
            }

            if (isEmpty(json, '{', '}')) {
                return JjsonObject.create();
            }

            final char[] jsonChars = json.toCharArray();

            int index = 1;

            final JjsonObject jjsonObject = new JjsonObject();
            final JjsonArrayConverter arrayMapper = new JjsonArrayConverter();

            final Object[] eoj = findCharWithoutSpace(jsonChars, 1);
            if (eoj != null && ((char) eoj[1]) == '}') {
                return jjsonObject;
            }

            do {

                final Object[] keyIndex = getKey(json, jsonChars, index);
                index = (int) keyIndex[0];
                final Object[] indexValue = getValue(json, jsonChars, index);
                index = (int) indexValue[0];

                final String key = (String) keyIndex[1];
                final Object value = indexValue[1];
                final JsonValueType valueType = (JsonValueType) indexValue[2];

                switch (valueType) {
                    case NULL -> jjsonObject.put(key, (Object) null);
                    case NUMBER -> jjsonObject.put(key, (Number) value);
                    case STRING -> jjsonObject.put(key, (String) value);
                    case BOOLEAN -> jjsonObject.put(key, (boolean) value);
                    case JSON_OBJECT -> jjsonObject.put(key, ofString((String) value));
                    case JSON_ARRAY -> jjsonObject.put(key, arrayMapper.ofString((String) value));
                }

                index = eoj(jsonChars, index, '}');
            } while (index >= 0);

            return jjsonObject;


        } catch (Exception e) {
            logger.error("Fail to validation json: {}", json, e);
            if (e instanceof JjsonException) throw e;
            else throw new JjsonException(e);
        }
    }

    private Object[] getKey(final String json, final char[] jsonChars, int index) throws JjsonException {
        return super.getString(json, jsonChars, index);
    }

    private Object[] getValue(final String json, final char[] jsonChars, int index) throws JjsonException {
        try {
            final Object[] findColon = findCharWithoutSpace(jsonChars, index);
            if (findColon == null || ((char) findColon[1]) != ':') {
                throw new JjsonException("Not found colon", getCharFromResultObject(findColon), index);
            }
            index = (int) findColon[0] + 1;
            final Object[] findFirstChar = findCharWithoutSpace(jsonChars, index);
            if (findFirstChar == null) {
                throw new JjsonException("Nothing after colon", index);
            }

            return super.getValue(findFirstChar, json, jsonChars, index);

        } catch (Exception e) {
            logger.error("Fail to get value", e);
            throw new JjsonException("Invalid column value", e.getMessage(), 0);
        }
    }

    public String encode(final JjsonObject jjsonObject) {

        final StringBuilder jsonString = new StringBuilder("{");
        final JjsonArrayConverter arrayConverter = new JjsonArrayConverter();

        final List<String> keys = jjsonObject.keys();
        for (int i = 0; i < keys.size(); i++) {
            final String key = keys.get(i);
            final Object object = jjsonObject.getObject(key);

            jsonString.append('"').append(key).append('"').append(':');

            if (object instanceof final String value) {
                jsonString.append('"').append(stringFormatterReverse(value)).append('"');
            } else if (object instanceof final JjsonObject value) {
                jsonString.append(encode(value));
            } else if (object instanceof final JjsonArray value) {
                jsonString.append(arrayConverter.encode(value));
            } else {
                jsonString.append(object);
            }

            if ((i + 1) < keys.size()) {
                jsonString.append(",");
            }
        }
        jsonString.append("}");

        return jsonString.toString();
    }

    public String encodeFormatter(final JjsonObject jjsonObject) {
        return encodeFormatter(jjsonObject, 1);
    }

    String encodeFormatter(final JjsonObject jjsonObject, int numberOfSpace) {

        final StringBuilder jsonString = new StringBuilder("{").append("\n").append(space(numberOfSpace));

        final JjsonArrayConverter arrayConverter = new JjsonArrayConverter();

        final List<String> keys = jjsonObject.keys();
        for (int i = 0; i < keys.size(); i++) {
            final String key = keys.get(i);
            final Object object = jjsonObject.getObject(key);

            jsonString.append('"').append(stringFormatterReverse(key)).append('"').append(':').append(" ");

            if (object instanceof final String value) {
                jsonString.append('"').append(stringFormatterReverse(value)).append('"');
            } else if (object instanceof final JjsonObject value) {
                jsonString.append(encodeFormatter(value, numberOfSpace + 1));
            } else if (object instanceof final JjsonArray value) {
                jsonString.append(arrayConverter.encodeFormatter(value, numberOfSpace + 1));
            } else {
                jsonString.append(object);
            }

            if ((i + 1) < keys.size()) {
                jsonString.append(",").append("\n").append(space(numberOfSpace));
            }
        }
        jsonString.append("\n").append(space(numberOfSpace - 1)).append("}");

        return jsonString.toString();
    }

    public JjsonObject ofMap(final Map<?, ?> map) {

        final JjsonObject jjsonObject = JjsonObject.create();

        if (map == null || map.isEmpty()) {
            return jjsonObject;
        }

        final JjsonArrayConverter arrayConverter = new JjsonArrayConverter();

        for (final Object keyObj : map.keySet()) {
            if (keyObj == null) continue;
            final String key = keyObj.toString();
            final Object valueObj = map.get(key);

            if (valueObj instanceof final Map<?, ?> value) {
                jjsonObject.put(key, ofMap(value));
            } else if (valueObj instanceof final Collection<?> value) {
                jjsonObject.put(key, arrayConverter.ofCollection(value));
            } else if (valueObj instanceof final Object[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else if (valueObj instanceof final int[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else if (valueObj instanceof final long[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else if (valueObj instanceof final short[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else if (valueObj instanceof final double[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else if (valueObj instanceof final float[] value) {
                jjsonObject.put(key, arrayConverter.ofArray(value));
            } else {
                jjsonObject.putValue(key, valueObj);
            }
        }

        return jjsonObject;
    }
}
