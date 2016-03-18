package com.lge.uiautomator.core;

import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.invoke;
import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.method;
import static com.lge.uiautomator.bootstrap.utils.ReflectionUtils.getField;
import android.util.Log;
import android.view.Display;
import android.view.InputEvent;

import com.android.uiautomator.core.UiDevice;

public class UiAutomatorBridge {

    private static final String CLASS_UI_AUTOMATOR_BRIDGE = "com.android.uiautomator.core.UiAutomatorBridge";

    private static final String FIELD_UI_AUTOMATOR_BRIDGE = "mUiAutomationBridge";
    private static final String FIELD_QUERY_CONTROLLER = "mQueryController";
    private static final String FIELD_INTERACTION_CONTROLLER = "mInteractionController";

    private static final String METHOD_GET_DEFAULT_DISPLAY = "getDefaultDisplay";
    private static final String METHOD_INJECT_INPUT_EVENT = "injectInputEvent";

    private static UiAutomatorBridge INSTANCE = new UiAutomatorBridge();

    private final Object uiAutomatorBridge;

    public UiAutomatorBridge() {
        try {
            final UiDevice device = UiDevice.getInstance();

            this.uiAutomatorBridge = getField(UiDevice.class, FIELD_UI_AUTOMATOR_BRIDGE, device);
        } catch (Error error) {
            Log.e("ERROR", "error", error);
            throw error;
        }
    }

    public InteractionController getInteractionController() {
        return new InteractionController(getField(CLASS_UI_AUTOMATOR_BRIDGE, FIELD_INTERACTION_CONTROLLER, uiAutomatorBridge));
    }

    public QueryController getQueryController() {
        return new QueryController(getField(CLASS_UI_AUTOMATOR_BRIDGE, FIELD_QUERY_CONTROLLER, uiAutomatorBridge));
    }

    public Display getDefaultDisplay() {
        return (Display)invoke(method(CLASS_UI_AUTOMATOR_BRIDGE, METHOD_GET_DEFAULT_DISPLAY), uiAutomatorBridge);
    }

    public boolean injectInputEvent(InputEvent event, boolean sync) {
        return (Boolean)invoke(method(CLASS_UI_AUTOMATOR_BRIDGE, METHOD_INJECT_INPUT_EVENT, InputEvent.class, boolean.class), uiAutomatorBridge, event, sync);
    }

    public static UiAutomatorBridge getInstance() {
        return INSTANCE;
    }
}
