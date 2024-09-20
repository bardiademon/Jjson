package com.bardiademon.Jjson.array;

import com.bardiademon.Jjson.converter.string.JjsonStringConverter;
import com.bardiademon.Jjson.converter.clazz.converter.ClassToJjsonConverter;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.data.model.JjsonString;
import com.bardiademon.Jjson.io.JjsonLFileWriter;
import com.bardiademon.Jjson.io.JjsonFileWriter;
import com.bardiademon.Jjson.encoder.JjsonEncoder;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonArrayConverter;
import com.bardiademon.Jjson.io.JjsonReader;
import com.bardiademon.Jjson.io.JjsonWriteToFile;
import com.bardiademon.Jjson.validation.JjsonValidation;
import com.bardiademon.Jjson.util.Logger;
import com.bardiademon.Jjson.data.model.Null;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public final class JjsonArray implements JjsonEncoder, JjsonArrayPut, JjsonArrayCollection, JjsonArrayGetter, JjsonArrayStream, JjsonFileWriter, JjsonLFileWriter {
    private static final Logger logger = new Logger(JjsonArray.class);

    private final List<Object> array = new CopyOnWriteArrayList<>();

    private boolean isJsonL;

    public JjsonArray() {
        this(false);
    }

    public JjsonArray(final boolean isJsonL) {
        this.isJsonL = isJsonL;
    }

    public static JjsonArray create() {
        return new JjsonArray();
    }

    public static JjsonArray createJsonL() {
        return new JjsonArray(true);
    }

    public static JjsonArray ofString(final String json) throws JjsonException {
        return JjsonArrayConverter.converter().ofStringByJackson(json);
    }

    public static JjsonArray ofJsonLString(final String jsonL) throws JjsonException {
        return JjsonArrayConverter.converter().ofJsonLString(jsonL);
    }

    public static JjsonArray ofCollection(final Collection<?> collection) {
        return JjsonArrayConverter.converter().ofCollection(collection);
    }

    public static JjsonArray ofClass(final Object clazz) throws JjsonException {
        return ClassToJjsonConverter.converter().toJjsonArray(clazz);
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

    public static JjsonArray ofJsonLFile(final String path) throws JjsonException {
        return JjsonReader.ofFile(path, JjsonArray::ofJsonLString);
    }

    public static JjsonArray ofJsonLFile(final String path, final Charset charset) throws JjsonException {
        return JjsonReader.ofFile(path, charset, JjsonArray::ofJsonLString);
    }

    public static JjsonArray ofStream(final InputStream inputStream) throws JjsonException {
        return JjsonReader.ofStream(inputStream, JjsonArray::ofString);
    }

    public static JjsonArray ofStream(final InputStream inputStream, Charset charset) throws JjsonException {
        return JjsonReader.ofStream(inputStream, charset, JjsonArray::ofString);
    }

    public static JjsonArray ofJsonLStream(final InputStream inputStream, final Charset charset) throws JjsonException {
        return JjsonReader.ofStream(inputStream, charset, JjsonArray::ofJsonLString);
    }

    public static JjsonArray ofJsonLStream(final InputStream inputStream) throws JjsonException {
        return JjsonReader.ofStream(inputStream, JjsonArray::ofJsonLString);
    }

    @Override
    public <T> JjsonArray put(final int index, final T value) {
        if (isJsonL) {
            if (value instanceof final JjsonObject jsonL) {
                putValue(index, jsonL);
            }
        } else {
            if (value instanceof final int[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final long[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final short[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final float[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final double[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final byte[] val) putValue(index, val);
            else if (value instanceof final Byte[] val) putValue(index, val);
            else if (value instanceof final Object[] val) putValue(index, JjsonArray.ofArray(val));
            else if (value instanceof final Collection<?> val) putValue(index, JjsonArray.ofCollection(val));
            else if (value instanceof final Map<?, ?> val) putValue(index, JjsonObject.ofMap(val));
            else if (value instanceof final Character val) putValue(index, String.valueOf(val));
            else if (value instanceof final Enum<?> val) {
                if (val instanceof final JjsonClass<?> jjsonClass) {
                    putValue(index, jjsonClass.jsonValue());
                } else {
                    putValue(index, val.toString());
                }
            } else putValue(index, value);
        }
        return this;
    }

    @Override
    public <T> JjsonArray put(final T value) {
        if (isJsonL) {
            if (value instanceof final JjsonObject jsonL) {
                putValue(jsonL);
            }
        } else {
            if (value instanceof final int[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final long[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final short[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final float[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final double[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final byte[] val) putValue(val);
            else if (value instanceof final Byte[] val) putValue(val);
            else if (value instanceof final Object[] val) putValue(JjsonArray.ofArray(val));
            else if (value instanceof final Collection<?> val) putValue(JjsonArray.ofCollection(val));
            else if (value instanceof final Map<?, ?> val) putValue(JjsonObject.ofMap(val));
            else if (value instanceof final Character val) putValue(String.valueOf(val));
            else if (value instanceof final Enum<?> val) {
                if (val instanceof final JjsonClass<?> jjsonClass) {
                    putValue(jjsonClass.jsonValue());
                } else {
                    putValue(val.toString());
                }
            } else putValue(value);
        }
        return this;
    }

    private <T> void putValue(final T value) {
        if (value instanceof byte[] || value instanceof Byte[]) {
            putBytesArray(value instanceof byte[] ? (byte[]) value : JjsonArrayConverter.converter().toPrimitive((Byte[]) value));
        } else {
            array.add(value instanceof final String strValue ? JjsonStringConverter.escapedByJackson(strValue) : (value == null ? Null.NULL : value));
        }
    }

    private <T> void putValue(final int index, final T value) {
        if (validIndex(index)) {
            if (value instanceof byte[] || value instanceof Byte[]) {
                putBytesArray(index, value instanceof byte[] ? (byte[]) value : JjsonArrayConverter.converter().toPrimitive((Byte[]) value));
            } else {
                array.set(index, value instanceof final String strValue ? JjsonStringConverter.escapedByJackson(strValue) : (value == null ? Null.NULL : value));
            }
        }
    }

    @Override
    public JjsonArray putBytesArray(final byte[] bytes) {
        try {
            array.add(Base64.getEncoder().encodeToString(bytes));
        } catch (Exception e) {
            logger.error("Fail to put bytes array, Bytes: {}", bytes, e);
        }
        return this;
    }

    @Override
    public JjsonArray putBytesArray(final int index, final byte[] bytes) {
        if (validIndex(index)) {
            try {
                array.set(index, Base64.getEncoder().encodeToString(bytes));
            } catch (Exception e) {
                logger.error("Fail to put bytes array, Index: {} , Bytes: {}", index, bytes, e);
            }
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
    public JjsonString getJjsonString(final int index) {
        return getJjsonString(index, null);
    }

    @Override
    public String getOriginalString(int index) {
        return getOriginalString(index, null);
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
    public byte[] getBytes(int index) {
        return getBytes(index, null);
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
            final Object obj = array.get(index);
            return obj instanceof Null ? def : obj;
        }
        return def;
    }

    @Override
    public JjsonString getJjsonString(final int index, final JjsonString def) {
        if (validIndex(index) && getObject(index) instanceof final JjsonString value) {
            return value;
        }
        return def;
    }

    @Override
    public String getString(final int index, final String def) {
        final JjsonString jjsonString = getJjsonString(index, new JjsonString(def, null));
        if (jjsonString != null) {
            return jjsonString.escaped();
        }
        return def;
    }

    @Override
    public String getOriginalString(final int index, final String def) {
        final JjsonString jjsonString = getJjsonString(index, new JjsonString(def, null));
        if (jjsonString != null) {
            return jjsonString.original();
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
                if (objectValue instanceof final JjsonClass<?> jjsonClass) {
                    final Object jsonClassValue = jjsonClass.jsonValue();
                    if (jsonClassValue instanceof final String jsonClassStringValue) {
                        return jsonClassStringValue;
                    }
                }
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
    public byte[] getBytes(final int index, final byte[] def) {
        if (validIndex(index) && getObject(index) instanceof final String base64) {
            try {
                return Base64.getDecoder().decode(base64);
            } catch (Exception e) {
                logger.error("Fail to Base64 to bytes array, Index: {} , Def: {} , Value: {}", index, def, base64, e);
            }
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

    public String encodeJsonL() throws JjsonException {
        return JjsonArrayConverter.converter().encodeJsonL(this);
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
    public void writeJjsonL(final String path, final boolean replace, final Charset charset) throws IOException, JjsonException {
        JjsonWriteToFile.writeJsonL(JjsonArrayConverter.converter().encodeJsonL(this), path, replace, charset);
    }

    public boolean isJsonL() {
        return JjsonValidation.isJjsonL(this);
    }

    public boolean setJsonL(boolean jsonL) {
        if (jsonL && JjsonValidation.isJjsonL(this)) {
            isJsonL = true;
            return true;
        } else {
            isJsonL = false;
            return false;
        }
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
