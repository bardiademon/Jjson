package com.bardiademon.Jjson.config;

import com.bardiademon.Jjson.util.Logger;

import java.io.IOException;

public class LoggerConfig {

    public static void enableLog(final boolean enable, final boolean console) {
        Logger.enableLog(enable, console);
    }

    public static void setPath(final String path) throws IOException {
        Logger.setPath(path);
    }

    public static void closePrintWriter() {
        Logger.closePrintWriter();
    }

    public static void shutdownThread() {
        Logger.shutdownThread();
    }

}
