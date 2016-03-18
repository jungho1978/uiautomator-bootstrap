package com.lge.uiautomator.handler;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lge.uiautomator.bootstrap.Logger;
import com.lge.uiautomator.core.UiAutomatorBridge;

/**
 * This handler is used to perform a touchMove event on an element in the sAndroid UI.
 *
 */
public class TouchMove extends TouchEvent {

    @Override
    protected boolean executeTouchEvent() throws UiObjectNotFoundException {
        printEventDebugLine("TouchMove");
        try {
            return UiAutomatorBridge.getInstance().getInteractionController().touchMove(clickX, clickY);
        } catch (final Exception e) {
            Logger.debug("Problem invoking touchMove: " + e);
            return false;
        }
    }
}
