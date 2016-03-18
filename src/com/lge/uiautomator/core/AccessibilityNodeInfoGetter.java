package com.lge.uiautomator.core;

import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.invoke;
import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.method;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiObject;

/**
 * Static helper class for getting {@link AccessibilityNodeInfo} instances.
 *
 */
public class AccessibilityNodeInfoGetter {
    private static Configurator configurator = Configurator.getInstance();

    /**
     * Gets the {@link AccessibilityNodeInfo} associated with the given {@link UiObject}
     */
    public static AccessibilityNodeInfo fromUiObject(UiObject uiObject) {
        return (AccessibilityNodeInfo)invoke(method(UiObject.class, "findAccessibilityNodeInfo", long.class), uiObject, configurator.getWaitForSelectorTimeout());
    }
}
