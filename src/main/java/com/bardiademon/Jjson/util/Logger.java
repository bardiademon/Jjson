package com.bardiademon.Jjson.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Logger {
    private final Class<?> aClass;

    private static ExecutorService executorService;

    private static PrintWriter printWriter;
    private static boolean isLoggedEnabled = false;
    private static boolean isLoggedConsoleEnabled = false;

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

        if (!isLoggedEnabled) {
            return;
        }
        if (executorService == null || executorService.isShutdown()) {
            System.out.println("ExecutorService is not initialized or has been shut down.");
            return;
        }

        executorService.execute(() -> {

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
            final String className = aClass.getName();

            final String logMessage = "%s: %s ---- %s ---> %s".formatted(type.toUpperCase(Locale.ROOT), nowTime, className, logBuilder);
            if (isLoggedConsoleEnabled) {
                System.out.printf("%s%s\n%s", color, logMessage, DISABLE_COLOR);
            }

            synchronized (Logger.class) {
                if (printWriter != null) {
                    printWriter.append(logMessage);
                    printWriter.append("\n");
                    printWriter.flush();
                }

                if (throwable != null) {
                    if (isLoggedConsoleEnabled) {
                        throwable.printStackTrace(System.err);
                    }
                    if (printWriter != null) {
                        throwable.printStackTrace(printWriter);
                    }
                }
            }
        });
    }

    public static void enableLog(final boolean enable, final boolean console) {
        Logger.isLoggedEnabled = enable;
        Logger.isLoggedConsoleEnabled = console;
        if (enable) {
            if (executorService == null || executorService.isShutdown()) {
                executorService = Executors.newSingleThreadExecutor();
            }
        } else {
            closePrintWriter();
            shutdownThread();
        }
    }

    public static void setPath(final String path) throws IOException {
        synchronized (Logger.class) {
            closePrintWriter();
            final File file = new File(path);
            if ((file.exists() || file.createNewFile()) && (file.isFile() && !file.isDirectory())) {
                printWriter = new PrintWriter(new FileWriter(file, true));
            } else {
                throw new IOException("The path is not a valid file: " + path);
            }
        }
    }


    public static void closePrintWriter() {
        synchronized (Logger.class) {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
                printWriter = null;
            }
        }
    }

    public static void shutdownThread() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                        System.err.println("ExecutorService did not terminate.");
                    }
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            executorService = null;
            enableLog(false, false);
        }
    }
}
