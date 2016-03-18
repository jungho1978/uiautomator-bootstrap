package com.lge.uiautomator.handler;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lge.uiautomator.bootstrap.Logger;
import com.lge.uiautomator.core.UiAutomatorBridge;

/**
 * This handler is used to perform a touchDown event on an element in the
 * Android UI.
 *
 */
public class TouchDown extends TouchEvent {

    @Override
    protected boolean executeTouchEvent() throws UiObjectNotFoundException {
        printEventDebugLine("TouchDown");
        try {
            return UiAutomatorBridge.getInstance().getInteractionController().touchDown(clickX, clickY);
        } catch (final Exception e) {
            Logger.debug("Problem invoking touchDown: " + e);
            return false;
        }
    }
}