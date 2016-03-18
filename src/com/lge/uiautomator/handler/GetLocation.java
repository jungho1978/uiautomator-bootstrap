package com.lge.uiautomator.handler;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Rect;

import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.AndroidElement;
import com.lge.uiautomator.bootstrap.CommandHandler;
import com.lge.uiautomator.bootstrap.WDStatus;

/**
 * This handler is used to get the text of elements that support it.
 *
 */
public class GetLocation extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand} used for this handler.
     * 
     * @return {@link AndroidCommandResult}
     * 
     * @throws JSONException
     * 
     * @see
     * io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.
     * bootstrap.AndroidCommand)
     */
    @Override
    public AndroidCommandResult execute(final AndroidCommand command) throws JSONException {
        if (!command.isElementCommand()) {
            return getErrorResult("Unable to get location without an element.");
        }

        try {
            final JSONObject res = new JSONObject();
            final AndroidElement el = command.getElement();
            final Rect bounds = el.getBounds();
            res.put("x", bounds.left);
            res.put("y", bounds.top);
            return getSuccessResult(res);
        } catch (final Exception e) {
            return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
        }
    }
}
