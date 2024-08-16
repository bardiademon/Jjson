package com.bardiademon.Jjson.converter.clazz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import static com.bardiademon.Jjson.converter.clazz.JjsonInclude.JjsonIncludeDefault.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JjsonInclude {
    SerializationMode mode() default SerializationMode.FIELDS_ONLY;

    boolean includeNonPublicField() default INCLUDE_NON_PUBLIC_FIELD;

    boolean includeNonPublicMethod() default INCLUDE_NON_PUBLIC_METHOD;

    boolean includeField() default INCLUDE_FIELD;

    boolean includeStaticField() default INCLUDE_STATIC_FIELD;

    boolean includeMethod() default INCLUDE_METHOD;

    boolean includeStaticMethod() default INCLUDE_STATIC_METHOD;

    boolean useJjsonClass() default USE_JJSON_CLASS;

    enum SerializationMode {
        FIELDS_ONLY,        // Only fields are included in JSON
        METHODS_ONLY,       // Only methods are included in JSON
        FIELDS_AND_METHODS, // Both fields and methods are included in JSON
        NONE                // Neither fields nor methods are included
        ;

        public boolean equals(final SerializationMode... serializationModes) {
            if (serializationModes == null || serializationModes.length == 0) {
                return false;
            }
            return Arrays.stream(serializationModes).filter(item -> item.name().equals(name())).findFirst().orElse(null) != null;
        }

        public boolean isFiled() {
            return equals(FIELDS_ONLY, FIELDS_AND_METHODS);
        }

        public boolean isMethod() {
            return equals(METHODS_ONLY, FIELDS_AND_METHODS);
        }

        public boolean isNone() {
            return name().equals(NONE.name());
        }
    }

    final class JjsonIncludeDefault {
        public static final JjsonInclude.SerializationMode MODE = SerializationMode.FIELDS_ONLY;
        public static final boolean INCLUDE_NON_PUBLIC_FIELD = false;
        public static final boolean INCLUDE_NON_PUBLIC_METHOD = false;
        public static final boolean INCLUDE_FIELD = true;
        public static final boolean INCLUDE_STATIC_FIELD = false;
        public static final boolean INCLUDE_METHOD = true;
        public static final boolean INCLUDE_STATIC_METHOD = false;
        public static final boolean USE_JJSON_CLASS = false;
    }
}