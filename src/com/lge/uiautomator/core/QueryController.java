package com.lge.uiautomator.core;

import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.invoke;
import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.method;
import android.view.accessibility.AccessibilityNodeInfo;

public class QueryController {
    private static final String CLASS_QUERY_CONTROLLER = "com.android.uiautomator.core.QueryController";
    private static final String METHOD_GET_ACCESSIBILITY_ROOT_NODE = "getAccessibilityRootNode";

    private final Object queryController;

    public QueryController(Object queryController) {
        this.queryController = queryController;
    }

    public AccessibilityNodeInfo getAccessibilityRootNode() {
        return (AccessibilityNodeInfo)invoke(method(CLASS_QUERY_CONTROLLER, METHOD_GET_ACCESSIBILITY_ROOT_NODE), queryController);
    }
}
