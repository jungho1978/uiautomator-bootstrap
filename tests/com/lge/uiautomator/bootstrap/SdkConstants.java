package com.lge.uiautomator.bootstrap;

/*
 * common/src/main/java/com/android/SdkConstants.java
 */
public class SdkConstants {
    private static final int PLATFORM_UNKNOWN = 0;
    private static final int PLATFORM_LINUX = 1;
    private static final int PLATFORM_WINDOWS = 2;
    private static final int PLATFORM_DARWIN = 3;

    private static final int CURRENT_PLATFORM = currentPlatform();

    public static final String FN_ADB = "adb" + ext(".exe", "");

    private static String ext(String windowsExtension, String nonWindowsExtensions) {
        if (CURRENT_PLATFORM == PLATFORM_WINDOWS) {
            return windowsExtension;
        } else {
            return nonWindowsExtensions;
        }
    }

    private static int currentPlatform() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Mac OS")) {
            return PLATFORM_DARWIN;
        } else if (os.startsWith("Windows")) {
            return PLATFORM_WINDOWS;
        } else if (os.startsWith("Linux")) {
            return PLATFORM_LINUX;
        }
        return PLATFORM_UNKNOWN;
    }
}
