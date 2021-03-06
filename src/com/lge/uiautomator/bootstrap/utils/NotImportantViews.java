package com.lge.uiautomator.bootstrap.utils;

import static com.lge.uiautomator.bootstrap.utils.API.API_18;

import com.android.uiautomator.core.UiDevice;

public abstract class NotImportantViews {
    // setCompressedLayoutHeirarchy doesn't exist on API <= 17
    // http://developer.android.com/reference/android/accessibilityservice/AccessibilityServiceInfo.html#FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
    private static boolean canDiscard = API_18;

    public static void discard(boolean discard) {
        if (canDiscard) {
            UiDevice.getInstance().setCompressedLayoutHeirarchy(discard);
        }
    }
}
