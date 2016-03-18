package com.lge.uiautomator.bootstrap;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/*
 * uiautomatorviewer/src/main/java/com/android/uiautomator/DebugBridge.java
 * sdk/tools/uiautomatorviewer.bat
 */
public class DebugBridge {
    private static AndroidDebugBridge sDebugBridge;
    private static String sAdbLocation = null;

    private static String getAdbLocation() {
        String toolsDir = System.getProperty("com.android.uiautomator.bindir");
        if (toolsDir == null) {
            return null;
        }

        File sdk = new File(toolsDir).getParentFile();

        // check if adb is present in platform-tools
        File platformTools = new File(sdk, "platform-tools");
        File adb = new File(platformTools, SdkConstants.FN_ADB);
        if (adb.exists()) {
            return adb.getAbsolutePath();
        }

        // check if adb is present in the tools directory
        adb = new File(toolsDir, SdkConstants.FN_ADB);
        if (adb.exists()) {
            return adb.getAbsolutePath();
        }

        // check if we're in the Android source tree where adb is in
        // $ANDROID_HOST_OUT/bin/adb
        String androidOut = System.getenv("ANDROID_HOST_OUT");
        if (androidOut != null) {
            String adbLocation = androidOut + File.separator + "bin" + File.separator + SdkConstants.FN_ADB;
            if (new File(adbLocation).exists()) {
                return adbLocation;
            }
        }

        return null;
    }

    public static void setAdbLocation(String adbLocation) {
        sAdbLocation = adbLocation;
    }

    public static void init() {
        String adbLocation = sAdbLocation != null ? sAdbLocation : getAdbLocation();
        if (adbLocation != null) {
            AndroidDebugBridge.init(false /* debugger support */);
            sDebugBridge = AndroidDebugBridge.createBridge(adbLocation, false);
        }
    }

    public static void terminate() {
        if (sDebugBridge != null) {
            sDebugBridge = null;
            AndroidDebugBridge.terminate();
        }
    }

    public static boolean isInitialized() {
        return sDebugBridge != null;
    }

    public static List<IDevice> getDevices() {
        return Arrays.asList(sDebugBridge.getDevices());
    }
}
