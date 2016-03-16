package com.goodluck.bootstrap;

public class Logger {
    private static String prefix = "[BOOTSTRAP]";
    private static String suffix = " [/BOOTSTRAP]";

    public static void debug(final String msg) {
        System.out.println(Logger.prefix + " [debug] " + msg + Logger.suffix);
    }

    public static void error(final String msg) {
        System.out.println(Logger.prefix + " [error] " + msg + Logger.suffix);
    }

    public static void info(final String msg) {
        System.out.println(Logger.prefix + " [info] " + msg + Logger.suffix);
    }
}
