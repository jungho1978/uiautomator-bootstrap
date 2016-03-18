package com.lge.uiautomator.handler;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.uiautomator.core.UiDevice;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;

/**
 * This handler is used to get the size of the screen.
 *
 */
public class GetDeviceSize extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand} used for this handler.
     * 
     * @return {@link AndroidCommandResult}
     * 
     * @throws JSONException
     * 
     * @see io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.bootstrap.AndroidCommand)
     */
    @Override
    public AndroidCommandResult execute(final AndroidCommand command) {
        if (!command.isElementCommand()) {
            // only makes sense on a device
            final UiDevice d = UiDevice.getInstance();
            final JSONObject res = new JSONObject();
            try {
                res.put("height", d.getDisplayHeight());
                res.put("width", d.getDisplayWidth());
            } catch (final JSONException e) {
                getErrorResult("Error serializing height/width data into JSON");
            }
            return getSuccessResult(res);
        } else {
            return getErrorResult("Unable to get device size on an element.");
        }
    }
}
