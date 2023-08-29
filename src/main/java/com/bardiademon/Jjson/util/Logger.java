package com.bardiademon.Jjson.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Logger {
    private final Class<?> aClass;

    private static boolean disable = true;

    private static final String DISABLE_COLOR = "\u001B[0m";
    private static final String INFO_COLOR = "\u001B[37m";
    private static final String TRACE_COLOR = "\u001B[35m";
    private static final String WARN_COLOR = "\u001B[33m";
    private static final String ERROR_COLOR = "\u001B[31m";

    public Logger(final Class<?> aClass) {
        this.aClass = aClass;
    }

    public void info(final String log, final Object... params) {
        log("info", INFO_COLOR, log, params);
    }

    public void trace(final String log, final Object... params) {
        log("trace", TRACE_COLOR, log, params);
    }

    public void warn(final String log, final Object... params) {
        log("warn", WARN_COLOR, log, params);
    }

    public void error(final String log, final Object... params) {
        log("error", ERROR_COLOR, log, params);
    }

    private void log(final String type, final String color, final String log, final Object... params) {

        if (disable) {
            return;
        }

        final StringBuilder logBuilder = new StringBuilder(log);

        Throwable throwable = null;
        if (!logBuilder.isEmpty()) {
            if (params.length > 0 && params[params.length - 1] instanceof Throwable) {
                throwable = (Throwable) params[params.length - 1];
            }
            final int endFor = (throwable != null ? params.length - 1 : params.length);
            for (int i = 0; i < endFor; i++) {
                final int indexOf = logBuilder.indexOf("{}");
                logBuilder.replace(indexOf, indexOf + 2, params[i] == null ? "null" : params[i].toString());
            }
        }

        final String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        final String className = aClass.getSimpleName();

        System.out.printf("%s%s: %s ---- %s ---> %s\n%s", color, type.toUpperCase(Locale.ROOT), nowTime, className, logBuilder, DISABLE_COLOR);
        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void disableLog(boolean disable) {
        Logger.disable = disable;
    }
}
