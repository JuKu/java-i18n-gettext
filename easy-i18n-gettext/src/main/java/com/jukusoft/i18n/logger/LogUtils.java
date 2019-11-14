package com.jukusoft.i18n.logger;

import com.jukusoft.i18n.I;

import java.util.function.BiConsumer;
import java.util.logging.Level;

public class LogUtils {

    protected static BiConsumer<Level,String> logConsumer;

    private LogUtils() {
        //
    }

    public static void setLogger(BiConsumer<Level,String> logConsumer) {
        LogUtils.logConsumer = logConsumer;
    }

    public static void log(Level level, String message) {
        if (logConsumer == null) {
            return;
        }

        logConsumer.accept(level, message);
    }

}
