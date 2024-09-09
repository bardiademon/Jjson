package com.bardiademon.Jjson.converter.clazz.converter;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

sealed class ClassConverter permits ClassToJjsonConverter {

    public <T> boolean isMemberOfOtherClass(final T value) {
        return value != null && (
                !(
                        value instanceof String
                                || value.getClass().isPrimitive()
                                || value instanceof Number
                                || value instanceof Boolean
                                || value instanceof Character
                                || value instanceof JjsonArray
                                || value instanceof JjsonObject
                                || (value instanceof byte[] || value instanceof Byte[])
                )
        );
    }

    public <T> boolean isArray(final T value) {
        return value != null && !(value instanceof byte[] || value instanceof Byte[]) && (value.getClass().isArray() || value instanceof Collection<?>);
    }

    public <T> boolean isPrimitiveStackArray(final T obj) {
        return (obj instanceof short[] || obj instanceof int[] || obj instanceof long[] || obj instanceof double[] || obj instanceof byte[] || obj instanceof float[] || obj instanceof char[] || obj instanceof boolean[]);
    }

    public static <T> Object getDefaultValue(final Class<T> type) {
        if (type.isArray() || Collection.class.isAssignableFrom(type)) {
            return JjsonArray.create();
        } else if (type == Boolean.class) {
            return false;
        } else if (type == Byte.class) {
            return (byte) 0;
        } else if (type == Short.class) {
            return (short) 0;
        } else if (type == Integer.class) {
            return 0;
        } else if (type == Long.class) {
            return 0L;
        } else if (type == Float.class) {
            return 0F;
        } else if (type == Double.class) {
            return 0D;
        } else if (type == Character.class) {
            return '\u0000';
        } else if (type == String.class) {
            return "";
        } else if (Number.class.isAssignableFrom(type)) {
            return 0;
        } else {
            return JjsonObject.create();
        }
    }

    protected <T> Class<?> getWrapperClass(Class<T> primitiveClass) {
        if (primitiveClass == boolean.class) return Boolean.class;
        if (primitiveClass == char.class) return Character.class;
        if (primitiveClass == byte.class) return Byte.class;
        if (primitiveClass == short.class) return Short.class;
        if (primitiveClass == int.class) return Integer.class;
        if (primitiveClass == long.class) return Long.class;
        if (primitiveClass == float.class) return Float.class;
        if (primitiveClass == double.class) return Double.class;
        return null;
    }

    protected <T> T newPrimitive(final T primitive) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (primitive == null || !primitive.getClass().isPrimitive()) {
            return null;
        }
        final Class<?> wrapperClass = getWrapperClass(primitive.getClass());
        assert wrapperClass != null;
        return (T) wrapperClass.getDeclaredConstructor().newInstance();
    }
}
