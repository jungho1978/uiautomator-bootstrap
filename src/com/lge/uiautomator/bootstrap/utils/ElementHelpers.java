package com.lge.uiautomator.bootstrap.utils;

import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.accessibility.AccessibilityNodeInfo;

import com.android.uiautomator.core.UiObject;
import com.lge.uiautomator.bootstrap.AndroidElement;

public class ElementHelpers {
    private static Method findAccessibilityNodeInfo;

    private static AccessibilityNodeInfo elementToNode(AndroidElement element) {
        AccessibilityNodeInfo result = null;
        try {
            result = (AccessibilityNodeInfo)findAccessibilityNodeInfo.invoke(element.getUiObject(), 5000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Remove all duplicate elements from the provided list
     *
     * @param elements - elements to remove duplicates from
     * @return a new list with duplicates removed
     */
    public static List<AndroidElement> dedupe(List<AndroidElement> elements) {
        try {
            findAccessibilityNodeInfo = method(UiObject.class, "findAccessibilityNodeInfo", long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<AndroidElement> result = new ArrayList<AndroidElement>();
        List<AccessibilityNodeInfo> nodes = new ArrayList<AccessibilityNodeInfo>();

        for (AndroidElement element : elements) {
            AccessibilityNodeInfo node = elementToNode(element);
            if (!nodes.contains(node)) {
                nodes.add(node);
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Return the JSONObject which Appium returns for an element
     *
     * For example, appium returns elements like [{"ELEMENT":1}, {"ELEMENT":2}]
     */
    public static JSONObject toJSON(AndroidElement el) throws JSONException {
        return new JSONObject().put("ELEMENT", el.getId());
    }
}
