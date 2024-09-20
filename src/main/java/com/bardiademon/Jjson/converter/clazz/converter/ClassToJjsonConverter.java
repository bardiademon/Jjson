package com.bardiademon.Jjson.converter.clazz.converter;

import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

public final class ClassToJjsonConverter extends ClassConverter {

    private static final Logger logger = new Logger(ClassToJjsonConverter.class);

    private static ClassToJjsonConverter converter;

    private ClassToJjsonConverter() {
    }

    public static ClassToJjsonConverter converter() {
        if (converter == null) {
            converter = new ClassToJjsonConverter();
        }
        return converter;
    }

    public <T> JjsonObject toJjsonObject(final T clazz) throws JjsonException {
        if (clazz == null) {
            logger.trace("Class instance is null.");
            return null;
        }

        final JjsonInclude jjsonInclude = clazz.getClass().getAnnotation(JjsonInclude.class);
        if (jjsonInclude != null && jjsonInclude.useJjsonClass() && clazz instanceof final JjsonClass<?> jjsonClass) {
            logger.trace("Using JjsonClass implementation. Class: {} , JjsonInclude: {}", clazz.getClass().getSimpleName(), jjsonInclude.toString());

            Object jsonClassValue = jjsonClass.jsonValue();
            if (jsonClassValue instanceof final JjsonObject jo) {
                logger.trace("The jsonClassValue is already a JjsonObject, returning it., Class: {} , jsonClassValue: {}", clazz.getClass().getSimpleName(), jo);
                return jo;
            } else {
                String name;
                try {
                    final Method method = clazz.getClass().getMethod(JjsonClass.METHOD_NAME);
                    name = method.getName();
                    final JjsonProperty jjsonPropertyJjsonClassMethod = method.getAnnotation(JjsonProperty.class);
                    if (jjsonPropertyJjsonClassMethod != null && jjsonPropertyJjsonClassMethod.name() != null && !jjsonPropertyJjsonClassMethod.name().trim().isEmpty()) {
                        name = jjsonPropertyJjsonClassMethod.name().trim();
                    }
                    logger.trace("Using method name '{}' for JjsonObject field., Class: {}", name, clazz.getClass().getSimpleName());
                } catch (NoSuchMethodException e) {
                    logger.error("Failed to retrieve method by name. Class: {} , Method name: {}", clazz.getClass().getSimpleName(), JjsonClass.METHOD_NAME, e);
                    throw new JjsonException("Cannot find default value method, MethodName: " + JjsonClass.METHOD_NAME);
                }

                if (isArray(jsonClassValue)) {
                    jsonClassValue = toJjsonArray(jsonClassValue);
                    logger.trace("Converted array to JjsonArray, Class: {} , jsonClassValue: {}", clazz.getClass().getSimpleName(), jsonClassValue);
                } else if (isMemberOfOtherClass(jsonClassValue)) {
                    jsonClassValue = toJjsonObject(jsonClassValue);
                    logger.trace("Converted nested object to JjsonObject.", clazz.getClass().getSimpleName(), jsonClassValue);
                }

                logger.trace("Creating JjsonObject with name '{}' and value '{}'.", name, jsonClassValue);
                return JjsonObject.create().put(name, jsonClassValue);

            }

        }

        final JjsonObject jjsonObject = JjsonObject.create();
        if (clazz instanceof final Map<?, ?> map) {
            logger.trace("Converting map to JjsonObject. Class: {} , Maps: {}", clazz.getClass().getSimpleName(), map);
            for (final Map.Entry<?, ?> entry : map.entrySet()) {
                final Object key = entry.getKey();
                final Object value = entry.getValue();
                if (key != null) {
                    final Object newValue;
                    if (isArray(value)) {
                        newValue = toJjsonArray(value);
                        logger.trace("Converted array in map to JjsonArray. Class: {} , Map: {} , Key: {}", clazz.getClass().getSimpleName(), map, key);
                    } else if (isMemberOfOtherClass(value)) {
                        newValue = toJjsonObject(value);
                        logger.trace("Converted nested object in map to JjsonObject. Class: {} , Map: {} , Key: {}", clazz.getClass().getSimpleName(), map, key);
                    } else {
                        newValue = value;
                    }
                    logger.trace("Putting key-value pair into JjsonObject. Class: {} , Map: {} , Key: {} , Value: {}", clazz.getClass().getSimpleName(), map, key, newValue);
                    jjsonObject.put(String.valueOf(key), newValue);
                }
            }
            return jjsonObject;
        }

        final JjsonInclude.SerializationMode serializationMode;
        final boolean includeNonPublicMethod, includeNonPublicField, includeField, includeStaticField, includeStaticMethod, includeMethod;
        if (jjsonInclude != null) {
            logger.trace("Applying JjsonInclude settings. Class: {} , JjsonInclude: {}", clazz.getClass().getSimpleName(), jjsonInclude);
            serializationMode = jjsonInclude.mode();
            includeNonPublicField = jjsonInclude.includeNonPublicField();
            includeNonPublicMethod = jjsonInclude.includeNonPublicMethod();
            includeField = jjsonInclude.includeField();
            includeStaticField = jjsonInclude.includeStaticField();
            includeStaticMethod = jjsonInclude.includeStaticMethod();
            includeMethod = jjsonInclude.includeMethod();
        } else {
            logger.trace("No JjsonInclude annotation found, using default settings. Class: {}", clazz.getClass().getSimpleName());
            serializationMode = JjsonInclude.JjsonIncludeDefault.MODE;
            includeNonPublicField = JjsonInclude.JjsonIncludeDefault.INCLUDE_NON_PUBLIC_FIELD;
            includeNonPublicMethod = JjsonInclude.JjsonIncludeDefault.INCLUDE_NON_PUBLIC_METHOD;
            includeStaticField = JjsonInclude.JjsonIncludeDefault.INCLUDE_STATIC_FIELD;
            includeField = JjsonInclude.JjsonIncludeDefault.INCLUDE_FIELD;
            includeStaticMethod = JjsonInclude.JjsonIncludeDefault.INCLUDE_STATIC_METHOD;
            includeMethod = JjsonInclude.JjsonIncludeDefault.INCLUDE_METHOD;
        }

        if (serializationMode.isNone()) {
            logger.trace("Serialization mode is set to NONE, returning empty JjsonObject. Class: {}", clazz.getClass().getSimpleName());
            return jjsonObject;
        }
        if (serializationMode.isFiled()) {
            logger.trace("Serializing fields. Class: {}", clazz.getClass().getSimpleName());
            final Field[] declaredFields = clazz.getClass().getDeclaredFields();
            JjsonProperty jjsonProperty;
            for (final Field field : declaredFields) {

                String name = null;
                jjsonProperty = field.getAnnotation(JjsonProperty.class);
                if (jjsonProperty != null && jjsonProperty.ignore()) {
                    logger.trace("Ignoring field as per JjsonProperty settings. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    continue;
                }
                if (!includeNonPublicField && !Modifier.isPublic(field.getModifiers())) {
                    logger.trace("Skipping non-public field. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    continue;
                }
                if (!includeField && !Modifier.isStatic(field.getModifiers())) {
                    logger.trace("Skipping non-static field. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    continue;
                }
                if (!includeStaticField && Modifier.isStatic(field.getModifiers())) {
                    logger.trace("Skipping static field. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    continue;
                }
                if (includeNonPublicField) {
                    logger.trace("Setting accessible for non-public field. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    field.setAccessible(true);
                }

                Object value;
                try {
                    value = field.get(clazz);
                    logger.trace("Retrieved value for field. Class: {} , Field: {}, Value: {}", clazz.getClass().getSimpleName(), field.getName(), value);
                } catch (IllegalAccessException e) {
                    logger.error("Failed to access field value. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName(), e);
                    throw new JjsonException("Fail to get field value, Name: " + field.getName());
                }

                jjsonProperty = field.getAnnotation(JjsonProperty.class);
                if (jjsonProperty != null) {
                    logger.trace("JjsonProperty is not null, Class: {} , Field: {} , JjsonProperty: {}", clazz, field, jjsonProperty);
                    if (jjsonProperty.ignoreIfNull() && value == null) {
                        logger.trace("Ignoring null field as per JjsonProperty settings. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                        continue;
                    }
                    name = jjsonProperty.name();
                }
                if (name == null || name.trim().isEmpty()) {
                    logger.trace("Get field name for json, Class: {} , Field: {} , JjsonProperty: {}", clazz, field, jjsonProperty);
                    name = field.getName();
                }

                if (value == null && jjsonProperty != null && !jjsonProperty.nullable()) {
                    final String defaultValueMethodName = jjsonProperty.defaultValueMethodName();
                    if (defaultValueMethodName != null && !defaultValueMethodName.trim().isEmpty()) {
                        try {
                            final Method method = clazz.getClass().getMethod(defaultValueMethodName);
                            if (method.isVarArgs() || method.getParameterCount() > 0) {
                                throw new JjsonException("The method has parameters, MethodName: " + defaultValueMethodName);
                            }
                            if (method.getReturnType() == void.class || method.getReturnType() == Void.class) {
                                logger.error("This method has a 'void' return type, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                                throw new JjsonException("This method has a 'void' return type, MethodName: " + defaultValueMethodName);
                            }
                            if (method.getReturnType() != field.getType()) {
                                logger.error("The method's return value is not the same as the field's type, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                                throw new JjsonException("The method's return value is not the same as the field's type, MethodName: " + defaultValueMethodName + " , FieldName: " + field.getName() + " , MethodType: " + method.getReturnType() + " , FieldType: " + field.getType());
                            }
                            if (!Modifier.isPublic(method.getModifiers())) {
                                logger.error("This method does not have public access, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                                throw new JjsonException("This method does not have public access, MethodName: " + defaultValueMethodName);
                            }
                            value = method.invoke(clazz);
                            logger.trace("Invoked default value method for field. Class: {} , Method: {}, Value: {}", clazz.getClass().getSimpleName(), defaultValueMethodName, value);

                            if (value == null) {
                                logger.error("The method's return value is null, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                                throw new JjsonException("The method's return value is null, MethodName: " + defaultValueMethodName);
                            }

                            if (isArray(value)) {
                                value = toJjsonArray(value);
                            } else if (isMemberOfOtherClass(value)) {
                                value = toJjsonObject(value);
                            }

                        } catch (NoSuchMethodException e) {
                            logger.error("Cannot find default value method, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                            throw new JjsonException("Cannot find default value method, MethodName: " + defaultValueMethodName);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            logger.error("Error invoking the method, Class : {} , MethodName: {}", clazz.getClass().getSimpleName(), defaultValueMethodName);
                            throw new JjsonException("Error invoking the method, MethodName: " + defaultValueMethodName);
                        }
                    } else {
                        value = getDefaultValue(field.getType());
                        logger.trace("Applied default value to null field. Class: {} , Field: {}, Default Value: {}", clazz.getClass().getSimpleName(), field.getName(), value);
                    }

                } else {
                    if (isArray(value)) {
                        value = toJjsonArray(value);
                        logger.trace("Converted array to JjsonArray. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    } else if (isMemberOfOtherClass(value)) {
                        value = toJjsonObject(value);
                        logger.trace("Converted nested object to JjsonObject. Class: {} , Field: {}", clazz.getClass().getSimpleName(), field.getName());
                    }
                }

                logger.trace("Putting field into JjsonObject. Class: {} , Field: {}, Name: {}, Value: {}", clazz.getClass().getSimpleName(), field.getName(), name, value);
                jjsonObject.put(name, value);

                if (includeNonPublicField) {
                    field.setAccessible(false);
                }
            }
        }
        if (serializationMode.isMethod()) {
            final Method[] declaredMethods = clazz.getClass().getDeclaredMethods();
            JjsonProperty jjsonProperty;
            for (final Method method : declaredMethods) {

                jjsonProperty = method.getAnnotation(JjsonProperty.class);
                if (jjsonProperty == null || jjsonProperty.ignore() || method.getReturnType() == void.class || method.getReturnType() == Void.class) {
                    logger.trace("Ignoring method as per JjsonProperty settings. Class: {} , Method: {} , JjsonProperty: {}", clazz.getClass().getSimpleName(), method.getName(), jjsonProperty);
                    continue;
                }
                if (!includeNonPublicMethod && !Modifier.isPublic(method.getModifiers())) {
                    logger.trace("Skipping non-public method. Class: {} , Method: {} , JjsonProperty: {}", clazz.getClass().getSimpleName(), method.getName(), jjsonProperty);
                    continue;
                }
                if (!includeMethod && !Modifier.isStatic(method.getModifiers())) {
                    logger.trace("Skipping non-static method. Class: {} , Method: {} , JjsonProperty: {}", clazz.getClass().getSimpleName(), method.getName(), jjsonProperty);
                    continue;
                }
                if (!includeStaticMethod && Modifier.isStatic(method.getModifiers())) {
                    logger.trace("Skipping static method. Class: {} , Method: {} , JjsonProperty: {}", clazz.getClass().getSimpleName(), method.getName(), jjsonProperty);
                    continue;
                }
                if (method.isVarArgs() || method.getParameterCount() > 0) {
                    logger.error("The method has parameters, Class : {} , MethodType: {} , MethodName: {}", clazz.getClass().getSimpleName(), method.getReturnType(), method.getName());
                    continue;
                }
                if (includeNonPublicMethod) {
                    logger.trace("Setting accessible for non-public method. Class: {} , Method: {}", clazz.getClass().getSimpleName(), method.getName());
                    method.setAccessible(true);
                }

                Object value;
                try {
                    value = method.invoke(clazz);
                    logger.trace("Invoked method. Class: {} , Method: {}, Value: {}", clazz.getClass().getSimpleName(), method.getName(), value);
                    if (jjsonProperty.ignoreIfNull() && value == null) {
                        logger.trace("Ignoring null method as per JjsonProperty settings. Class: {} , Method: {} , JjsonProperty: {}", clazz.getClass().getSimpleName(), method.getName(), jjsonProperty);
                        continue;
                    }

                    if (value == null && !jjsonProperty.nullable()) {
                        value = getDefaultValue(method.getReturnType());
                    } else {
                        if (isArray(value)) {
                            value = toJjsonArray(value);
                            logger.trace("Converted array to JjsonArray using default method. Class: {} , Method: {}", clazz.getClass().getSimpleName(), method.getName(), value);
                        } else if (isMemberOfOtherClass(value)) {
                            value = toJjsonObject(value);
                            logger.trace("Converted nested object to JjsonObject using default method. Class: {} , Method: {} , Value: {}", clazz.getClass().getSimpleName(), method.getName(), value);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("Failed to invoke method. Class: {} , Method: {}", clazz.getClass().getSimpleName(), method.getName(), e);
                    throw new JjsonException("Fail to invoke method, Name: " + method.getName());
                }

                String name = jjsonProperty.name();
                if (name == null || name.trim().isEmpty()) {
                    name = method.getName();
                }

                logger.trace("Putting method result into JjsonObject. Class: {} , Method: {}, Name: {}, Value: {}", clazz.getClass().getSimpleName(), method.getName(), name, value);
                jjsonObject.put(name, value);

                if (includeNonPublicMethod) {
                    method.setAccessible(false);
                }
            }
        }

        return jjsonObject;
    }

    public <T> JjsonArray toJjsonArray(final T clazz) throws JjsonException {
        if (clazz == null || !isArray(clazz)) {
            return null;
        }

        final JjsonArray jjsonArray = JjsonArray.create();

        if (isPrimitiveStackArray(clazz)) {
            for (int i = 0, length = Array.getLength(clazz); i < length; i++) {
                jjsonArray.put(Array.get(clazz, i));
            }
        } else if (clazz.getClass().isArray()) {
            final Object[] objects = (Object[]) clazz;
            for (Object item : objects) {
                if (isArray(item) || item instanceof Object[] || item instanceof Collection<?>) {
                    item = toJjsonArray(item);
                } else if (isMemberOfOtherClass(item)) {
                    item = toJjsonObject(item);
                }
                jjsonArray.put(item);
            }
        } else if (clazz instanceof final Collection<?> collection) {
            for (Object item : collection) {
                if (isArray(item) || item instanceof Object[] || item instanceof Collection<?>) {
                    item = toJjsonArray(item);
                } else if (isMemberOfOtherClass(item)) {
                    item = toJjsonObject(item);
                }
                jjsonArray.put(item);
            }
        }

        return jjsonArray;
    }

}
