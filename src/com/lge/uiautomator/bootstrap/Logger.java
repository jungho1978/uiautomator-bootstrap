package com.lge.uiautomator.bootstrap;

/**
 * Log to standard out so that the Appium framework can pick it up.
 *
 */
public class Logger {
    private static String prefix = "[UIAUTO-BOOTSTRAP]";
    private static String suffix = " [/UIAUTO-BOOTSTRAP]";

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
